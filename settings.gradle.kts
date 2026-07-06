pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		mavenCentral()
		gradlePluginPortal()
	}

	// TODO: Find a way to move this to gradle.properties/build.gradle.kts without errors
	plugins {
		id("net.fabricmc.fabric-loom-remap") version providers.gradleProperty("loom_version")
		id("org.jetbrains.kotlin.jvm") version providers.gradleProperty("kotlin_version")
	}
}

// TODO: Find a way to move this to gradle.properties/build.gradle.kts without errors
// Should match MOD_ID in HardenedGlass.kt
rootProject.name = "hardenedglass"
