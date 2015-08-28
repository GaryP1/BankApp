package services

import models.Account

/**
 * @author a585401
 */

object AccountTransferService {
  def depositToAccount(account : Account, amount : BigDecimal){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update accounts set balance = ${account.balance + amount} where accNo = '${account.accNo}';"
  }
  
  def withdrawFromAccount(account : Account, amount : BigDecimal){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update accounts set balance = ${account.balance - amount} where accNo = '${account.accNo}';"
  }
  
  def transferToAccount(account : Account, accountTo : Account, amount : BigDecimal){
    withdrawFromAccount(account, amount)
    depositToAccount(accountTo, amount)
  }
}