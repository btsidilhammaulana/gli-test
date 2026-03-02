import java.util.Properties

include(":custom")


pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
  }
}
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://www.jitpack.io")
  }
}

val props = Properties().apply {
  file("$rootDir/config/app.properties").inputStream().use {
    load(it)
  }
}

rootProject.name = props["PROJECT_NAME"].toString()
include(":app")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:local")
include(":core:network")
