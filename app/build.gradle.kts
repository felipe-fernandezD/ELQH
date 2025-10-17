plugins {
    // This is the correct way using your version catalog
    alias(libs.plugins.android.application)

    // This is also correct for the Kotlin plugin
    alias(libs.plugins.kotlin.android)

    // Apply the Google Services plugin using its alias
    alias(libs.plugins.google.gms.google.services)

    // You can keep this for Kotlin Parcelize if you need it
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.elqh"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.elqh"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Importa el Firebase BOM (Bill of Materials)
    // Esto gestiona las versiones de todas las librerías de Firebase
    implementation(platform(libs.firebase.bom))

    // Añade las dependencias de Firebase que necesitas (sin especificar versión)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // El resto de tus dependencias se mantienen igual
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Para la imagen circular
    implementation("de.hdodenhof:circleimageview:3.1.0")


}