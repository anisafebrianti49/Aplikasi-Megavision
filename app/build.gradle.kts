plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.aplikasimegavision"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aplikasimegavision"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Android Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    // Firebase Database
    implementation("com.google.firebase:firebase-database-ktx")

    // Firebase Auth
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase Storage (TAMBAHAN BARU UNTUK FOTO PROFIL)
    implementation("com.google.firebase:firebase-storage-ktx")

    // Glide (TAMBAHAN BARU UNTUK LOAD FOTO PROFIL DARI STORAGE)
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Navigation
    val navVersion = "2.7.7"

    implementation(
        "androidx.navigation:navigation-fragment-ktx:$navVersion"
    )

    implementation(
        "androidx.navigation:navigation-ui-ktx:$navVersion"
    )

    // Testing
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation(
        "androidx.test.ext:junit:1.1.5"
    )

    androidTestImplementation(
        "androidx.test.espresso:espresso-core:3.5.1"
    )
}