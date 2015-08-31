package models

/**
 * @author a585401
 */

class Account(val accNo:String,val  PIN:String,var balance:BigDecimal,val pass:String,val  fName:String,val lName:String,val email:String, val transactions : Array[String]){
  def loginPass(passIn : String) : Boolean = {pass equals passIn}
}