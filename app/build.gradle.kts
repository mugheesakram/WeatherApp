import java.util.*
plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}
android {
    compileSdk = 32
    namespace = "com.test.weatherapp"
    defaultConfig {
        applicationId = "com.test.weatherapp"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
                isDebuggable = true
            }
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    flavorDimensions.add("default")
    productFlavors {
        val localProperties = File(project.rootDir, "variants.properties")
        val properties = Properties()
        if (localProperties.exists()) {
            localProperties.inputStream().use { properties.load(it) }

            create("dev") {
                dimension = "default"
                applicationId = "com.test.weatherapp"
                manifestPlaceholders["APPLICATION_ID"] = "com.test.weatherapp"
//                versionNameSuffix = "-${ProductFlavors.STG} -${AppConfig.versionName}"
                buildConfigField("String", "BASE_URL", properties["BASE_URL_DEV"].toString())
            }
            create("prod") {
                dimension = "default"
                applicationId = "com.test.weatherapp"
                manifestPlaceholders["APPLICATION_ID"] = "com.test.weatherapp"
                buildConfigField("String", "BASE_URL", properties["BASE_URL_PROD"].toString())
            }
        } else {
            System.err.println("Missing variants.properties file.")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Kotlin
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("androidx.test:core-ktx:1.4.0")
    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    debugImplementation("androidx.compose.ui:ui-tooling:1.0.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.0.5")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.5")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    // Room DB
    implementation("androidx.room:room-runtime:2.4.0-alpha03")
    implementation("androidx.room:room-ktx:2.4.0-alpha03")
    kapt("androidx.room:room-compiler:2.4.0-alpha03")
    //Location
    implementation("com.google.android.gms:play-services-location:21.0.0")
    // Navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.4.2")
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("androidx.test:rules:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("junit:junit:4.12")
    implementation("androidx.test.ext:junit-ktx:1.1.3")
}