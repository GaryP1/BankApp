package controllers


import play.api.mvc._
/**
 * Created by a585401 on 08/09/2015.
 */
class Error extends Controller{
  def showError = Action{implicit request=>
    Unauthorized("TODO - Temporary fix")
  }
}
