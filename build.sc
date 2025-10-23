// Core Mill build system imports
import mill._
import scalalib._
import scalajslib._
import scalajslib.api._

// Publishing imports for creating a reusable library
import mill.scalalib.publish._

object custom extends ScalaJSModule with PublishModule {
  def scalaVersion = "3.3.6"
  def scalaJSVersion = "1.19.0"

  // Dependencies (minimal, only scalajs-dom required for CustomHTMLElement)
  def mvnDeps = Seq(
    mvn"org.scala-js::scalajs-dom::2.8.1"
  )

  // Configure for ES6 module output
  def moduleKind = ModuleKind.ESModule

  def scalaJSUseMainModuleInitializer = false

  // Publishing configuration (for local or remote repository)
  def publishVersion = "0.1.0"
	def pomSettings = PomSettings(
		description = "A Scala.js base class for custom web components with lifecycle methods and utilities",
		organization = "io.github.lkikunte",  // open-source
		url = "https://github.com/lkikunte/custom-html-element",
		licenses = Seq(License.MIT),
		versionControl = VersionControl.github("lkikunte", "custom-html-element"),
		developers = Seq(
			Developer("lkikunte", "Lukindo Kikunt'e", "https://github.com/lkikunte")
		)
)
  )

  // Include documentation in the published artifact
  def docSources = T.sources {
    millSourcePath / "docs"
  }
}

import utest._
import custom.CustomHTMLElement

object CustomHTMLElementTests extends TestSuite {
  val tests = Tests {
    test("Basic lifecycle") {
      val elem = new CustomHTMLElement {}
      assert(elem.isInstanceOf[HTMLElement])
    }
  }
}