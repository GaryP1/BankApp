package services

import exceptions.FieldException
import scala.util.Random
import java.math.BigDecimal
/**
 * @author a585401
 */
object AccountCreatorService {
  def createAccount(fName : String, lName : String, email : String, password : String, confPassword : String) = {
    if(!(fName matches "^[A-Za-z]*$") || !(lName matches "^[A-Za-z]*$")) 
      throw new FieldException("Use only letters for Names")
    else if(!(email matches "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$"))
      throw new FieldException("Use correct email format")
    else if(!(password matches "^[A-Za-z]*$"))
      throw new FieldException("Broken Password")
    else if(!(password equals confPassword))
      throw new FieldException("Passwords need to match")
    if(afs dataExists("email", email, "userinfo"))
      throw new FieldException("Email Address Already Used")
    var accNo = generateRandom
    val balance = new BigDecimal("0")
    var PIN = ""
    for(n <- 1 to 4) PIN.+(Random.nextInt(9))
    val dis = DatabaseInteractionService
    dis executeUpdate(s"insert into accounts(accNo, balance, PIN) values('$accNo', $balance, '$PIN');")
    dis executeUpdate(s"insert into userinfo(accNo, pass, fName, lName, email, dob) values('$accNo', '$password', '$fName', '$lName', '$email', '00/00/0000');")
    dis executeUpdate(s"insert into transactions(accNo, transactions) values('$accNo', '');")
  }
  
  private[this] val afs = AccountFetchService
  private[this] def generateRandom(): String = {
    var accNo : String = ""
    for(a <- 1 to 8) accNo = accNo + (Random.nextInt(9))
    if(afs dataExists("accNo", accNo, "accounts"))
      generateRandom
    else  
      accNo
  }
}