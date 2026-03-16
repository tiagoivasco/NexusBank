plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nexus.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nexus.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ─── White Label Flavor Dimensions ────────────────────────────────────────
    flavorDimensions += "product"

    productFlavors {
        create("bankCorp") {
            dimension = "product"
            applicationId = "com.nexus.bank"
            buildConfigField("String", "BASE_URL", "\"https://api.nexusbank.com.br/v1/\"")
            buildConfigField("String", "FLAVOR_NAME", "\"bankCorp\"")
        }
        create("bankDigital") {
            dimension = "product"
            applicationId = "com.nexus.pay"
            buildConfigField("String", "BASE_URL", "\"https://api.nexuspay.com.br/v1/\"")
            buildConfigField("String", "FLAVOR_NAME", "\"bankDigital\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(project(":feature:home"))
    api(libs.retrofit.kotlinx.serialization)
    api(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.navigation.compose)
}
