package com.zagrebelnyy.spray.server

import akka.actor.ActorSystem
import com.zagrebelnyy.spray._
import spray.http.MediaTypes

import spray.routing.{Route, SimpleRoutingApp}


object Main extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem()
  var plentyOfDrugs = Drug.drugs

  lazy val helloroute = {
    path("hello") {
      complete {
        "Welcome to my main)))))))))))))))))"
      }
    }
  }

  def getJson(route: Route): Route = {
    get {
      respondWithMediaType(MediaTypes.`application/json`) {
        route
      }
    }
  }

  //startServer(interface = "Localhost", port = 8080) {
  startServer(interface = "Localhost", port = 8080) {
    helloroute ~
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


}
