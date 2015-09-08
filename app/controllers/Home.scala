package controllers

/**
 * @author a585401
 */

import play.api.mvc._
import services.AccountFetchService
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Home extends Controller{
  
  def showHome = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.home(account))
  }
}