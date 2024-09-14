plugins {
    `java-library`
    alias(libs.plugins.kotlin.plugin.spring)
}

dependencies {
    compileOnlyApi(libs.paper.api)

    api(libs.spring.boot.starter)
    api(libs.kotlinx.coroutines.core)

    testImplementation(libs.paper.api)
    testImplementation(libs.spring.boot.starter.test)
}