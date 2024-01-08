plugins {
	id("com.android.application")
	id("com.google.dagger.hilt.android")
	kotlin("kapt") version "1.8.10"
	kotlin("plugin.serialization") version "1.8.10"
	kotlin("android")
}

android {
	namespace = "com.android.hospital"
	compileSdk = 34
	
	defaultConfig {
		applicationId = "com.android.hospital"
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
		sourceCompatibility = JavaVersion.VERSION_18
		targetCompatibility = JavaVersion.VERSION_18
	}
	kotlinOptions {
		jvmTarget = "18"
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
		}
	}
}

dependencies {
	
	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
	implementation("androidx.activity:activity-compose:1.8.2")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	/*Icons*/
	implementation("androidx.compose.material:material-icons-extended")
	/*Ktor Client*/
	val ktor_version: String by project
	implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
	implementation("io.ktor:ktor-client-core:$ktor_version")
	implementation("io.ktor:ktor-client-android:$ktor_version")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
	/*Hilt*/
	implementation("com.google.dagger:hilt-android:2.50")
	kapt("com.google.dagger:hilt-compiler:2.50")
	/*store*/
	implementation ("androidx.datastore:datastore-preferences:1.0.0")
	
	/*Navigation*/
	val nav_version: String by project
	implementation("androidx.navigation:navigation-compose:$nav_version")
	implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
}