package controllers

/**
 * @author a585401
 */
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.SessionHandlerService
import services.AccountFetchService
import models.Account

class Login extends Controller{
  
  case class Data(email : String, password : String)
  val data : Form[Data] = Form(mapping("email" -> nonEmptyText, "password" -> nonEmptyText)(Data.apply)(Data.unapply))
  def doLogin = Action{ implicit request =>
    val sessionId = java.util.UUID.randomUUID().toString()
    try{
      val input = data.bindFromRequest.get
      val accFetch = AccountFetchService
      require(accFetch dataExists("email", input email, "userinfo"))
      val account = accFetch getAccount("email", input email, "userinfo", false)
      require(account loginPass(input password))
      val shs = SessionHandlerService
      shs setNewSessionId(account accNo, sessionId)
      Ok(views.html.home(account, "")) withSession("uuid" -> sessionId)
    }catch{
      case e : IllegalArgumentException =>{Ok(views.html.login("Incorrect Login"))}  
    }
  }
  
  def showLogin = Action {
    Ok(views.html.login(""))
  }
}