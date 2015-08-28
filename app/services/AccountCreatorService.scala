package services

import exceptions.FieldException
import scala.util.Random

/**
 * @author a585401
 */
object AccountCreatorService {
  def createAccount(fName : String, lName : String, email : String, password : String, confPassword : String) = {
    if(!(fName matches "^[A-Za-z]*$") || !(lName matches "^[A-Za-z]*$")) 
      throw new FieldException("Use only letters for Names")
    else if(!(email matches "^[A-Za-z]*$"))
      throw new FieldException("Use correct email format")
    else if(!(password matches "^[A-Za-z]*$"))
      throw new FieldException("Broken Password")
    else if(!(password equals confPassword))
      throw new FieldException("Passwords need to match")
    val afs = AccountFetchService
    if(afs.dataExists("email", email, "userinfo"))
      throw new FieldException("Email Address Already Used")
    val accNo = generateRandom()
    val balance = BigDecimal(0.00)
    val PIN = ""
    val dis = DatabaseInteractionService
    dis executeUpdate s"insert into accounts(accNo, balance, PIN) values($accNo, $balance, $PIN)"
  }
  
  val afs = AccountFetchService
  def generateRandom(accNo : String = ""): String = {
    for(n <- 1 to 9) accNo.+(Random.nextInt(9))
    if(afs dataExists("accNo", accNo, "accounts"))
      generateRandom()
    accNo
  }
}