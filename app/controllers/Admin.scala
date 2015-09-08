
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
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.addAccountAdmin(account))
  }
  
  def doAddAccount = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    try{
      RegisterForm.registerForm2.bindFromRequest.fold(
        errors => BadRequest(views.html.addAccountAdmin(account)),
        value => {
          AccountCreatorService createAccount(value fName, value lName, value email, value password, isAdmin = value isAdmin)
          Ok(views.html.homeAdmin(account, "Register Successful"))
        }
      )
    }catch{case e : FieldException => {BadRequest(views.html.addAccountAdmin(account, e message))}}
  }
  
  def showAccountSearch = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    Ok(views.html.accountSearchAdmin(account))
  }

  def doAccountSearch = Action{implicit request =>
    val account = AccountFetchService getAccount("lastsession", request.session.get("uuid").get)
    SearchForm.searchForm.bindFromRequest.fold(
      errors => BadRequest(views.html.login("Internal Error")),
      value => Ok(views.html.accountSearchResultsAdmin(account, AccountFetchService.getAccounts(value searchCriteria, value searchField)))
    )
  }
}