package models

import exceptions.FieldException
import play.api.data.validation.{Valid, Constraint}
import services.AccountFetchService

/**
 * Created by a585401 on 07/09/2015.
 */
object Constraints {
  def numberConstraint : Constraint[String] = Constraint(""){
    input =>
      if(input matches "[0-9]+")
        Valid
      else
        throw new exceptions.FieldException("Numbers only!")
  }

  def accountExists : Constraint[String] = Constraint(""){
    input =>
      if(AccountFetchService dataExists("accNo", input, "accounts"))
        Valid
      else
        throw new FieldException("Account Doesn't Exist")
  }
  def nameConstraints: Constraint[String] = Constraint(""){
    input =>
      if(input matches "[A-Za-z]+")
        Valid
      else
        throw new exceptions.FieldException("Name has to be letters only")
  }

  def emailConstraints: Constraint[String] = Constraint(""){
    input =>
      if(input matches "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
        Valid
      else
        throw new exceptions.FieldException("Email is in incorrect format")
  }

  def passwordConstraints : Constraint[String] = Constraint(""){
    input =>
      if(input matches "[A-Za-z0-9]{8,21}")
        Valid
      else
        throw new exceptions.FieldException("Password needs an upper and lower case letter and a number")
  }

  def emailExists : Constraint[String] = Constraint(""){
    input =>
      if(AccountFetchService dataExists("email", input, "userinfo"))
        Valid
      else
        throw new FieldException("Email not recognized")
  }

  def emailNotExist : Constraint[String] = Constraint(""){
    input =>
      if(!(AccountFetchService dataExists("email", input, "userinfo")))
        Valid
      else
        throw new FieldException("Email not recognized")
  }
}
