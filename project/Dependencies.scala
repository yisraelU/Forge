import sbt._
object Dependencies {

  object Smithy {
    val version = "1.60.2"
    val build   = "software.amazon.smithy" % "smithy-build" % version
  }
}
