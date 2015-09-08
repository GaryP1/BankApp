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
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.withdraw(account,"", TransactionData.transactionData1))
  }

  def doWithdraw = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    try{
      TransactionData.transactionData1.bindFromRequest.fold(
        errors => BadRequest(views.html.withdraw(account, "Not enough Cash...", TransactionData.transactionData1)),
        value => AccountHandlerService withdraw(account, value amount)
      )
      Ok(views.html.home(account, "Withdraw Successful"))
    }catch{
      case e : FieldException => BadRequest(views.html.withdraw(account, e message, TransactionData.transactionData1))
    }
  }
}