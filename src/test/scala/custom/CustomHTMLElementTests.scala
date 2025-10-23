package custom

import utest._
import org.scalajs.dom.HTMLElement
import custom.CustomHTMLElement

object CustomHTMLElementTests extends TestSuite {
  val tests = Tests {
    test("Basic lifecycle") {
      val elem = new CustomHTMLElement {}
      assert(elem.isInstanceOf[HTMLElement])
    }

    test("Attribute observation") {
      val elem = new CustomHTMLElement {
        override val observedAttributes: js.Array[String] = js.Array("test-attr")
      }
      assert(elem.observedAttributes.contains("test-attr"))
    }

    test("Shadow DOM initialization") {
      val elem = new CustomHTMLElement {
        override protected def useShadowRoot: Boolean = true
      }
      assert(elem.shadowRoot.isDefined)
    }
  }
}