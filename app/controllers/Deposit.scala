package controllers

/**
 * @author a585401
 */

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.{AccountFetchService, AccountHandlerService}

class Deposit extends Controller{
  
  def showDeposit = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.deposit(account,"")) 
  }
  
  case class DataD(amount : String)
  val data : Form[DataD]=Form(mapping("amount" -> nonEmptyText)(DataD.apply)(DataD.unapply))
  def doDeposit = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    try{
      val input = data.bindFromRequest.get
      val accHandler = AccountHandlerService
      accHandler deposit(account, input amount)
      Ok(views.html.home(account, "Deposit Successful"))
    }catch{
      case e : IllegalArgumentException =>{BadRequest(views.html.deposit(account, "Deposit Failed"))}
    }
  }
  
}