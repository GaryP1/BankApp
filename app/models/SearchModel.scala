package models

import play.api.data.Form
import play.api.data.Forms._

/**
 * Created by a585401 on 01/09/2015.
 */
case class SearchModel(searchCriteria : String, searchField : String)
object SearchForm{
  val searchForm : Form[SearchModel] = Form(mapping(
    "searchCriteria" ->  nonEmptyText.verifying(),
    "searchField" -> nonEmptyText.verifying())
    (SearchModel.apply)(SearchModel.unapply))
}
