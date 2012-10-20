package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Linkeduser(linkedId: Pk[Long], name: String, headline: String, picture: String, numConnections: Int, lastPosition: String)

object Linkeduser {
  val linkeduser = {
    get[Pk[Long]]("linkedusers.linkedId") ~
    get[String]("linkedusers.name") ~
    get[String]("linkedusers.headline") ~
    get[String]("linkedusers.picture") ~
    get[Int]("linkedusers.numConnections") ~
    get[String]("linkedusers.lastPosition") map {
      case linkedId~name~headline~picture~numConnections~lastPosition => Linkeduser(linkedId, name, headline, picture, numConnections, lastPosition)
    }
  }

  def all(): List[Linkeduser] = {
    DB.withConnection { implicit connection =>
      SQL("select * from linkedusers").as(linkeduser *)
    }
  }

  def findById(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("select * from linkedusers where linkedId = {id}").on("id" -> id).as(linkeduser.singleOpt)
    }
  }

  def create(user: Linkeduser) {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into groups values(
           {linkedId}, {name}, {headline}, {picture}, {numConnections}, {lastPosition}
        """
      ).on(
        "linkedId" -> user.linkedId,
        "name" -> user.name,
        "headline" -> user.headline,
        "picture" -> user.picture,
        "numConnections" -> user.numConnections,
        "lastPosition" -> user.lastPosition
      ).executeUpdate()
    }
  }
}