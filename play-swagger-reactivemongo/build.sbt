name := "play-swagger-reactivemongo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

val reactiveMongoVer = "1.1.0-play28-RC9"

libraryDependencies ++= Seq(
  guice,
  "org.reactivemongo"      %% "play2-reactivemongo" % reactiveMongoVer,
  "org.scalatestplus.play" %% "scalatestplus-play"  % "5.1.0" % Test
)

import play.sbt.routes.RoutesKeys

RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
