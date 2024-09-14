plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

subprojects {
    group = property("project.group").toString()
    version = property("project.version").toString()

    with(pluginManager) {
        apply(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
}