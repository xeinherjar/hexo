import Dependencies._

val scala3Version = "3.1.3"
enablePlugins(ScalaJSPlugin)

val catsVersion = "2.+"
val catsEffectVersion = "3.+"

lazy val root = project
  .in(file("."))
  .settings(
    name := "hexo",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-Xsemanticdb", "-Xfatal-warnings"),
    ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.+",
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % catsVersion,
      "org.typelevel" %%% "cats-effect" % catsEffectVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.1.0",
      "co.fs2" %%% "fs2-core" % "3.1.+",
      "com.disneystreaming" %%% "weaver-cats" % "0.+" % Test
    )
  )
