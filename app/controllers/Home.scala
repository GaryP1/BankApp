package controllers

/**
 * @author a585401
 */

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.AccountFetchService

class Home extends Controller{
  
  def showHome = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.home(account, "")) 
  }
}