package controllers

import models.LoginForm
import play.api.mvc._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Application extends Controller {

  def signOut = Action{
    Ok(views.html.login("", LoginForm.loginForm)).withNewSession
  }
}
