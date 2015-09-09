package services

/**
 * @author a585401
 */

import exceptions.FieldException
import models.Account

object AccountHandlerService {
  def deposit(account : Account, input : String){
    val amount = BigDecimal valueOf(input toDouble)
    AccountTransferService depositToAccount(account, amount)
    account balance = (account.balance+amount)
  }
  
  def withdraw(account : Account, input : String){
    val amount = BigDecimal valueOf(input toDouble)
    require(account.balance > amount)
    AccountTransferService withdrawFromAccount(account, amount)
    account balance = (account.balance-amount)
  }
  
  def transfer(account : Account, accountTo : Account, input : String){
    val amount = BigDecimal valueOf(input toDouble)
    if(account.balance <= amount)
      throw new FieldException("Cannot send more money than you currently have")
    else if(account.accNo equals accountTo.accNo)
      throw new FieldException("Cannot send money to self")
    val ats = AccountTransferService
    ats transferToAccount(account, accountTo, amount)
    account balance = account.balance-amount
    accountTo balance = accountTo.balance+amount
  }
}