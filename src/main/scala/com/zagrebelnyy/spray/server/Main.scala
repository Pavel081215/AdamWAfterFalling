package com.zagrebelnyy.spray.server

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.zagrebelnyy.spray.{Drug, DrugSecond}
import spray.http.MediaTypes
import spray.routing.{RequestContext, Route, SimpleRoutingApp}

import scala.concurrent.duration._


object Main extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem()

  import actorSystem.dispatcher

  implicit val timeout = Timeout(1.second)

  var plentyOfDrugs = Drug.drugs


  lazy val helloActor = actorSystem.actorOf(Props(new FirstActor))
  lazy val burnActor = actorSystem.actorOf(Props(new BurnActor))

  lazy val helloroute = get {
    path("hello") {
      complete {
        "Welcome to my main)))))))))))))))))"
      }
    }
  }
  lazy val helloroute2 = get {
    path("hello2") {

      ctx => helloActor ! ctx
    }
  }

  lazy val burnRoute = get {
    path("burn" / "remaining") {
      complete {
        (burnActor ? RemainingBurningTime)
          .mapTo[Int]
          .map(s => s"The remaining burning time is $s")
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


  startServer(interface = "Localhost", port = 8080) {
      helloroute ~
      helloroute2 ~
      burnRoute ~
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

  class BurnActor extends Actor {
    val remainingTime = 10

    override def receive = {
      case RemainingBurningTime => sender ! remainingTime
    }
  }

  object RemainingBurningTime

}
