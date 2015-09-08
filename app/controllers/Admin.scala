
package controllers

/**
 * @author a585401
 */

import play.api.mvc._
import services.AccountFetchService
import services.AccountCreatorService
import exceptions.FieldException
import models._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

class Admin extends Controller{
  def showAddAccount = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.addAccountAdmin(account, "", RegisterForm.registerForm2))
  }
  
  def doAddAccount = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    try{
      RegisterForm.registerForm2.bindFromRequest.fold(
        errors => BadRequest(views.html.addAccountAdmin(account, "", RegisterForm.registerForm2)),
        value => {
          AccountCreatorService createAccount(value fName, value lName, value email, value password, value confirmPassword, value isAdmin)
          Ok(views.html.homeAdmin(account, "Register Successful"))
        }
      )
    }catch{case e : FieldException => {BadRequest(views.html.addAccountAdmin(account, e message, RegisterForm.registerForm2))}}
  }
  
  def showAccountSearch = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.accountSearchAdmin(account, "", SearchForm.searchForm))
  }

  def doAccountSearch = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    SearchForm.searchForm.bindFromRequest.fold(
      errors => BadRequest(views.html.login("It's fucked!", LoginForm.loginForm)),
      value => Ok(views.html.accountSearchResultsAdmin(account, AccountFetchService.getAccounts(value searchCriteria, value searchField)))
    )
  }
}