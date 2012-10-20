package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import play.api.libs.json._

case class Linkeduser(id: Pk[Long], linkedId: String, name: String, headline: String, picture: String, numConnections: Int, lastPosition: String)

object Linkeduser {
  val linkeduser = {
    get[Pk[Long]]("linkedusers.id") ~
    get[String]("linkedusers.linkedId") ~
    get[String]("linkedusers.name") ~
    get[String]("linkedusers.headline") ~
    get[String]("linkedusers.picture") ~
    get[Int]("linkedusers.numConnections") ~
    get[String]("linkedusers.lastPosition") map {
      case id~linkedId~name~headline~picture~numConnections~lastPosition => Linkeduser(id, linkedId, name, headline, picture, numConnections, lastPosition)
    }
  }

  def all(): List[Linkeduser] = {
    DB.withConnection { implicit connection =>
      SQL("select * from linkedusers").as(linkeduser *)
    }
  }

  def findById(id: String) = {
    DB.withConnection { implicit connection =>
      SQL("select * from linkedusers where linkedId = {id}").on("id" -> id).as(linkeduser.singleOpt)
    }
  }

  def create(user: Linkeduser) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
           insert into groups values(
           (select next value for linkedusers_id_seq),
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

  def createLinkedUserFromJson(json: JsValue) = {
    // Getting the user data
    val linkedId = (json \ "id").as[String]
    val firstName = (json \ "firstName").as[String]
    val lastName = (json \ "lastName").as[String]
    val fullName = new String(firstName + " " + lastName)
    val headline = (json \ "headline").as[String]
    val pictureURL = (json \ "pictureUrl").as[String]
    var numConnections = (json \ "numConnections").as[Int]
    if ((json \ "numConnectionsCapped").as[Boolean]) numConnections = numConnections + 1

    val positionList = (json \ "positions" \ "values").as[List[JsValue]]
    val lastPosition = positionList.filter(x => (x \ "isCurrent").as[Boolean])
    val lastPositionTitle = (lastPosition.head \ "title").as[String]
    println(lastPositionTitle)

    // Getting the related entities
    val skills = (json \ "skills" \ "values").as[List[JsValue]].map(x => ((x \ "id").as[Int], (x \ "skill" \ "name").as[String]))
    val newsList = (json \ "suggestions" \ "toFollow" \ "newsSources" \ "values").as[List[JsValue]]
    val newsSources = newsList.map(x => ((x \ "id").as[Int], (x \ "name").as[String]))
    val groupsList = (json \ "groupMemberships" \ "values").as[List[JsValue]]
    val groups = groupsList.map(x => ((x \ "group" \ "id").as[String].toInt, (x \ "group" \ "name").as[String]))

    val newUser = new Linkeduser(anorm.NotAssigned, linkedId, fullName, headline, pictureURL, numConnections, lastPositionTitle)
  }
}