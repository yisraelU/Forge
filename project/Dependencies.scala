import sbt._
object Dependencies {

  object Smithy {
    val version = "1.65.0"
    val build   = "software.amazon.smithy" % "smithy-build" % version
  }
}
