package services

/**
 * @author a585401
 */

import exceptions.FieldException
import java.sql.SQLException
import play.api.Play.current
import play.api.db._
import exceptions.FieldException

object SessionHandlerService {
  def getSessionId(accNo : String) : String ={
     try{
       val con = DB getConnection()
       val stmt = con createStatement
       val rs = stmt executeQuery s"select lastSession from userinfo where accNo ='${accNo}';"
       rs next()
       rs getString "lastsession"
     }catch{
       case e : IllegalArgumentException =>{throw new FieldException("No match!")}
     }
  }
  def setNewSessionId(accNo : String, uuid : String){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update userinfo set lastsession='$uuid' where accNo = '$accNo';"
  }
}