package custom

import org.scalajs.dom.{HTMLElement, ShadowRoot, CustomEvent, CustomEventInit}
import org.scalajs.dom.raw.{Element, Document}
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.|

@JSExportTopLevel("CustomHTMLElement")
class CustomHTMLElement extends HTMLElement {
  // Essential: Constructor for initialization
  def this() = {
    this()
    // Initialize shadow DOM if enabled (default: open mode)
    if (useShadowRoot) {
      _shadowRoot = this.attachShadow(ShadowRootInit(mode = "open"))
    }
    // Optional: Initialize internal state or call render
    initialize()
  }

  // Desired: Shadow DOM support
  private var _shadowRoot: js.UndefOr[ShadowRoot] = js.undefined
  protected def useShadowRoot: Boolean = false // Override in subclasses to enable
  def shadowRoot: js.UndefOr[ShadowRoot] = _shadowRoot

  // Essential: Lifecycle method - Called when element is inserted into DOM
  @JSExport
  def connectedCallback(): Unit = {
    // Setup event listeners or other DOM-related initialization
    // Call render to update DOM (subclasses can override)
    render()
  }

  // Essential: Lifecycle method - Called when element is removed from DOM
  @JSExport
  def disconnectedCallback(): Unit = {
    // Cleanup (e.g., remove event listeners)
  }

  // Essential: Lifecycle method - Called when element is moved to a new document
  @JSExport
  def adoptedCallback(): Unit = {
    // Handle document adoption (rarely needed)
  }

  // Essential: Lifecycle method - Called when observed attributes change
  @JSExport
  def attributeChangedCallback(name: String, oldValue: js.Any, newValue: js.Any): Unit = {
    // Sync attributes to properties if defined in observedAttributes
    if (observedAttributes.contains(name)) {
      updatePropertyFromAttribute(name, newValue)
      requestUpdate()
    }
  }

  // Essential: Static getter for observed attributes
  @JSExport
  @JSExportTopLevel("observedAttributes")
  val observedAttributes: js.Array[String] = js.Array()

  // Desired: Initialize method for subclasses to override
  protected def initialize(): Unit = {
    // Subclasses can set initial state, properties, or DOM structure
  }

  // Desired: Simple attribute management
  protected def getStringAttribute(name: String): Option[String] = {
    val value = this.getAttribute(name)
    if (js.isUndefined(value) || value == null) None else Some(value.asInstanceOf[String])
  }

  protected def setStringAttribute(name: String, value: Option[String]): Unit = {
    value match {
      case Some(v) => this.setAttribute(name, v)
      case None => this.removeAttribute(name)
    }
  }

  protected def getBooleanAttribute(name: String): Boolean = {
    this.hasAttribute(name)
  }

  protected def setBooleanAttribute(name: String, value: Boolean): Unit = {
    if (value) this.setAttribute(name, "") else this.removeAttribute(name)
  }

  // Desired: Property reflection (sync attributes to JS properties)
  protected def updatePropertyFromAttribute(name: String, value: js.Any): Unit = {
    // Subclasses can override to map attributes to properties
    // Example: if (name == "my-prop") this.myProp = value.asInstanceOf[String]
  }

  // Desired: Update mechanism
  protected def requestUpdate(): Unit = {
    // Trigger render (subclasses implement actual DOM updates)
    render()
  }

  // Desired: Render hook for subclasses
  protected def render(): Unit = {
    // Subclasses override to update DOM (e.g., update shadowRoot content)
    // If using shadowRoot, append content to shadowRoot; otherwise, to this
    val target = shadowRoot.getOrElse(this)
    // Placeholder: Subclasses should define actual rendering logic
  }

  // Desired: Event dispatching utility
  protected def dispatchCustomEvent(eventName: String, detail: js.Any = js.undefined, bubbles: Boolean = true, cancelable: Boolean = true): Unit = {
    val init = CustomEventInit(
      detail = detail,
      bubbles = bubbles,
      cancelable = cancelable
    )
    val event = new CustomEvent(eventName, init)
    this.dispatchEvent(event)
  }
}

// Companion object for registration and static properties
object CustomHTMLElement {
  // Register the custom element
  def register(tagName: String): Unit = {
    org.scalajs.dom.window.customElements.define(tagName, js.constructorOf[CustomHTMLElement])
  }

  // Static observedAttributes (can be overridden in subclasses)
  def observedAttributes: js.Array[String] = js.Array()
}

// Helper for ShadowRoot initialization
@js.native
trait ShadowRootInit extends js.Object {
  var mode: String = js.native // "open" or "closed"
}
object ShadowRootInit {
  def apply(mode: String): ShadowRootInit = {
    js.Dynamic.literal(mode = mode).asInstanceOf[ShadowRootInit]
  }
}