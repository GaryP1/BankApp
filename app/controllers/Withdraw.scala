package controllers

/**
 * @author a585401
 */

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.AccountFetchService
import services.AccountHandlerService

class Withdraw extends Controller{
  
  def showWithdraw = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.withdraw(account,"")) 
  }
  
  case class DataW(amount : String)
  val data : Form[DataW]=Form(mapping("amount" -> nonEmptyText)(DataW apply)(DataW unapply))
  def doWithdraw = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs.getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    try{
      val input = data.bindFromRequest.get
      val accHandler = AccountHandlerService
      accHandler withdraw(account, input amount)
      Ok(views.html.home(account, "Withdraw Successful"))
    }catch{
      case e : IllegalArgumentException =>{BadRequest(views.html.withdraw(account, "Withdraw Failed"))}
    }
      
  }
}