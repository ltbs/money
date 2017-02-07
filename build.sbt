name := "money"

version := "1.0-SNAPSHOT"

lazy val scalaV = "2.11.8"

scalaVersion in ThisBuild := scalaV
scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "org.typelevel" %% "cats-core" % "0.9.0",
  "com.github.mpilquist" %% "simulacrum" % "0.10.0",  
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "io.github.amrhassan" %% "scalacheck-cats" % "0.3.2" % Test,
  "org.typelevel" %% "discipline" % "0.7.3" % Test
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
