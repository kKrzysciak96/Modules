import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.compose") version "1.4.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

group = "org.example"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)

    implementation("io.github.jan-tennert.supabase:postgrest-kt:0.7.6")

    implementation("io.ktor:ktor-client-cio:2.3.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    val koin_version = "3.5.3"

    implementation("io.insert-koin:koin-core:$koin_version")
//    implementation ("io.insert-koin:koin-androidx-compose:$koin_version")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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