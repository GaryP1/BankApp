package models

import play.api.data.Form
import play.api.data.Forms._
import models.Constraints._

/**
 * Created by a585401 on 07/09/2015.
 */
case class LoginModel(email : String, password : String)
object LoginForm {
  val loginForm : Form[LoginModel] = Form(mapping(
    "email" -> nonEmptyText.verifying(emailExists),
    "password" -> nonEmptyText)
    (LoginModel.apply)(LoginModel.unapply))
}
