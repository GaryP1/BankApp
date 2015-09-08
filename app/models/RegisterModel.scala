package models

import exceptions.FieldException
import play.api.data.Form
import play.api.data.Forms._
import models.Constraints._

/**
*  Created by a585401 on 01/09/2015.
*/
case class RegisterModel1(fName : String, lName : String, email : String, password : String, confirmPassword : String)
case class RegisterModel2(fName : String, lName : String, email : String, password : String, confirmPassword : String, isAdmin : Boolean)
object RegisterForm{
  val registerForm2 : Form[RegisterModel2] = Form(mapping(
    "fName"->nonEmptyText.verifying(nameConstraints),
    "lName"->nonEmptyText.verifying(nameConstraints),
    "email"->nonEmptyText.verifying(emailConstraints,emailNotExist),
    "password"->nonEmptyText.verifying(passwordConstraints),
    "confirmPassword"->nonEmptyText,
    "isAdmin"-> boolean)
    (RegisterModel2.apply)(RegisterModel2.unapply)verifying(throw new FieldException("Passwords Do Not Match"), form=>form.password equals form.confirmPassword)
  )
  val registerForm1 : Form[RegisterModel1] = Form(mapping(
    "fName"->nonEmptyText.verifying(nameConstraints),
    "lName"->nonEmptyText.verifying(nameConstraints),
    "email"->nonEmptyText.verifying(emailConstraints,emailNotExist),
    "password"->nonEmptyText.verifying(passwordConstraints),
    "confirmPassword"->nonEmptyText)
    (RegisterModel1.apply)(RegisterModel1.unapply)verifying(throw new FieldException("Passwords Do Not Match"), form=>form.password equals form.confirmPassword)
  )
}
