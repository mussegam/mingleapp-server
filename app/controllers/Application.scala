package controllers

import play.api.mvc._
import models.Linkeduser

object Application extends Controller {
  
  def index = Action {
    Ok("hello world!")
  }

  def indexLinkedUsers = Action {
    val users = Linkeduser.all()
    Ok("Number of linkedusers: " + users.length)
  }

  def createLinkedUser = Action { request =>
    request.body.asJson.map { json =>
      Linkeduser.createLinkedUserFromJson(json)
    }
    Ok("create")
  }

  def recommendLinkedUsers(id: String) = TODO
}