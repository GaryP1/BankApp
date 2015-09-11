package models

import exceptions.FieldException
import play.api.data.Form
import play.api.data.Forms._

/**
 * Created by a585401 on 11/09/2015.
 */

case class PasswordChangeModel(oldPassword : String, newPassword : String, confPassword : String)
case class DetailsChangeModel(password : String, fName : String, lName : String)
case class EmailChangeModel(email : String)
object SettingsForm{
  val passwordChangeForm : Form[PasswordChangeModel] = Form(mapping(
    "oldPassword" -> nonEmptyText,
    "newPassword" -> nonEmptyText.verifying(Constraints.passwordConstraints),
    "confPassword" -> nonEmptyText)
    (PasswordChangeModel.apply)(PasswordChangeModel.unapply).verifying(throw new FieldException("Passwords need to match"),form=>form.newPassword equals form.confPassword))
  val detailsChangeForm : Form[DetailsChangeModel] = Form(mapping(
    "password" -> nonEmptyText,
    "fName" -> nonEmptyText,
    "lName" -> nonEmptyText
  )(DetailsChangeModel.apply)(DetailsChangeModel.unapply))

  val emailChangeForm : Form[EmailChangeModel] = Form(mapping(
  "email" -> nonEmptyText.verifying(Constraints.emailConstraints, Constraints.emailNotExist)
  )(EmailChangeModel.apply)(EmailChangeModel.unapply))

}
