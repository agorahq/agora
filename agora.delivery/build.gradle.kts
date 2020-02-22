plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
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

        implementation(ktorServerNetty)
        implementation(ktorServerCore)
        implementation(ktorHtmlBuilder)

        implementation(logbackClassic)
        implementation(kotlinCssJvm)

        testImplementation(ktorServerTests)
    }
}