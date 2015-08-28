package services

/**
 * @author a585401
 */

import models.Account

object AccountHandlerService {
  def deposit(account : Account, input : String){
    require(input matches "^[0-9]*$")
    val amount = BigDecimal valueOf(input toDouble)
    val ats = AccountTransferService
    ats depositToAccount(account, amount)
    account balance = (account.balance+amount)
  }
  
  def withdraw(account : Account, input : String){
    require(input matches "^[0-9]*$")
    val amount = BigDecimal valueOf(input toDouble)
    require(account.balance > amount)
    val ats = AccountTransferService
    ats withdrawFromAccount(account, amount)
    account balance = (account.balance-amount)
  }
  
  def transfer(account : Account, accountTo : Account, input : String){
    require(input matches "^[0-9]*$")
    val amount = BigDecimal valueOf(input toDouble)
    require(account.balance > amount)
    val ats = AccountTransferService
    ats transferToAccount(account, accountTo, amount)
    account balance = account.balance-amount
    accountTo balance = accountTo.balance+amount
  }
}