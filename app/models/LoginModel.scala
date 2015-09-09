package models

import exceptions.FieldException
import play.api.data.Form
import play.api.data.Forms._
import models.Constraints._
import services.AccountFetchService

/**
 * Created by a585401 on 07/09/2015.
 */
case class LoginModel(email : String, password : String)
object LoginForm {

  @throws(classOf[FieldException])
  val loginForm : Form[LoginModel] = Form(mapping(
    "email" -> nonEmptyText.verifying(emailExists),
    "password" -> nonEmptyText)
    (LoginModel.apply)(LoginModel.unapply)
    verifying(throw new FieldException("Incorrect Password"),form => AccountFetchService getAccount("email", form email) loginPass(form password)))
}
