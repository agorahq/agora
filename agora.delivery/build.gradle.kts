@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

val mainClassName = "io.ktor.server.netty.EngineMain"


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

        implementation(ktorServerCore)
        implementation(ktorServerNetty)
        implementation(ktorHtmlBuilder)
        implementation(ktorAuth)
        implementation(ktorClient)
        implementation(ktorServerSessions)
        implementation(ktorJackson)

        implementation(logbackClassic)
        implementation(kotlinCssJvm)

        testImplementation(ktorServerTests)
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("agora.delivery.jar")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to mainClassName))
        }
    }
    build {
        dependsOn(shadowJar)
    }
}