plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.hostello"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.hostello"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.9.0")
    // AndroidX Core
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
// Material Design
    implementation("com.google.android.material:material:1.11.0")
// RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// CardView
    implementation("androidx.cardview:cardview:1.0.0")
    // Google Play Services for SMS Retriever
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.2")
}