import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.compose") version "1.4.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "org.example"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://oss.sonatype.org/content/repositories/snapshots")

}

sqldelight {
    databases {
        create("ModuleDb") {
            packageName.set("com.example")
        }
    }
}



dependencies {
    implementation(compose.desktop.currentOs)

    implementation("io.github.jan-tennert.supabase:postgrest-kt:0.7.6")

    implementation("io.ktor:ktor-client-cio:2.3.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    val koinVersion = "3.5.3"

    implementation("io.insert-koin:koin-core:$koinVersion")


    val sqlDelightversion = "2.0.0"

    implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightversion")
    implementation("app.cash.sqldelight:runtime:$sqlDelightversion")
    implementation("app.cash.sqldelight:coroutines-extensions:$sqlDelightversion")
    implementation("app.cash.sqldelight:gradle-plugin:$sqlDelightversion")
    implementation(compose.materialIconsExtended)

    val richerTextEditorVer = "1.0.0-rc01"

//    implementation("com.mohamedrejeb.richeditor:richeditor-compose:$richerTextEditorVer")

//     val composeVersion = "1.5.0"
//     val composeCompilerVersion = "1.4.5"
//     val composeBoomVersion = "2023.03.00"
//    val navigationVersion = "2.5.3"
//
//    implementation("androidx.compose.compiler:compiler:$composeCompilerVersion")
//    implementation(platform("androidx.compose:compose-bom:$composeBoomVersion"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.compose.runtime:runtime:$composeVersion")
//    implementation("androidx.navigation:navigation-compose:$navigationVersion")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Modules"
            packageVersion = "1.0.0"
        }
    }
}

