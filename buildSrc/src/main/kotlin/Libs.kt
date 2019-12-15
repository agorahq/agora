import Versions.cobaltVersion
import Versions.kotlinVersion
import Versions.kotlinxCollectionsImmutableVersion
import Versions.kotlinxCoroutinesVersion
import Versions.kotlinxHtmlVersion
import Versions.logbackVersion
import Versions.slf4jVersion

object Libs {

    const val kotlinStdLibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val kotlinStdLibJs = "org.jetbrains.kotlin:kotlin-stdlib-js"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"

    const val kotlinxHtmlCommon = "org.jetbrains.kotlinx:kotlinx-html-common:$kotlinxHtmlVersion"
    const val kotlinxHtmlJvm = "org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion"
    const val kotlinxHtmlJs = "org.jetbrains.kotlinx:kotlinx-html-js:$kotlinxHtmlVersion"

    const val kotlinxCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion"
    const val kotlinxCollectionsImmutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:$kotlinxCollectionsImmutableVersion"

    const val cobaltDatatypes = "org.hexworks.cobalt:cobalt.datatypes:$cobaltVersion"

    const val slf4jApi = "org.slf4j:slf4j-api:$slf4jVersion"
    const val logbackClassic = "ch.qos.logback:logback-classic:$logbackVersion"
}
