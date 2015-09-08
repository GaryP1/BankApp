package controllers

/**
 * @author a585401
 */

import exceptions.FieldException
import models.LoginForm
import play.api.mvc._
import services.{SessionHandlerService, AccountFetchService}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Login extends Controller{

  def doLogin = Action{ implicit request =>
    val sessionId = java.util.UUID.randomUUID().toString()
    try{
      LoginForm.loginForm.bindFromRequest.fold(
        errors => BadRequest(views.html.login()),
        value => {
          val account = AccountFetchService getAccount ("email", value email)
          SessionHandlerService setNewSessionId (account accNo, sessionId)
          if(account isAdmin)
            Ok(views.html.homeAdmin(account)).addingToSession(("uuid",sessionId))
          else
            Ok(views.html.home(account))
        }
      )
    }
    catch { case e : FieldException => BadRequest(views.html.login(e.message))}
  }
  
  def showLogin = Action {
    Ok(views.html.login())
  }
}