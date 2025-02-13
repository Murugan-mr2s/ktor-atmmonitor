plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ktor) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
}

val projectversion by extra("0.0.1")

/*
val commonDependencies = listOf(
    "implementation" to libs.ktor.server.core,
    "implementation"  to libs.ktor.server.netty,
    "implementation" to libs.logback.classic,
    "implementation" to libs.ktor.server.config.yaml,
    "testImplementation"  to libs.ktor.server.test.host,
    "testImplementation" to libs.kotlin.test.junit)
*/

allprojects {

    /*
    group = "com.example"
    version = projectversion*/

    repositories {
        mavenCentral()
    }
}

/*
subprojects {

    apply(plugin = "java")

    dependencies {
        commonDependencies.forEach { (config, dep) ->
            add(config,dep)
        }
    }
}
*/