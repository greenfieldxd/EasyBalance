import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.pluginSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            //Koin
            implementation(libs.koin.android)

            //SqlDelight
            implementation(libs.sqldelight.android.driver)

            //Charts
            implementation(libs.charts.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //Data
            implementation(libs.kotlinx.datetime)

            //Serialization
            implementation(libs.kotlinx.serialization.json)

            //Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)

            //SqlDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            //Voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.koin)

            //Charts
            implementation(libs.charts)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            //Coroutines
            implementation(libs.kotlinx.coroutines.swing)

            //SqlDelight
            implementation(libs.sqldelight.sqlite.driver)

            //Charts
            implementation(libs.charts.jvm)

            //Logger
            implementation(libs.logback.classic)
        }
        iosMain.dependencies {
            //Charts
            implementation(libs.charts.iosx64)
            implementation(libs.charts.iosarm64)
        }
        nativeMain.dependencies {
            //SqlDelight
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "com.greenfieldxd.easybalance"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.greenfieldxd.easybalance"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.greenfieldxd.easybalance.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.greenfieldxd.easybalance"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("databases")
        }
    }
}