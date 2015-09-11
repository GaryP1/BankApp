package services

/**
 * Created by a585401 on 11/09/2015.
 */
object AccountEditingService {

  def removeAccount(accNo : String) = {
    DatabaseInteractionService executeUpdate s"delete from accounts where accNo = $accNo"
  }

  def editAccountField(accNo : String, data : String, columnName : String)={
    columnName match{
      case "PIN" => DatabaseInteractionService executeUpdate s"update accounts set PIN = '$data' where accNo = $accNo"
      case "pass" => DatabaseInteractionService executeUpdate s"update userinfo set pass = '$data' where accNo = $accNo"
      case "fName" => DatabaseInteractionService executeUpdate s"update userinfo set fName = '$data' where accNo = $accNo"
      case "lName" => DatabaseInteractionService executeUpdate s"update userinfo set lName = '$data' where accNo = $accNo"
      case "email" => DatabaseInteractionService executeUpdate s"update userinfo set email = '$data' where accNo = $accNo"
    }
  }
}
