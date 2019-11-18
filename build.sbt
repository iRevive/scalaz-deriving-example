lazy val root = (project in file("."))
  .settings(
    name          := "scalaz-deriving-example",
    scalaVersion  := "2.13.1",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Seq(
      "io.circe"       %% "circe-core"     % "0.12.3",
      "io.circe"       %% "circe-generic"  % "0.12.3",
      "com.propensive" %% "magnolia"       % "0.12.0",
      "org.scalaz"     %% "deriving-macro" % "2.0.0-M3"
    ),
    addCompilerPlugin("org.scalaz" %% "deriving-plugin" % "2.0.0-M3" cross CrossVersion.full),
    // WORKAROUND for scalaz.deriving: https://github.com/sbt/sbt/issues/1965
    Compile / managedClasspath := {
      val res = (Compile / resourceDirectory).value
      val old = (Compile / managedClasspath).value
      Attributed.blank(res) +: old
    }
  )
