plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.shopmobile"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.shopmobile"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val useMockServer = project.findProperty("useMockServer")?.toString() ?: "false"
        buildConfigField("boolean", "USE_MOCK_SERVER", useMockServer)

        val takeScreenshots = project.findProperty("takeScreenshots")?.toString() ?: "false"
        buildConfigField("boolean", "TAKE_SCREENSHOTS", takeScreenshots)
     }

    buildTypes {
        getByName("release") {
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

    val useOrchestrator = project.findProperty("useOrchestrator")?.toString() ?: "false"

    testOptions {
        animationsDisabled = false
        if(useOrchestrator == "true")
            execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt"
            )
        }
    }
}

configurations.all {
    exclude(group = "org.hamcrest", module = "hamcrest-library")
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    
    // Idling Resource (Implementation required as helper class is in main source)
    implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")

    // Mock Web Server
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    androidTestImplementation("com.squareup.okhttp3:okhttp-tls:4.12.0")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
}