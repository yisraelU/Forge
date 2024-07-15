import org.typelevel.sbt.TypelevelCiPlugin.autoImport.{
  tlCiDependencyGraphJob,
  tlCiHeaderCheck,
  tlCiMimaBinaryIssueCheck
}
import org.typelevel.sbt.TypelevelGitHubPlugin.autoImport.tlGitHubDev
import org.typelevel.sbt.TypelevelSettingsPlugin.autoImport.{
  tlFatalWarnings,
  tlJdkRelease
}
import org.typelevel.sbt.TypelevelSonatypePlugin.autoImport.tlSonatypeUseLegacyHost
import org.typelevel.sbt.TypelevelVersioningPlugin.autoImport.tlBaseVersion
import sbt.Keys.*
import sbt.nio.Keys.{ReloadOnSourceChanges, onChangedBuildSource}
import sbt.{ThisBuild, *}
import scalafix.sbt.ScalafixPlugin.autoImport.{
  scalafixScalaBinaryVersion,
  scalafixSemanticdb
}
import xerial.sbt.Sonatype.autoImport.sonatypeProfileName

object BuildPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def globalSettings: Seq[Def.Setting[_]] = Seq(reloadSetting) ++
    addCommandAlias("lint", "; scalafmtSbt; scalafmtAll; scalafixAll; ")

  override def projectSettings: Seq[Setting[_]] = Seq(
    organization := "com.caesars.pam",
    scalacOptions ++= compilerOptions(scalaVersion.value)
  ) ++ scalafixSettings ++ compilerPlugins ++ tlCiSettings

  private lazy val reloadSetting =
    Global / onChangedBuildSource := ReloadOnSourceChanges

  private lazy val scalafixSettings = Seq(
    ThisBuild / scalafixScalaBinaryVersion := "2.13",
    // Scalafix configuration
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
  )

  lazy val tlCiSettings = Seq(
    // https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
    ThisBuild / tlBaseVersion := "0.0", // your current series x.y
    ThisBuild / organization := "io.github.yisraelu",
    ThisBuild / organizationName := "Forge",
    ThisBuild / startYear := Some(2023),
    ThisBuild / licenses := Seq(License.Apache2),
    ThisBuild / developers := List(
      // your GitHub handle and name
      tlGitHubDev("yisraelu", "Yisrael Union")
    ),
    ThisBuild / tlCiHeaderCheck := false,
    ThisBuild / tlFatalWarnings := false,
    ThisBuild / tlCiMimaBinaryIssueCheck := false,
    ThisBuild / sonatypeProfileName := "io.github.yisraelu",
    ThisBuild / tlJdkRelease := Some(11),
    ThisBuild / tlCiDependencyGraphJob := false,
    // publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
    ThisBuild / tlSonatypeUseLegacyHost := false
  )

  lazy val compilerPlugins = Seq(
    libraryDependencies ++= {
      if (scalaVersion.value.startsWith("2."))
        Seq(
          compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
          compilerPlugin(
            "org.typelevel" % "kind-projector" % "0.13.3" cross CrossVersion.full
          )
        )
      else Seq.empty
    }
  )

  lazy val commonCompilerOptions = Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:postfixOps", // Allow postfix operators
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    //    "-Ywarn-dead-code",              // Warn when dead code is identified.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
    //   "-Ywarn-unused:locals",          // Warn if a local definition is unused.
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
    //   "-Ywarn-unused:privates",        // Warn if a private member is unused.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Xfatal-warnings" // Fail the compilation if there are any warnings.
  )

  def filterScala3Options(opts: Seq[String]) =
    ("-Ykind-projector" +: opts)
      .filterNot(_.startsWith("-Xlint"))
      .filterNot(_.startsWith("-Ywarn-"))
      .filterNot(_ == "-explaintypes")
      .filterNot(_ == "-Xcheckinit")

  def compilerOptions(scalaVersion: String) = {
    if (scalaVersion.startsWith("3."))
      filterScala3Options(commonCompilerOptions)
    else
      commonCompilerOptions
  }
}
