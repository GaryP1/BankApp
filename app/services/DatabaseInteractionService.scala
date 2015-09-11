package services

/**
 * @author a585401
 */

import play.api.Play.current
import play.api.db._


object DatabaseInteractionService {
  def executeUpdate(query : String){
    DB withConnection { con =>
      try{
        val stmt = con.createStatement
        stmt executeUpdate query
      }finally{
        con close
      }
    }
  }
}