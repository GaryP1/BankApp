package services

/**
 * @author a585401
 */
import play.api.Play.current
import play.api.db._
import java.sql.ResultSet
import java.util.Date

object AccountTransactionService {
  def updateSimple(accNo : String, amount : String, tType : String) {
    //Setup Connection
    val con = DB getConnection()
    val stmt = con.createStatement
    val date : Date = new Date
    val dateString = date.getDate + "/" + date.getMonth + "/" + ((date getYear) + 1900)
    val res = stmt executeQuery s"select transactions from transactions where accNo = '$accNo'"
    res next
    val transactions = (res getString "transactions") + s"$amount $tType $dateString;"
    stmt executeUpdate s"update transactions set transactions = '$transactions' where accNo = '$accNo'"
    con close
  }
  
  def updateTransfer(accNo : String, accNoTo : String, amount : String){
    val con = DB getConnection()
    val stmt = con.createStatement
    val date : Date = new Date
    val dateString = date.getDate + "/" + date.getMonth + "/" + ((date getYear) + 1900)
    //Transfer to
    var res = stmt executeQuery s"select transactions from transactions where accNo = '$accNo'"
    res next()
    var transactions = (res getString "transactions") + s"$amount tr$accNoTo $dateString;"
    stmt executeUpdate s"update transactions set transactions = '$transactions' where accNo = '$accNo'"
    //Transfer from
    res = stmt executeQuery s"select transactions from transactions where accNo = '$accNoTo'"
    res next()
    transactions = (res getString "transactions") + s"$amount tf$accNo $dateString;"
    stmt executeUpdate s"update transactions set transactions = '$transactions' where accNo = '$accNoTo'"
    con close
  }
}