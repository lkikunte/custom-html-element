# CustomHTMLElement Documentation

`CustomHTMLElement` is a Scala.js base class for creating custom web components that extend the browser's `HTMLElement`. It implements the essential lifecycle methods required by the Web Components Custom Elements API and includes optional utilities for common tasks like shadow DOM management, attribute handling, property reflection, event dispatching, and manual DOM updates. Designed to be framework-agnostic, it serves as a lightweight foundation for building custom elements in Scala.js, suitable for use with libraries like Laminar or standalone.

## Table of Contents

- [Overview](#overview)
- [Setup](#setup)
- [Usage](#usage)
- [API Reference](#api-reference)
  - [Essential Lifecycle Methods](#essential-lifecycle-methods)
  - [Desired Utilities](#desired-utilities)
- [Examples](#examples)
  - [Basic Custom Element](#basic-custom-element)
  - [Custom Element with Shadow DOM](#custom-element-with-shadow-dom)
- [Extending with Laminar](#extending-with-laminar)
- [Notes](#notes)

## Overview

`CustomHTMLElement` extends `org.scalajs.dom.raw.HTMLElement` and provides:

- **Essential Features**: All standard Web Components lifecycle methods (`constructor`, `connectedCallback`, `disconnectedCallback`, `adoptedCallback`, `attributeChangedCallback`, `observedAttributes`) for browser integration.
- **Desired Features**:
  - Shadow DOM support for encapsulated styling and DOM.
  - Type-safe attribute getters/setters for strings and booleans.
  - Property reflection to sync attributes to JavaScript properties.
  - Custom event dispatching for communication.
  - A simple update mechanism (`requestUpdate`/`render`) for manual DOM updates.
- **Scala.js Interop**: Uses `@JSExportTopLevel` and `@JSExport` for seamless JavaScript compatibility, ensuring the class can be registered as a custom element.

This class is ideal for developers building reusable, standards-compliant web components in Scala.js without relying on frameworks like Lit or Laminar, though it can be extended for reactive use cases.

#### Setup

To use `CustomHTMLElement` in your Scala.js project, follow the instructions below for either **sbt** or **Mill** to configure your build and integrate the class, either as a local source or as an external dependency.

### sbt Setup

1. **Ensure ECMAScript 2015 Output**:
   
   - Custom elements require ES6 class syntax. Configure your `build.sbt` to use ECMAScript 2015:
     
     ```scala
     scalaJSLinkerConfig ~= { _.withOutputMode(scala.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv.OutputMode.ECMAScript2015) }
     ```

2. **Add Dependencies**:
   
   - Include `scalajs-dom` for DOM interop. Update `build.sbt`:
     
     ```scala
     libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0"
     ```
   
   - If using `CustomHTMLElement` as an external dependency (recommended for reusability), also add:
     
     ```scala
     libraryDependencies += "com.example" %%% "custom" % "0.1.0"
     ```

3. **Include `CustomHTMLElement`**:
   
   - **Option 1: Local Source**:
     - Save `CustomHTMLElement.scala` in your project, e.g., `src/main/scala/custom/CustomHTMLElement.scala`.
     - This is suitable for single-project use but not ideal for reuse across projects.
   - **Option 2: External Dependency (Recommended)**:
     - Set up `CustomHTMLElement` as a separate project (e.g., in `~/scala-js-libs/custom-html-element/`).
     - Publish it locally (see [Publishing CustomHTMLElement](https://grok.com/c/c2c18129-385f-4620-8468-ae613874a17c#publishing-customhtmlelement)).
     - Reference it in `build.sbt` as shown above.

4. **Register the Element**:
   
   - Use the companion object’s `register` method to define the custom element in the browser. In your main Scala.js code:
     
     ```scala
     import custom.CustomHTMLElement
     object Main {
      def main(args: Array[String]): Unit = {
        MyCustomElement.register() // Assuming MyCustomElement extends CustomHTMLElement
      }
     }
     ```

5. **Build and Run**:
   
   - Compile and link your project:
     
     ```bash
     sbt fastLinkJS
     ```
   
   - Serve the output (e.g., `target/scala-3.3.6/scalajs-bundler/main/main.js`) with a web server and include it in your HTML:
     
     ```html
     <script type="module" src="main.js"></script>
     ```

### Mill Setup

1. **Create or Use a Mill Project**:
   
   - Initialize a Mill project if you don’t have one:
     
     ```bash
     mkdir my-project
     cd my-project
     mill -i init com.lihaoyi/mill-scala-hello.g8
     ```
   
   - This creates a basic Mill project with a `build.sc` file.

2. **Configure ECMAScript 2015 Output**:
   
   - Custom elements require ES6 class syntax. In your `build.sc`, configure the `ScalaJSModule` to use `ModuleKind.ESModule`:
     
     ```scala
     import mill._
     import scalalib._
     import scalajslib._
     import scalajslib.api._
     
     object app extends ScalaJSModule {
      def scalaVersion = "3.3.6"
      def scalaJSVersion = "1.19.0"
      def moduleKind = ModuleKind.ESModule
     }
     ```

3. **Add Dependencies**:
   
   - Include `scalajs-dom` for DOM interop. Update `build.sc`:
     
     ```scala
     def mvnDeps = Seq(
      mvn"org.scala-js::scalajs-dom::2.8.1"
     )
     ```
   
   - If using `CustomHTMLElement` as an external dependency (recommended), also add:
     
     ```scala
     def mvnDeps = Seq(
      mvn"org.scala-js::scalajs-dom::2.8.1",
      mvn"com.example::custom::0.1.0" // CustomHTMLElement library
     )
     ```

4. **Include `CustomHTMLElement`**:
   
   - **Option 1: Local Source**:
     - Save `CustomHTMLElement.scala` in your project’s source directory, e.g., `src/main/scala/custom/CustomHTMLElement.scala`.
     - This is suitable for single-project use but not recommended for reuse across projects.
   - **Option 2: External Dependency (Recommended)**:
     - Set up `CustomHTMLElement` as a separate Mill project (e.g., in `~/scala-js-libs/custom-html-element/`).
     - Publish it locally (see [Publishing CustomHTMLElement](https://grok.com/c/c2c18129-385f-4620-8468-ae613874a17c#publishing-customhtmlelement)).
     - Reference it in `build.sc` as shown above.

5. **Register the Element**:
   
   - Use the companion object’s `register` method to define the custom element in the browser. In your main Scala.js code:
     
     ```scala
     import custom.CustomHTMLElement
     object Main {
      def main(args: Array[String]): Unit = {
        MyCustomElement.register() // Assuming MyCustomElement extends CustomHTMLElement
      }
     }
     ```

6. **Build and Run**:
   
   - Compile and link your project:
     
     ```bash
     mill app.fastLinkJS
     ```
   
   - Serve the output (e.g., `out/app/fastLinkJS.dest/main.js`) with a web server and include it in your HTML:
     
     ```html
     <script type="module" src="main.js"></script>
     ```

### Publishing CustomHTMLElement

To use `CustomHTMLElement` as an external dependency:

1. **Create a Separate Project**:
   
   - Set up a new Mill project for `CustomHTMLElement` in a directory like `~/scala-js-libs/custom-html-element/`.
   
   - Directory structure:
     
     ```
     custom-html-element/
     ├── build.sc
     ├── src/main/scala/custom/CustomHTMLElement.scala
     ├── docs/CustomHTMLElement.md
     ```

2. **Configure the Build**:
   
   - Use the following `build.sc` for the `custom-html-element` project:
     
     ```scala
     import mill._
     import scalalib._
     import scalajslib._
     import scalajslib.api._
     import mill.scalalib.publish._
     
     object custom extends ScalaJSModule with PublishModule {
      def scalaVersion = "3.3.6"
      def scalaJSVersion = "1.19.0"
      def moduleKind = ModuleKind.ESModule
      def scalaJSUseMainModuleInitializer = false
      def mvnDeps = Seq(mvn"org.scala-js::scalajs-dom::2.8.1")
      def publishVersion = "0.1.0"
      def pomSettings = PomSettings(
        description = "A Scala.js base class for custom web components",
        organization = "com.example",
        url = "https://github.com/yourusername/custom-html-element",
        licenses = Seq(License.MIT),
        versionControl = VersionControl.github("yourusername", "custom-html-element"),
        developers = Seq(Developer("yourusername", "Your Name", "https://github.com/yourusername"))
      )
      def docSources = T.sources { millSourcePath / "docs" }
     }
     ```

3. **Save Source and Documentation**:
   
   - Save `CustomHTMLElement.scala` in `custom-html-element/src/main/scala/custom/`.
   - Save `CustomHTMLElement.md` in `custom-html-element/docs/`.

4. **Publish Locally**:
   
   - Run:
     
     ```bash
     cd path/to/custom-html-element
     mill custom.publishLocal
     ```
   
   - This publishes `com.example::custom:0.1.0` to `~/.m2/repository`, which your main project can reference via `mvnDeps` (Mill) or `libraryDependencies` (sbt).

5. **Use in Your Project**:
   
   - Reference the dependency in your main project’s `build.sc` or `build.sbt` as shown above.
   - Import and use `CustomHTMLElement` in your Scala.js code.

## Usage

1. **Create a Subclass**: Extend `CustomHTMLElement` and override methods like `observedAttributes`, `initialize`, `render`, or lifecycle methods as needed.
2. **Register the Element**: Call `CustomHTMLElement.register(tagName)` to register your custom element with the browser.
3. **Use in HTML**: Use the custom element tag in your HTML, setting attributes or listening for events as needed.

Example:

```scala
@JSExportTopLevel("MyCustomElement")
class MyCustomElement extends CustomHTMLElement {
  override val observedAttributes: js.Array[String] = js.Array("value")
  override protected def render(): Unit = {
    val target = shadowRoot.getOrElse(this)
    target.innerHTML = s"<div>Value: ${getStringAttribute("value").getOrElse("No value")}</div>"
  }
}

MyCustomElement.register("my-custom-element")
```

HTML:

```html
<my-custom-element value="Hello"></my-custom-element>
```

## API Reference

### Essential Lifecycle Methods

These methods are required for browser integration and align with the Web Components Custom Elements API.

| Method/Property                                                                        | Description                                                                                                                                                                      |
| -------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `def this()`                                                                           | Constructor. Initializes the element, optionally attaching a shadow root if `useShadowRoot` is `true`. Calls `initialize()` for subclass setup. Avoid DOM mutations here.        |
| `def connectedCallback(): Unit`                                                        | Called when the element is inserted into the DOM. Calls `render()` by default. Override to add event listeners or setup logic.                                                   |
| `def disconnectedCallback(): Unit`                                                     | Called when the element is removed from the DOM. Override for cleanup (e.g., removing listeners).                                                                                |
| `def adoptedCallback(): Unit`                                                          | Called when the element is moved to a new document (e.g., via `adoptNode`). Rarely used; override if needed.                                                                     |
| `def attributeChangedCallback(name: String, oldValue: js.Any, newValue: js.Any): Unit` | Called when an observed attribute changes. Syncs attributes to properties via `updatePropertyFromAttribute` and calls `requestUpdate`. Override to customize attribute handling. |
| `val observedAttributes: js.Array[String]`                                             | Static getter specifying attributes to observe. Default: empty array. Override in subclasses (e.g., `js.Array("value", "disabled")`).                                            |

### Desired Utilities

These optional features simplify common custom element tasks, inspired by patterns in libraries like Lit but kept framework-agnostic.

| Method/Property                                                                                                                                  | Description                                                                                                                 |
| ------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `protected def useShadowRoot: Boolean`                                                                                                           | Flag to enable shadow DOM (default: `false`). Override to `true` for encapsulated DOM/styling.                              |
| `def shadowRoot: js.UndefOr[ShadowRoot]`                                                                                                         | Getter for the attached shadow root, if any. Returns `js.undefined` if `useShadowRoot` is `false`.                          |
| `protected def initialize(): Unit`                                                                                                               | Hook for subclass initialization (e.g., setting state or DOM). Called in constructor. Override as needed.                   |
| `protected def getStringAttribute(name: String): Option[String]`                                                                                 | Gets a string attribute, returning `Option[String]` (`None` if unset). Type-safe for string attributes.                     |
| `protected def setStringAttribute(name: String, value: Option[String]): Unit`                                                                    | Sets or removes a string attribute based on `Option[String]`. Removes if `None`.                                            |
| `protected def getBooleanAttribute(name: String): Boolean`                                                                                       | Checks if a boolean attribute exists (e.g., `disabled`). Returns `true` if present.                                         |
| `protected def setBooleanAttribute(name: String, value: Boolean): Unit`                                                                          | Sets or removes a boolean attribute (sets empty string for `true`, removes for `false`).                                    |
| `protected def updatePropertyFromAttribute(name: String, value: js.Any): Unit`                                                                   | Hook to sync attributes to JavaScript properties (e.g., `this.value = value`). Override in subclasses for reflection.       |
| `protected def requestUpdate(): Unit`                                                                                                            | Triggers a DOM update by calling `render`. Override or use for manual updates.                                              |
| `protected def render(): Unit`                                                                                                                   | Hook for DOM updates. Targets `shadowRoot` or `this`. Override to define rendering logic (e.g., set `innerHTML`).           |
| `protected def dispatchCustomEvent(eventName: String, detail: js.Any = js.undefined, bubbles: Boolean = true, cancelable: Boolean = true): Unit` | Dispatches a custom event with optional `detail`, `bubbles`, and `cancelable` settings. Useful for signaling state changes. |

### Companion Object

| Method                                | Description                                                                                                                          |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| `def register(tagName: String): Unit` | Registers the custom element with the browser using `customElements.define`. Call with a hyphenated tag name (e.g., `"my-element"`). |

## Examples

### Basic Custom Element

A simple custom element that displays an attribute value.

```scala
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import custom.CustomHTMLElement

@JSExportTopLevel("SimpleElement")
class SimpleElement extends CustomHTMLElement {
  override val observedAttributes: js.Array[String] = js.Array("text")

  override protected def render(): Unit = {
    this.innerHTML = s"<span>${getStringAttribute("text").getOrElse("No text")}</span>"
  }

  override protected def updatePropertyFromAttribute(name: String, value: js.Any): Unit = {
    if (name == "text") {
      this.asInstanceOf[js.Dynamic].text = value
      dispatchCustomEvent("text-changed", js.Dynamic.literal(text = value))
    }
  }
}

object SimpleElement {
  def register(): Unit = CustomHTMLElement.register("simple-element")
}
```

HTML:

```html
<simple-element text="Hello, World!"></simple-element>
<script>
  document.querySelector("simple-element").addEventListener("text-changed", (e) => {
    console.log("Text changed:", e.detail.text);
  });
</script>
```

Main Scala.js:

```scala
SimpleElement.register()
```

### Custom Element with Shadow DOM

A custom element using shadow DOM for encapsulated styling.

```scala
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import custom.CustomHTMLElement

@JSExportTopLevel("ShadowElement")
class ShadowElement extends CustomHTMLElement {
  override protected def useShadowRoot: Boolean = true
  override val observedAttributes: js.Array[String] = js.Array("color")

  override protected def initialize(): Unit = {
    // Add styles to shadow DOM
    shadowRoot.foreach { root =>
      root.innerHTML = """
        <style>
          .container { font-family: Arial; padding: 10px; }
        </style>
      """
    }
  }

  override protected def render(): Unit = {
    shadowRoot.foreach { root =>
      val color = getStringAttribute("color").getOrElse("black")
      root.innerHTML += s"""<div class="container" style="color: $color;">Shadow Content</div>"""
    }
  }
}

object ShadowElement {
  def register(): Unit = CustomHTMLElement.register("shadow-element")
}
```

HTML:

```html
<shadow-element color="blue"></shadow-element>
```

Main Scala.js:

```scala
ShadowElement.register()
```

## Extending with Laminar

To use `CustomHTMLElement` with Laminar for reactive programming:

1. Extend `ReactiveHtmlElement[CustomHTMLElement]` and mix in your custom element logic.
2. Use Laminar’s reactive bindings (e.g., `attr`, `prop`, `onEvent`) to manage attributes and events reactively.

Example:

```scala
import com.raquo.laminar.api.L.*
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel}
import custom.CustomHTMLElement

@JSExportTopLevel("ReactiveElement")
class ReactiveElement extends CustomHTMLElement {
  override val observedAttributes: js.Array[String] = js.Array("value")
}

object ReactiveElement {
  def apply(): HtmlElement = {
    val element = new ReactiveElement()
    htmlTag[ReactiveElement]("reactive-element").apply(
      attr("value") := "Initial",
      onClick --> (_ => element.setStringAttribute("value", Some("Clicked")))
    )
  }

  def register(): Unit = CustomHTMLElement.register("reactive-element")
}
```

Usage:

```scala
ReactiveElement.register()
div(
  ReactiveElement()
)
```

## Notes

- **Type Safety**: Attribute methods use `Option[String]` and `Boolean` for safer handling, avoiding JavaScript’s `null`/`undefined` issues.
- **Shadow DOM**: Enable only when encapsulation is needed, as it adds complexity. Use `mode = "closed"` in `ShadowRootInit` for stricter encapsulation if required.
- **Performance**: Keep `render` lightweight to avoid performance issues, especially with frequent updates.
- **Laminar Integration**: If using Laminar, consider wrapping `CustomHTMLElement` in `ReactiveHtmlElement` and using reactive signals for attributes/events instead of manual DOM updates.
- **Custom Events**: Use `dispatchCustomEvent` to communicate with parent components or JavaScript code, ensuring `bubbles` is `true` for event propagation.
- **Scala.js Interop**: Ensure `@JSExportTopLevel` matches the class name, and use hyphenated tag names (e.g., `my-element`) per the Custom Elements spec.

For further customization or integration (e.g., with ARIA mixins or Laminar), refer to the Scala.js DOM documentation or Laminar’s API.