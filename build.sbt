import scala.collection.immutable.Seq

Global / onChangedBuildSource := ReloadOnSourceChanges
// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")
// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y
ThisBuild / organization := "io.github.yisraelu"
ThisBuild / organizationName := "Forge"
ThisBuild / startYear := Some(2024)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("yisraelu", "Yisrael Union")
)
ThisBuild / tlCiHeaderCheck := false
ThisBuild / tlFatalWarnings := false
ThisBuild / tlCiMimaBinaryIssueCheck := false
ThisBuild / sonatypeProfileName := "io.github.yisraelu"
ThisBuild / tlJdkRelease := Some(11)
ThisBuild / tlCiDependencyGraphJob := false
// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false
ThisBuild / tlCiScalafixCheck := false
ThisBuild / tlCiDocCheck := false
ThisBuild / tlCiMimaBinaryIssueCheck := false

val projectPrefix = "forge"
lazy val root     = project
  .in(file("."))
  .settings(
    name := "forge"
  )
  .settings(commonSettings)
  .enablePlugins(NoPublishPlugin)
  .aggregate(modules.map(_.project): _*)

lazy val modules = List(avro, jsonSchema, docs)

lazy val avro = project
  .in(file("modules/avro"))
  .settings(
    name := s"$projectPrefix-avro"
  )
  .settings(commonSettings)

lazy val jsonSchema = project
  .in(file("modules/jsonSchema"))
  .settings(
    name := s"$projectPrefix-jsonschema"
  )
  .settings(commonSettings)

lazy val docs = project
  .in(file("site"))
  .settings(commonSettings)
  .enablePlugins(TypelevelSitePlugin)

lazy val commonSettings =
  Seq(
    javacOptions := Seq.empty,
    libraryDependencies ++= Seq(
      "software.amazon.smithy" % "smithy-build" % "1.54.0"
    ),
    Compile / packageSrc / mappings := (Compile / packageSrc / mappings).value
      .filterNot { case (file, path) =>
        path.equalsIgnoreCase("META-INF/smithy/manifest")
      },
    resolvers += Resolver.mavenLocal,
    javacOptions ++= Seq("--release", "11"),
    crossPaths := false,
    autoScalaLibrary := false
  )

addCommandAlias("lint", ";scalafmtAll;scalafmtSbt;")
