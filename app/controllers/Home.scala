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
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.home(account, "")) 
  }
}