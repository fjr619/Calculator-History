import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotin.plugin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.room)
}

android {
    namespace = "com.fjr619.calculatorhistory"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fjr619.calculatorhistory"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        // to use KSP generated Code
        // KSP - To use generated sources

//        sourceSets {
//            getByName("main").kotlin.srcDirs("src/main/kotlin")
//            getByName("test").kotlin.srcDirs("src/test/kotlin")
//        }

        applicationVariants.configureEach {
            kotlin.sourceSets {
                getByName(name) {
                    kotlin.srcDir("build/generated/ksp/${name}/kotlin")
                }
            }
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
    arg("KOIN_DEFAULT_MODULE","true")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.bundles.koin)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.runtime.compose)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.splashscreen)
    implementation(libs.google.fonts)

    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    implementation ("org.mariuszgromada.math:MathParser.org-mXparser:6.0.0")


}