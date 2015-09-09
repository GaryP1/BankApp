package models

/**
 * Created by a585401 on 04/09/2015.
 */

import play.api.data.Form
import play.api.data.Forms._
import models.Constraints._

case class TransactionData1(amount : String)
case class TransactionData2(accNoTo : String, amount : String)
object TransactionData{
  val transactionData1 : Form[TransactionData1]=Form(mapping(
    "amount" -> nonEmptyText.verifying(numberConstraint))
    (TransactionData1.apply)(TransactionData1.unapply))

  val transactionData2 : Form[TransactionData2]=Form(mapping(
    "accNoTo" -> nonEmptyText.verifying(accountExists),
    "amount" -> nonEmptyText.verifying(numberConstraint))
  (TransactionData2.apply)(TransactionData2.unapply))


}
