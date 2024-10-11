pluginManagement {
    repositories {
        maven("https://kitpowered.nexus/")
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        maven("https://kitpowered.nexus/")
    }
}

rootProject.name = "kit"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":kit-project:kit-core")
include(":kit-project:kit-plugin")
include(":kit-project:kit-test")

includeBuild("build-logic")