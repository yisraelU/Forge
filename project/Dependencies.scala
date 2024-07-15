import sbt._
import org.portablescala.sbtplatformdeps.PlatformDepsGroupID._
object Dependencies {

  object Smithy {
    val version = "1.46.0"
    val build = "software.amazon.smithy" % "smithy-build" % version
  }

  object Smithy4s {
    val version = "0.18.15"
    val core = "com.disneystreaming.smithy4s" %% "smithy4s-core" % version

  }

}
