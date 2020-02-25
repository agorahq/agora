import Versions.cobaltVersion
import Versions.flexmarkVersion
import Versions.kotlinVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.kotlinxHtmlVersion
import Versions.ktorVersion
import Versions.logbackVersion
import Versions.slf4jVersion

object Libs {

    const val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinStdLibJs = "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlinVersion"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    const val kotlinCssJvm = "org.jetbrains:kotlin-css-jvm:1.0.0-pre.89-kotlin-1.3.60"

    const val kotlinxHtmlCommon = "org.jetbrains.kotlinx:kotlinx-html-common:$kotlinxHtmlVersion"
    const val kotlinxHtmlJvm = "org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion"
    const val kotlinxHtmlJs = "org.jetbrains.kotlinx:kotlinx-html-js:$kotlinxHtmlVersion"

    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltCore = "org.hexworks.cobalt:cobalt.core:$cobaltVersion"

    const val ktorServerNetty = "io.ktor:ktor-server-netty:$ktorVersion"
    const val ktorAuth = "io.ktor:ktor-auth:$ktorVersion"
    const val ktorClient = "io.ktor:ktor-client-apache:$ktorVersion"
    const val ktorServerCore = "io.ktor:ktor-server-core:$ktorVersion"
    const val ktorServerSessions = "io.ktor:ktor-server-sessions:$ktorVersion"
    const val ktorJackson = "io.ktor:ktor-jackson:$ktorVersion"
    const val ktorHtmlBuilder = "io.ktor:ktor-html-builder:$ktorVersion"

    const val ktorLocations = "io.ktor:ktor-locations:$ktorVersion"

    const val ktorServerTests = "io.ktor:ktor-server-tests:$ktorVersion"

    const val flexmark = "com.vladsch.flexmark:flexmark-all:$flexmarkVersion"
    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"
    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"

}
