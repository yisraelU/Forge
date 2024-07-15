import scala.collection.immutable.Seq

Global / onChangedBuildSource := ReloadOnSourceChanges

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

lazy val root = project
  .in(file("."))
  .settings(
    name := "smithy-forge"
  )
  .enablePlugins(NoPublishPlugin)
  .aggregate(modules.map(_.project): _*)

lazy val modules = List(avro, jsonSchema,docs)

lazy val avro = project
  .in(file("modules/avro"))
  .settings(
    name := "avro",
  ).settings(commonSettings)

lazy val jsonSchema = project
  .in(file("modules/jsonSchema"))
  .settings(
    name := "json-schema",
  ).settings(commonSettings)

lazy val docs = project.in(file("site")).enablePlugins(TypelevelSitePlugin)

lazy val commonSettings  =
  Seq(  libraryDependencies ++= Seq(
    "software.amazon.smithy" % "smithy-build" % "1.49.0"
  ),
    Compile / packageSrc / mappings := (Compile / packageSrc / mappings).value
      .filterNot { case (file, path) =>
        path.equalsIgnoreCase("META-INF/smithy/manifest")
      },
    resolvers += Resolver.mavenLocal,
    javacOptions ++= Seq("--release", "11"))
