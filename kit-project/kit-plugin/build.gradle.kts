import java.time.LocalDateTime

plugins {
    alias(libs.plugins.pluginjar)
    alias(libs.plugins.kotlin.plugin.spring)
}

dependencies {
    compileOnly(libs.paper.api)

    implementation(projects.kitProject.kitCore)
}

tasks.pluginJar {
    manifest {
        attributes(
            "Implementation-Title" to rootProject.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to rootProject.extra["project.vendor"].toString(),
            "Implementation-Timestamp" to LocalDateTime.now()
        )
    }
}