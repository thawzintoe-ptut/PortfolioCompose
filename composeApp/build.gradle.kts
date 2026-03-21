import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

val flavor: String by project
val suffix = when (flavor) {
    "dev" -> ".dev"
    "staging" -> ".staging"
    else -> ""
}

val versionMajor: String by project
val versionMinor: String by project
val versionPatch: String by project
val appVersionCode = versionMajor.toInt() * 10000 + versionMinor.toInt() * 100 + versionPatch.toInt()
val appVersionName = "$versionMajor.$versionMinor.$versionPatch"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(project(":config:$flavor"))
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.coil.compose)
            implementation(compose.materialIconsExtended)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.ptut.portfolio"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ptut.portfolio"
        applicationIdSuffix = suffix
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = appVersionCode
        versionName = appVersionName

        when (flavor) {
            "dev" -> resValue("string", "app_name", "Portfolio Dev")
            "staging" -> resValue("string", "app_name", "Portfolio Staging")
            else -> resValue("string", "app_name", "Portfolio")
        }
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
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.ptut.portfolio.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.ptut.portfolio"
            packageVersion = appVersionName

            macOS {
                iconFile.set(project.file("src/jvmMain/resources/app-icon.icns"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/app-icon.png"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/app-icon.png"))
            }
        }
    }
}

tasks.register("copyFirebaseConfig") {
    val source = file("${rootProject.projectDir}/config/$flavor/google-services.json")
    val target = file("${projectDir}/google-services.json")
    doLast {
        if (source.exists()) {
            source.copyTo(target, overwrite = true)
        }
    }
}

tasks.named("preBuild") {
    dependsOn("copyFirebaseConfig")
}

tasks.register("runDebug", Exec::class) {
    dependsOn("clean", "uninstallDebug", "installDebug")
    val adb = android.adbExecutable.absolutePath
    commandLine(
        adb, "shell", "am", "start", "-n",
        "com.ptut.portfolio$suffix/com.ptut.portfolio.MainActivity"
    )
}
