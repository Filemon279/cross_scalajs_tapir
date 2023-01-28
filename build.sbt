ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = project.in(file(".")).
  aggregate(cross.js, cross.jvm).
  settings(
    idePackagePrefix := Some("com.pawscode.scalajstapir"),
    version := "0.1-SNAPSHOT",
    publish := {},
    publishLocal := {},
  )

lazy val cross = crossProject(JSPlatform, JVMPlatform).in(file(".")).
  settings(
    name := "cross_scalajs_tapir",
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.14.1",
      "io.circe" %%% "circe-generic" % "0.14.1",
      "com.softwaremill.sttp.tapir" %%% "tapir-core" % "1.2.7"
    )
  ).jvmSettings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % "1.2.7",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.20",
      "com.typesafe.akka" %% "akka-stream" % "2.6.20",
      "com.typesafe.akka" %% "akka-http" % "10.2.9",
      "de.heikoseeberger" %% "akka-http-circe" % "1.29.1",
      "ch.megard" %% "akka-http-cors" % "1.1.3"
    )
  ).jsSettings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %%% "core" % "3.6.2",
      "com.softwaremill.sttp.client3" %%% "circe" % "3.6.2",
      "com.softwaremill.sttp.tapir" %%% "tapir-sttp-client" % "1.2.7",
      "org.scala-js" %%% "scalajs-dom" % "2.1.0",
      "com.github.japgolly.scalajs-react" %%% "core" % "2.0.1",
      "com.github.japgolly.scalajs-react" %%% "extra" % "2.1.0",
      "com.github.japgolly.scalajs-react" %%% "extra-ext-monocle2" % "2.1.0",
      "dev.optics" %%% "monocle-core" % "3.1.0",
      "dev.optics" %%% "monocle-macro" % "3.1.0",
      "io.kinoplan" %%% "scalajs-react-material-ui-core"  % "0.3.1",
      "io.kinoplan" %%% "scalajs-react-material-ui-icons" % "0.3.1",
      "io.kinoplan" %%% "scalajs-react-material-ui-lab"   % "0.3.1"
    ),
    webpackBundlingMode := BundlingMode.LibraryAndApplication(),
    npmExtraArgs ++= Seq("--force"),
    scalaJSUseMainModuleInitializer := true,
    Compile / npmDependencies ++= Seq(
      "@material-ui/core" -> "3.9.4",
      "@material-ui/icons" -> "3.0.2",
      "@material-ui/lab" -> "3.0.0-alpha.30"
    )
  ).jsConfigure { project => project.enablePlugins(ScalaJSBundlerPlugin) }

Global / scalacOptions += "-Ymacro-annotations"
