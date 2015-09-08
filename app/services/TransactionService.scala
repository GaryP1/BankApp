package services

/**
 * @author a585401
 */
class TransactionService {
  def reform(input : String) : String = {
    val parts = input split " "
    parts(1).substring(0,2) match{
      case "wd" => s"${parts(0)} withdrawn on ${parts(2)}"
      case "dt" => s"${parts(0)} deposited on ${parts(2)}"
      case "tr" => s"${parts(0)} transferred to ${parts(1) substring 2 } on ${parts(2)}"
      case "tf" => s"${parts(0)} transferred from ${parts(1) substring 2} on ${parts(2)}"
    }
  }
}