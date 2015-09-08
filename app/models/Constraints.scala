package models

import exceptions.FieldException
import play.api.data.validation.{Valid, Constraint}
import services.AccountFetchService

/**
 * Created by a585401 on 07/09/2015.
 */
object Constraints {

  @throws(classOf[FieldException])
  def numberConstraint : Constraint[String] = Constraint(""){
    input =>
      if(input matches "[0-9]+")
        Valid
      else
        throw new FieldException("Numbers only!")
  }

  @throws(classOf[FieldException])
  def accountExists : Constraint[String] = Constraint(""){
    input =>
      if(AccountFetchService dataExists("accNo", input, "accounts"))
        Valid
      else
        throw new FieldException("Account Doesn't Exist")
  }

  @throws(classOf[FieldException])
  def nameConstraints: Constraint[String] = Constraint(""){
    input =>
      if(input matches "[A-Za-z]+")
        Valid
      else
        throw new FieldException("Name has to be letters only")
  }

  @throws(classOf[FieldException])
  def emailConstraints: Constraint[String] = Constraint(""){
    input =>
      if(input matches "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
        Valid
      else
        throw new FieldException("Email is in incorrect format")
  }

  @throws(classOf[FieldException])
  def passwordConstraints : Constraint[String] = Constraint(""){
    input =>
      if(input matches "[A-Za-z0-9]{8,21}")
        Valid
      else
        throw new FieldException("Password needs an upper and lower case letter and a number")
  }

  @throws(classOf[FieldException])
  def emailExists : Constraint[String] = Constraint(""){
    input =>
      if(AccountFetchService dataExists("email", input, "userinfo"))
        Valid
      else
        throw new FieldException("Email not recognized")
  }

  @throws(classOf[FieldException])
  def emailNotExist : Constraint[String] = Constraint(""){
    input =>
      if(!(AccountFetchService dataExists("email", input, "userinfo")))
        Valid
      else
        throw new FieldException("Email not recognized")
  }
}
