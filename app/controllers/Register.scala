package controllers

import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.data.Form
import play.api.data.Forms._
import services.AccountCreatorService
import exceptions.FieldException


class Register {
  def showRegister() =Action{Ok(views.html.register(""))}
  
  case class Data(fName : String, lName : String, email : String, password : String, confirmPassword : String)
  
  val data : Form[Data] = Form(mapping("fName"->nonEmptyText, "lName"->nonEmptyText, "email"->nonEmptyText, "password"->nonEmptyText, "confirmPassword"->nonEmptyText)(Data.apply)(Data.unapply))
  
  def doRegister() = Action{implicit request =>
    try{
      val input = data.bindFromRequest.get
      val acs = AccountCreatorService
      acs createAccount(input fName, input lName, input email, input password, input confirmPassword)
      Ok(views.html.login("Register Succesful"))
    }catch{
      case e : FieldException =>{BadRequest(views.html.register(e message))}
    }
  }
}