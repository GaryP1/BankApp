package controllers

/**
 * @author a585401
 */

import exceptions.FieldException
import models.TransactionData
import play.api.mvc._
import services.{AccountHandlerService, AccountFetchService}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Deposit extends Controller{
  
  def showDeposit = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.deposit(account))
  }
  

  def doDeposit = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      TransactionData.transactionData1.bindFromRequest.fold(
        errors => BadRequest(views.html.deposit(account)),
        value => {
          AccountHandlerService deposit(account, value amount)
          Ok(views.html.home(account, "Deposit Successful"))
        }
      )
    }catch{
      case e : FieldException => BadRequest(views.html.deposit(account, e message))
    }
  }
}