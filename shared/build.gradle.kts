import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Ktor 공통 의존성
            implementation(libs.ktor.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.logging)
            // storage
            implementation(libs.settings)
            implementation(libs.settings.coroutines)
            // navigation
            implementation(libs.decompose)
            implementation(libs.essenty.lifecycle)
            // koin
            implementation(libs.koin.core)
            // log
            implementation(libs.napier)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }
        androidMain.dependencies {

            implementation(libs.ktor.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.compose)
            implementation(libs.decompose.compose)
//            // Kakao
//            implementation(libs.kakao.user)
//
//            // naver
//            implementation(libs.naver.login)
//            // google
//            implementation(libs.google.sigin)

            implementation(libs.decompose)
            implementation(libs.decompose.compose)
            implementation(libs.essenty.lifecycle)
        }
        iosMain.dependencies {
            // iOS용 Ktor 엔진
            implementation(libs.ktor.darwin)
        }
    }
}

android {
    namespace = "com.pawcare.client"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
        buildConfigField(
            "String",
            "NAVER_CLIENT_ID",
            "\"${project.findProperty("naver.client.id")}\""
        )
        buildConfigField(
            "String",
            "NAVER_CLIENT_SECRET",
            "\"${project.findProperty("naver.client.secret")}\""
        )
        buildConfigField(
            "String",
            "GOOGLE_CLIENT_ID",
            "\"${project.findProperty("google.client.id")}\""
        )
        buildConfigField(
            "String",
            "APP_NAME",
            "\"${project.findProperty("app.name")}\""
        )
        buildConfigField(
            "String",
            "KAKAO_APP_KEY",
            "\"${project.findProperty("kakao.app.key")}\""
        )
        buildConfigField(
            "String",
            "BASE_API_URL",
            "\"${project.findProperty("api.base.url")}\""
        )
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true // BuildConfig 기능 활성화
    }
}
