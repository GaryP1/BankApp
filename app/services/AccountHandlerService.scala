package services

/**
 * @author a585401
 */

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
    require(account.balance > amount)
    val ats = AccountTransferService
    ats transferToAccount(account, accountTo, amount)
    account balance = account.balance-amount
    accountTo balance = accountTo.balance+amount
  }
}