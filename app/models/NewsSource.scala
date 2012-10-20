package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class NewsSource(linkedId: Pk[Long], name: String)

object NewsSource {
  val newssource = {
    get[Pk[Long]]("newssources.linkedId") ~
    get[String]("newssources.name") map {
      case linkedId~name => NewsSource(linkedId, name)
    }
  }

  def all(): List[NewsSource] = {
    DB.withConnection { implicit connection =>
      SQL("select * from newssources").as(newssource *)
    }
  }

  def findById(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("select * from newssources where linkedId = {id}").on("id" -> id).as(newssource.singleOpt)
    }
  }

  def create(newssource: NewsSource) {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into newssources values(
           {linkedId}, {name}
        """
      ).on(
        "linkedId" -> newssource.linkedId,
        "name" -> newssource.name
      ).executeUpdate()
    }
  }
}