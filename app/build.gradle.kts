plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.stc.muhammadsayed"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stc.muhammadsayed"
        minSdk = 21
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {

    //foundation
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //compose
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.09.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //room
    val room = "2.5.2"
    implementation("androidx.room:room-runtime:${room}")
    implementation("androidx.room:room-ktx:${room}")
    ksp("androidx.room:room-compiler:${room}")

    //retrofit
    val retrofit = "2.9.0"
    val okHttp = "4.11.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit}")
    implementation("com.squareup.okhttp3:okhttp:${okHttp}")
    implementation ("com.squareup.okhttp3:logging-interceptor:${okHttp}")

    //hilt
    val hilt = "2.48"
    implementation("com.google.dagger:hilt-android:${hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${hilt}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    //glide
    val glide = "4.16.0"
    implementation("com.github.bumptech.glide:glide:${glide}")
    ksp("com.github.bumptech.glide:compiler:${glide}")


}