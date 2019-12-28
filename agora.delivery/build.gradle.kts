plugins {
    application
    kotlin("jvm")
}

group = "org.agorahq.agora"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
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