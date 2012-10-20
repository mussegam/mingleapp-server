package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Group(linkedId: Pk[Long], name: String)

object Group {
  val group = {
    get[Pk[Long]]("groups.linkedId") ~
    get[String]("groups.name") map {
      case linkedId~name => Group(linkedId, name)
    }
  }

  def all(): List[Group] = {
    DB.withConnection { implicit connection =>
      SQL("select * from groups").as(group *)
    }
  }

  def findById(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("select * from groups where linkedId = {id}").on("id" -> id).as(group.singleOpt)
    }
  }

  def create(group: Group) {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into groups values(
           {linkedId}, {name}
        """
      ).on(
        "linkedId" -> group.linkedId,
        "name" -> group.name
      ).executeUpdate()
    }
  }
}