package com.zagrebelnyy.spray.server

import akka.actor.{Props, Actor, ActorSystem}
import com.zagrebelnyy.spray._
import spray.http.MediaTypes

import spray.routing.{RequestContext, Route, SimpleRoutingApp}


object Main extends App with SimpleRoutingApp {

  implicit val actorSystem = ActorSystem()


  var plentyOfDrugs = Drug.drugs


  lazy val helloActor = actorSystem.actorOf(Props(new FirstActor))

  lazy val helloroute = {
    path("hello") {
      complete {
        "Welcome to my main)))))))))))))))))"
      }
    }
  }
  lazy val helloroute2 = {
    path("hello2") {

      ctx => helloActor ! ctx
    }
  }


  def getJson(route: Route): Route = {
    get {
      respondWithMediaType(MediaTypes.`application/json`) {
        route
      }
    }
  }


  startServer(interface = "Localhost", port = 8080) {
    helloroute ~
      helloroute2 ~
      getJson {
        path("list" / "all") {
          complete {
            Drug.toJson(plentyOfDrugs)
          }
        }
      } ~
      get {
        path("drug" / IntNumber / "detail") { index =>
          complete {
            Drug.toJson(plentyOfDrugs(index))
          }
        }
      } ~
      post {
        path("drug" / "addall") {
          parameter("name".as[String], "age".as[Int]) { (name, age) =>
            val newDrug = DrugSecond(name, age)
            plentyOfDrugs = newDrug :: plentyOfDrugs
            complete {
              "OK"
            }
          }
        }
      } ~
      post {
        path("drug" / "add") {
          parameter("name" ?, "age".as[Int]) { (name, age) =>
            val newDrug = DrugSecond(name.getOrElse("NewDrug"), age)
            plentyOfDrugs = newDrug :: plentyOfDrugs
            complete {
              "OK"
            }
          }
        }
      }
  }

  class FirstActor extends Actor {
    override def receive = {
      case ctx: RequestContext => ctx.complete("First Actor!!!!!!!!!!!!!!!!!!!!! SUPER")

    }

  }


}
