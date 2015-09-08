package controllers

import models.{RegisterForm, LoginForm}
import play.api.mvc.Action
import play.api.mvc.Results._
import services.AccountCreatorService
import exceptions.FieldException
import play.api.i18n.Messages.Implicits._
import play.api.Play.current


class Register {
  def showRegister() =Action{Ok(views.html.register("", RegisterForm.registerForm1))}

  def doRegister() = Action{implicit request =>
    try{
      RegisterForm.registerForm1.bindFromRequest.fold(
        errors=> BadRequest(views.html.register("",RegisterForm.registerForm1)),
        value=>{
          AccountCreatorService createAccount(value fName, value lName, value email, value password, value confirmPassword, false)
          Ok(views.html.login("Register Succesful", LoginForm.loginForm))
        }
      )

    }catch{
      case e : FieldException =>{BadRequest(views.html.register(e message,RegisterForm.registerForm1))}
    }
  }
}