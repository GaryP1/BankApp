package services

import models.Account

/**
 * @author a585401
 */

object AccountTransferService {
  def depositToAccount(account : Account, amount : BigDecimal){
    DatabaseInteractionService executeUpdate s"update accounts set balance = ${account.balance + amount} where accNo = '${account accNo}';"
    AccountTransactionService updateSimple(account accNo, amount toString, "dt")
  }
  
  def withdrawFromAccount(account : Account, amount : BigDecimal){
    DatabaseInteractionService executeUpdate s"update accounts set balance = ${account.balance - amount} where accNo = '${account accNo}';"
    AccountTransactionService updateSimple(account accNo, amount toString, "wd")
  }
  
  def transferToAccount(account : Account, accountTo : Account, amount : BigDecimal){
    DatabaseInteractionService executeUpdate s"update accounts set balance = ${account.balance - amount} where accNo = '${account accNo}';"
    DatabaseInteractionService executeUpdate s"update accounts set balance = ${accountTo.balance + amount} where accNo = '${accountTo accNo}';"
    AccountTransactionService updateTransfer(account accNo, accountTo accNo, amount toString)
  }
}