package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Skill(linkedId: Pk[Long], name: String)

object Skill {
  val skill = {
      get[Pk[Long]]("skills.linkedId") ~
      get[String]("skills.name") map {
      case linkedId~name => Skill(linkedId, name)
    }
  }

  def all(): List[Skill] = {
    DB.withConnection { implicit connection =>
      SQL("select * from skills").as(skill *)
    }
  }

  def findById(id: Long) = {
    DB.withConnection { implicit connection =>
      SQL("select * from skills where linkedId = {id}").on("id" -> id).as(skill.singleOpt)
    }
  }

  def create(skill: Skill) {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into skills values(
           {linkedId}, {name}
        """
      ).on(
        "linkedId" -> skill.linkedId,
        "name" -> skill.name
      ).executeUpdate()
    }
  }

  def link(id_skill: Long, id_user: Long) {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into skills_users values(
           {skill_id}, {linkeduser_id})
        """
      ).on(
        "skill_id" -> id_skill, 
        "linkeduser_id" -> id_user
      ).executeUpdate()
    }
   }
}
