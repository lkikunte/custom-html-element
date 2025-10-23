## CustomHTMLElement

A lightweight Scala.js base class for building custom web components, extending `HTMLElement` with essential lifecycle methods and utilities for shadow DOM, attributes, and events.

## Quick Start

Add the following dependency to your `build.sc` (Mill) or `build.sbt` (sbt):

**Mill**:

```scala
def mvnDeps = Seq(
  mvn"io.github.lkikunte::custom::0.1.0"
)
```

**sbt**:

```scala
libraryDependencies += "io.github.lkikunte" %%% "custom" % "0.1.0"
```

Example usage:

```scala
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import custom.CustomHTMLElement

@JSExportTopLevel("MyElement")
class MyElement extends CustomHTMLElement {
  override val observedAttributes: js.Array[String] = js.Array("value")
  override protected def render(): Unit = {
    this.innerHTML = s"<div>Value: ${getStringAttribute("value").getOrElse("No value")}</div>"
  }
}

object MyElement {
  def register(): Unit = CustomHTMLElement.register("my-element")
}
```

In HTML:

```html
<my-element value="Hello"></my-element>
<script type="module" src="path/to/your/main.js"></script>
```

## Features

- Essential lifecycle hooks (`connectedCallback`, `attributeChangedCallback`, etc.).
- Optional shadow DOM support for encapsulated styling and DOM.
- Type-safe attribute handling and property reflection.
- Custom event dispatching for component communication.

See [full documentation](./docs/CustomHTMLElement.md) for detailed API and usage instructions.

## Building & Contributing

- **Build**: `mill custom.compile`
- **Test**: `mill custom.test`
- **Publish Locally**: `mill custom.publishLocal`
- **Contribute**: Open issues or PRs on [GitHub](https://github.com/lkikunte/custom-html-element).

## License

MIT