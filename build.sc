//| mill-version: 1.0.6
import mill._
import mill.scalalib._
import mill.scalajslib._
import mill.scalajslib.api._
import mill.scalalib.publish._

object custom extends ScalaJSModule with PublishModule {
  def scalaVersion = "3.3.6"
  def scalaJSVersion = "1.19.0"

  // Dependencies
  def mvnDeps = Seq(
    mvn"org.scala-js::scalajs-dom::2.8.1"
  )

  // Configure for ES6 module output
  def moduleKind = ModuleKind.ESModule

  // No main method for a library
  def scalaJSUseMainModuleInitializer = false

  // Publishing configuration
  def publishVersion = "0.1.0"
  def pomSettings = PomSettings(
    description = "A Scala.js base class for custom web components with lifecycle methods and utilities",
    organization = "io.github.lkikunte",
    url = "https://github.com/lkikunte/custom-html-element",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("lkikunte", "custom-html-element"),
    developers = Seq(
      Developer("lkikunte", "Lukindo Kikunt'e", "https://github.com/lkikunte")
    )
  )

  // Include documentation in the published artifact
  def docSources = T.sources {
    millSourcePath / "docs"
  }

  // Test module
  object test extends Tests {
    def mvnDeps = Seq(
      mvn"com.lihaoyi::utest::0.8.4" // Latest uTest version
    )
    def testFramework = "utest.runner.Framework"
  }
}