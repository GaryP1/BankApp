package services

/**
 * @author a585401
 */

import models.Account
import exceptions.FieldException
import java.sql.ResultSet
import play.api.db.DB
import play.api.Play.current
import play.api.libs.iteratee.Iteratee

object AccountFetchService {
  
  def getAccount(columnName : String, data : String, tableName : String, isAccNo : Boolean) : Account = {
    val con = DB getConnection()
    val stmt = con createStatement
    var res  : ResultSet = null
    var accNo = data
    if(!isAccNo){
      res = stmt executeQuery s"select accNo from $tableName where $columnName = '$data';"
      res next()
      accNo = res getString "accNo"
    }
    res = stmt executeQuery s"select * from accounts where accNo = '$accNo';"
    res next()
    val accNo2 = res getString "accNo"
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
    new Account(accNo2, PIN, balance, pass, fName, lName, email, transactions, isAdmin)
  }
  
  def dataExists(column : String, data : String, table : String): Boolean = {
    val con = DB getConnection()
    val stmt = con createStatement
    var res = stmt executeQuery s"select $column from $table where $column = '$data'" 
    var rows = 0
    while (res next) rows+=1
    con close()
    rows == 1
  }
}