package controllers

import play.api._
import play.api.mvc._
import models.Linkeduser

object Application extends Controller {
  
  def index = Action {
    Ok("hello world!")
  }

  def indexLinkedUsers = Action {
    val users = Linkeduser.all()
    Ok("Number of linkedusers: "+users.length)
  }

  def createLinkedUser = Action {
    Ok("create")
  }

  def recommendLinkedUsers(id: Long) = TODO
}