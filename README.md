# swagger-asciidoc-primer

This project describes some approaches to generating API reference documentation from Java or Groovy code using the Swagger (OpenAPI) 
specification and AsciiDoc.

To view the generated documentation:

1. Clone this repository.
1. Ensure you have an Oracle Java or Open Java JDK installed on your machine.
1. Run the following from the root of the project directory:

    <details>
      <summary>Linux or Mac</summary>

      ```shell
      ./gradlew asciidoctor
      ```

      </summary>
    </details>

    <details>
      <summary>Windows</summary>

      ```shell
      gradlew.bat asciidoctor
      ```

      </summary>
    </details>

The `gradlew` wrapper script automatically downloads and installs the Gradle build tool and executes the commands in the `build.gradle` file.
Generated HTML and PDF is written to the project `build` directory.

The document is published to GitHub Pages when you push to the 'master' branch using GitHub Actions.
