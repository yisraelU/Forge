import scala.collection.immutable.Seq
// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "io.github.yisraelu"
ThisBuild / organizationName := "Forge"
ThisBuild / startYear := Some(2023)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("yisraelu", "Yisrael Union")
)

Global / onChangedBuildSource := ReloadOnSourceChanges
// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

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

lazy val modules = List(avro,jsonSchema).flatMap(_.projectRefs)

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
