package com.zagrebelnyy.spray.server

import org.scalatest.{FlatSpec, ShouldMatchers}

import spray.routing.Directives
import spray.testkit.ScalatestRouteTest

class MainTest extends FlatSpec with ShouldMatchers with ScalatestRouteTest with Directives {
  it should "work" in {
    Get("/hello") ~>  Main.helloroute ~> check {
      responseAs[String] should include("Welcome to my main)")
    }

  }
}

