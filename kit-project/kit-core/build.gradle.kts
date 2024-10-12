plugins {
    `java-library`
    alias(libs.plugins.kotlin.plugin.spring)
    id("kit-publishing")
}

dependencies {
    compileOnlyApi(libs.paper.api)

    api(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.aop)
    api(libs.kotlin.reflect)
    api(libs.kotlinx.coroutines.core)

    testImplementation(libs.paper.api)
    testImplementation(libs.spring.boot.starter.test)
}