apply(plugin = "maven-publish")

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("kit") {
            group = project.group
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])

            setupPom(pom)
        }
    }

    val publishingUsername: String? = System.getenv("NEXUS_USERNAME")
    val publishingPassword: String? = System.getenv("NEXUS_PASSWORD")

    if (publishingUsername != null && publishingPassword != null) {
        repositories {
            val name: String
            val url: String
            if (isSnapshotVersion(project.version.toString())) {
                name = "snapshot"
                url = "https://nexus.kitpowered.com/repository/maven-snapshots/"
            } else {
                name = "release"
                url = "https://nexus.kitpowered.com/repository/maven-releases/"
            }
            maven {
                this.name = name
                this.url = uri(url)
                credentials {
                    username = publishingUsername
                    password = publishingPassword
                }
            }
        }
    }
}

configure<JavaPluginExtension> {
    withSourcesJar()
    withJavadocJar()
}

tasks.withType(GenerateMavenPom::class.java) {
    doFirst {
        setupPom(pom)
    }
}

fun setupPom(pom: MavenPom) {
    with(pom) {
        name by "project.name"
        url by "project.url"
        description by "project.description"

        licenses {
            license {
                name by "project.license"
                url by "project.license.url"
            }
        }
        developers {
            developer {
                id by "project.developer.id"
                name by "project.developer.name"
                email by "project.developer.email"
            }
            scm {
                url by "project.url.scm"
            }
        }
    }
}

fun isSnapshotVersion(version: String): Boolean {
    return version.endsWith("-SNAPSHOT")
}

infix fun Property<String>.by(propertyName: String) {
    set(property(propertyName).toString())
}