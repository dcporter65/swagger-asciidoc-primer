# This is the Kotlin DSL equivalent of the Gradle build script used at the project root

import io.github.swagger2markup.tasks.Swagger2MarkupTask
import org.asciidoctor.gradle.jvm.AsciidoctorTask

buildscript {
    dependencies {
        classpath("io.github.swagger2markup:swagger2markup-gradle-plugin:1.3.3")
        classpath("io.github.swagger2markup:swagger2markup-import-files-ext:1.3.3")
    }
}

plugins {
    id("org.asciidoctor.jvm.convert") version "3.2.0"
    id("org.asciidoctor.jvm.pdf")  version "3.2.0"
}

repositories {
    maven {
        url = uri("https://repo.spring.io/release")
    }
    jcenter()
}

apply( plugin = "io.github.swagger2markup")

val asciidoctorExt by configurations.creating

dependencies {
    asciidoctorExt("io.spring.asciidoctor:spring-asciidoctor-extensions-block-switch:0.5.0")
}

tasks.withType<Swagger2MarkupTask> {
        swaggerInputFile = file("${buildDir}/swagger/swagger.yml")
        outputDir = file("${buildDir}/asciidoc/generated")
        config = mapOf(
                "swagger2markup.pathsGroupedBy" to "TAGS",
                "swagger2markup.extensions.dynamicPaths.contentPath" to file("asciidoc/extensions/paths").absolutePath
        )
}

tasks.withType<AsciidoctorTask> {
    dependsOn(tasks.getByName("clean").name, tasks.getByName("convertSwagger2markup").name)
    setSourceDir(file("${project.rootDir}/docs/asciidoc"))
    outputs.upToDateWhen { false }
    configurations("asciidoctorExt")
    outputOptions {
        backends("html5", "pdf")
        separateOutputDirs = false
    }
    sources(delegateClosureOf<PatternSet> {
        include("index.adoc")
    })

    baseDirFollowsSourceDir()
    this.attributes(
            mapOf(
                    "docinfo"            to "shared",
                    "doctype"            to "book",
                    "generated"          to file("${buildDir}/asciidoc/generated"),
                    "revnumber"          to "",
                    "sectanchors"        to "true",
                    "source-highlighter" to "prettify",
                    "toc"                to "left",
                    "toclevels"          to "3")
    )
}

