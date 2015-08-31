package services

import models.Account

/**
 * @author a585401
 */

object AccountTransferService {
  val ats = AccountTransactionService
  def depositToAccount(account : Account, amount : BigDecimal){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update accounts set balance = ${account.balance + amount} where accNo = '${account accNo}';"
    ats.updateDeposit(account accNo, amount toString)
  }
  
  def withdrawFromAccount(account : Account, amount : BigDecimal){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update accounts set balance = ${account.balance - amount} where accNo = '${account accNo}';"
    ats.updateWithdraw(account accNo, amount toString)
  }
  
  def transferToAccount(account : Account, accountTo : Account, amount : BigDecimal){
    val dis = DatabaseInteractionService
    dis executeUpdate s"update accounts set balance = ${account.balance - amount} where accNo = '${account accNo}';"
    dis executeUpdate s"update accounts set balance = ${accountTo.balance + amount} where accNo = '${accountTo accNo}';"
    ats.updateTransfer(account accNo, accountTo accNo, amount toString)
  }
}