package services

/**
 * @author a585401
 */

import models.Account
import java.sql.ResultSet
import play.api.db.DB
import play.api.Play.current
import java.util.ArrayList

object AccountFetchService {
  
  def getAccount(columnName : String = "accNo", data : String, tableName : String = "userinfo") : Account = {
    val con = DB getConnection()
    val stmt = con createStatement
    var res  : ResultSet = null
    var accNo = data
    if(!(columnName equals "accNo")){
      res = stmt executeQuery s"select accNo from $tableName where $columnName = '$data';"
      res next()
      accNo = res getString "accNo"
    }
    res = stmt executeQuery s"select * from accounts where accNo = '$accNo';"
    res next()
    val PIN = res getString "PIN"
    val balance = res getBigDecimal "balance"
    val isAdmin = res getBoolean "isAdmin"
    res = stmt executeQuery s"select * from userinfo where accNo = '$accNo';"
    res next()  
    val pass = res getString "pass"
    val fName = res getString "fName"
    val lName = res getString "lName"
    val email = res getString "email"
    res = stmt executeQuery s"select * from transactions where accNo = '$accNo'"
    res next()
    val trans = res getString "transactions"
    val transactions = trans split ";"
    con close()
    new Account(accNo, PIN, balance, pass, fName, lName, email, transactions, isAdmin)
  }
  
  def dataExists(column : String, data : String, table : String): Boolean = {
    val con = DB getConnection()
    val stmt = con createStatement
    val res = stmt executeQuery s"select $column from $table where $column = '$data'"
    var rows = 0
    while (res next) rows+=1
    con close()
    rows == 1
  }
  
  def getAccounts(input : String, column : String) : ArrayList[Account] = {
    val con = DB getConnection()
    val stmt = con createStatement()
    var res : ResultSet = null
    column match{
      case "fName" => res = stmt executeQuery s"select accNo from userinfo where fName = '$input'"
      case "lName" => res = stmt executeQuery s"select accNo from userinfo where lName = '$input'"
      case "email" => res = stmt executeQuery s"select accNo from userinfo where email = '$input'"
      case "accNo" => res = stmt executeQuery s"select accNo from userinfo where accNo = '$input'"
    }
    val accounts : ArrayList[Account] = new ArrayList[Account]
    while(res next) {
      val accNo = res getString "accNo"
      val account = getAccount(data = accNo)
      accounts add account
    }
    con close()
    accounts
  }
}