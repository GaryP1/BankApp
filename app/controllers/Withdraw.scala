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

class Withdraw extends Controller{
  
  def showWithdraw = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.withdraw(account))
  }

  def doWithdraw = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      TransactionData.transactionData1.bindFromRequest.fold(
        errors => BadRequest(views.html.withdraw(account, "Not enough Cash...")),
        value => AccountHandlerService withdraw(account, value amount)
      )
      Ok(views.html.home(account, "Withdraw Successful"))
    }catch{
      case e : FieldException => BadRequest(views.html.withdraw(account, e message))
    }
  }
}