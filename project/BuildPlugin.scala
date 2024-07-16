import org.typelevel.sbt.TypelevelCiPlugin.autoImport.{
  tlCiDependencyGraphJob,
  tlCiDocCheck,
  tlCiHeaderCheck,
  tlCiMimaBinaryIssueCheck,
  tlCiScalafixCheck
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

  override def projectSettings: Seq[Setting[_]] =
    scalafixSettings ++ tlCiSettings

  private lazy val reloadSetting =
    Global / onChangedBuildSource := ReloadOnSourceChanges

  private lazy val scalafixSettings = Seq(
    // Scalafix configuration
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
  )

  lazy val tlCiSettings = Seq(
    // https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
    ThisBuild / tlBaseVersion := "0.0", // your current series x.y
    ThisBuild / organization := "io.github.yisraelu",
    ThisBuild / organizationName := "Smithy-Forge",
    ThisBuild / startYear := Some(2024),
    ThisBuild / licenses := Seq(License.Apache2),
    ThisBuild / developers := List(
      // your GitHub handle and name
      tlGitHubDev("yisraelu", "Yisrael Union")
    ),
    ThisBuild / tlCiHeaderCheck := false,
    ThisBuild / tlFatalWarnings := false,
    ThisBuild / tlCiMimaBinaryIssueCheck := false,
    ThisBuild / sonatypeProfileName := "io.github.yisraelu",
    ThisBuild / tlJdkRelease := Some(17),
    ThisBuild / tlCiDependencyGraphJob := false,
    // publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
    ThisBuild / tlSonatypeUseLegacyHost := false,
    ThisBuild / tlCiScalafixCheck := false,
    ThisBuild / tlCiDocCheck := false,
    ThisBuild / tlCiMimaBinaryIssueCheck := false
  )

}
