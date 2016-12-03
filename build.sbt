name := "AdamWAfterFaling"



scalaVersion := "2.11.2"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.1"
  Seq(


    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" % "akka-http-experimental_2.11" % "0.7",
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "org.json4s" % "json4s-native_2.11" % "3.2.10",
    "com.typesafe.scala-logging" % "scala-logging-slf4j_2.11" % "2.1.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test",
    "org.mockito" % "mockito-all" % "1.9.5"


  )
}
    