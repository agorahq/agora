@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    kotlin("plugin.serialization") version "1.3.71"
}

application {
    mainClassName = "org.agorahq.agora.delivery.ApplicationKt"
}

tasks.withType<Jar> {
    manifest {
        attributes(mapOf("Main-Class" to application.mainClassName))
    }
}

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }
}

dependencies {

    with(Projects) {
        implementation(agoraPost)
    }

    with(Libs) {
        implementation(kotlinStdLibJdk8)
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

        implementation(ktorServerCore)
        implementation(ktorServerNetty)
        implementation(ktorHtmlBuilder)
        implementation(ktorAuth)
        implementation(ktorClient)
        implementation(ktorNetworkTls)
        implementation(ktorNetworkTlsCertificates)
        implementation(ktorServerSessions)
        implementation(ktorSerialization)

        implementation("io.jsonwebtoken:jjwt-api:0.11.1")
        implementation("io.jsonwebtoken:jjwt-impl:0.11.1")
        implementation("io.jsonwebtoken:jjwt-jackson:0.11.1")

        runtime("io.jsonwebtoken:jjwt-impl:0.11.1")
        runtime("io.jsonwebtoken:jjwt-jackson:0.11.1")

        implementation(logbackClassic)
        implementation(kotlinCssJvm)

        testImplementation(ktorServerTests)
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("agora.delivery.jar")
        mergeServiceFiles()
    }
    build {
        dependsOn(shadowJar)
    }
}
