organization := "com.charlie.world"
name := "scala-async-bigtable-client"

version := "0.1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "com.google.cloud.bigtable" % "bigtable-client-core" % "1.0.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test
)

parallelExecution in Test := false
fork in Test := false

