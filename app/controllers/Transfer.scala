package controllers

/**
 * @author a585401
 */

import exceptions.FieldException
import models.TransactionData
import play.api.mvc._
import services.AccountFetchService
import services.AccountHandlerService
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Transfer extends Controller{
  
  def showTransfer = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.transfer(account))
  }

  def doTransfer = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      TransactionData.transactionData2.bindFromRequest.fold(
        errors => BadRequest(views.html.transfer(account, "Not enough Cash...")),
        value => {
          val accountTo = AccountFetchService getAccount (data = value accNoTo)
          AccountHandlerService transfer(account, accountTo, value amount)
          Ok(views.html.home(account, "Withdraw Successful"))
        }
      )
    }catch{
      case e : FieldException => BadRequest(views.html.transfer(account, e message))
    }
  }
}