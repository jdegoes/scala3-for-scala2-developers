import sbt.Keys._ 
import sbt._ 

val dottyVersion = "3.0.0-M2"

lazy val root = project
  .in(file(""))
  .settings(
    name := "scala3-for-scala2-developers",
    version := "0.1.0",
    scalacOptions ++= Seq(
      "-language:postfixOps",
      "-Ykind-projector",
      "-Yexplicit-nulls",
      "-source", "3.1"
    ),
    scalaVersion := dottyVersion
  )