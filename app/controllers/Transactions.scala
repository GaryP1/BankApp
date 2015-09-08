package controllers

/**
 * @author a585401
 */
import play.api.mvc._
import services.AccountFetchService
import services.TransactionService

class Transactions extends Controller{
  def showTransactions = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.transactions(account, new TransactionService))
  }
}