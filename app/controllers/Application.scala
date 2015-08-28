package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def signOut = Action{
    Ok(views.html.signout()).withNewSession
  }
}
