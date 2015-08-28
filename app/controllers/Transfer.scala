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

class Transfer extends Controller{
  
  def showTransfer = Action{implicit request =>
    val afs = AccountFetchService
    val account = afs getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    Ok(views.html.transfer(account,"")) 
  }
  
  case class DataT(accTo : String, amount : String)
  val data : Form[DataT]=Form(mapping( "accTo" -> nonEmptyText, "amount" -> nonEmptyText)(DataT.apply)(DataT.unapply))
  def doTransfer = Action{implicit request =>
    val input = data.bindFromRequest.get
    val afs = AccountFetchService
    val account = afs getAccount("lastsession", request.session.get("uuid").get, "userinfo", false)
    try{
      require(afs dataExists("accNo", input accTo, "accounts"))
      val accountTo = afs getAccount("accNo", input accTo, "accounts", true)
      val accHandler = AccountHandlerService
      accHandler transfer(account, accountTo, input amount)
      Ok(views.html.home(account, "Transfer Successful"))
      
    }catch{
      case e : IllegalArgumentException =>{BadRequest(views.html.transfer(account, "Transfer Failed"))}
    }
  }
}