import scala.collection.immutable.Seq

Global / onChangedBuildSource := ReloadOnSourceChanges

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")
val Scala213 = Seq("2.13.13")
val Scala3 = Seq("3.3.3")
val LatestTwoScala = Scala213 ++ Scala3

lazy val root = projectMatrix
  .in(file("."))
  .settings(
    name := "smithy-forge"
  )
  .enablePlugins(NoPublishPlugin)
  .aggregate((modules :+ docs.project).map(_.project): _*)

lazy val modules = List(avro, jsonSchema).flatMap(_.projectRefs)

lazy val avro = projectMatrix
  .in(file("modules/avro"))
  .jvmPlatform(autoScalaLibrary = false)
  .settings(
    name := "avro",
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.46.0"
    ),
    Compile / packageSrc / mappings := (Compile / packageSrc / mappings).value
      .filterNot { case (file, path) =>
        path.equalsIgnoreCase("META-INF/smithy/manifest")
      },
    resolvers += Resolver.mavenLocal,
    javacOptions ++= Seq("--release", "11")
  )

lazy val jsonSchema = projectMatrix
  .in(file("modules/jsonSchema"))
  .jvmPlatform(autoScalaLibrary = false)
  .settings(
    name := "json-schema",
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.46.0"
    ),
    Compile / packageSrc / mappings := (Compile / packageSrc / mappings).value
      .filterNot { case (file, path) =>
        path.equalsIgnoreCase("META-INF/smithy/manifest")
      },
    resolvers += Resolver.mavenLocal,
    javacOptions ++= Seq("--release", "11")
  )

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)
