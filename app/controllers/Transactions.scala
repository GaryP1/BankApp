package controllers

import play.api.mvc.{Action, Controller}
import services.AccountFetchService

/**
 * Created by a585401 on 09/09/2015.
 */
class Transactions extends Controller{
  def showTransactions = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.transactions(account))
  }
}
