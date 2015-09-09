package services

import scala.util.Random
import java.math.BigDecimal
/**
 * @author a585401
 */
object AccountCreatorService {


  private[this] val afs = AccountFetchService

  def createAccount(fName : String, lName : String, email : String, password : String, isAdmin : Boolean = false) = {
    val accNo = generateRandom()
    val balance = new BigDecimal("0")
    var PIN = ""
    for(n <- 1 to 4) PIN = PIN+(Random nextInt 9)
    DatabaseInteractionService executeUpdate s"insert into accounts(accNo, balance, PIN, isAdmin) values('$accNo', $balance, '$PIN', $isAdmin);"
    DatabaseInteractionService executeUpdate s"insert into userinfo(accNo, pass, fName, lName, email, dob) values('$accNo', '$password', '$fName', '$lName', '$email', '00/00/0000');"
    DatabaseInteractionService executeUpdate s"insert into transactions(accNo, transactions) values('$accNo', '');"
  }

  private[this] def generateRandom(): String = {
    var accNo : String = ""
    for(a <- 1 to 8) accNo = accNo + (Random nextInt 9)
    if(afs dataExists("accNo", accNo, "accounts"))
      generateRandom()
    else  
      accNo
  }
}