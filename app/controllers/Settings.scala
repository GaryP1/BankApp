package controllers

import exceptions.FieldException
import models.SettingsForm
import play.api.mvc.{Action, Controller}
import services.{AccountEditingService, AccountFetchService}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

/**
 * Created by a585401 on 11/09/2015.
 */
class Settings extends Controller{
  def showPasswordChange() = Action{implicit request =>
    val account = AccountFetchService getAccount ("lastsession", request.session.get("uuid").get)
    Ok(views.html.passwordChange(account))
  }

  def doPasswordChange() = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      SettingsForm.passwordChangeForm.bindFromRequest.fold(
        errors => BadRequest(views.html.passwordChange(account)),
        value => {
          if(!account.loginPass(value oldPassword))
            throw new FieldException("Incorrect Account Password")
          AccountEditingService editAccountField (account.accNo, value.newPassword, "pass")
          Ok(views.html.passwordChange(account))
        }
      )
    }catch{case e : FieldException => BadRequest(views.html.passwordChange(account, e message))}
  }

  def showEmailChange() =Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.emailChange(account))
  }


  def doEmailChange() = Action {implicit request=>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      SettingsForm.emailChangeForm.bindFromRequest.fold(
        errors => BadRequest(views.html.emailChange(account, "Change Failed")),
        values => {
          AccountEditingService editAccountField(account accNo, values email, "email")
          Ok(views.html.home(account, "Email Change Successful"))
        }
      )
    }catch{case e : FieldException => BadRequest(views.html.emailChange(account, e message))}
  }
}
