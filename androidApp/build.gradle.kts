import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.google.protobuf)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}
dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)

    // Notificaciones push (FirebaseMessagingService vive en androidApp)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.work.runtime.ktx)
    // gRPC y Protobuf
    implementation(libs.grpc.protobuf.lite)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.grpc.kotlin.stub)
    runtimeOnly(libs.grpc.okhttp)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "edu.ucb.pablostify"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "edu.ucb.pablostify"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }
    plugins {
        id("java") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.get()}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${libs.versions.grpcKotlin.get()}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("java") { option("lite") }
                create("grpc") { option("lite") }
                create("grpckt") { option("lite") }
            }
            it.builtins {
                create("kotlin") { option("lite") }
            }
        }
    }
}

// Firebase: el plugin lee androidApp/google-services.json (ahi vive la API key del proyecto).
// Solo se aplica si el archivo existe, asi el proyecto compila y abre incluso sin Firebase configurado.
if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
}
