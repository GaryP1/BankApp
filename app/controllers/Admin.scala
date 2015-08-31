package controllers

/**
 * @author a585401
 */

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.AccountFetchService

class Admin extends Controller{
  def showAddAccount = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.addAccountAdmin(account, ""))
  }
  def doAddAccount =Action{implicit request =>
    
  }
}