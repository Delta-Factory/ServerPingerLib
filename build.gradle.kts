plugins {
	id("java")
	kotlin("jvm") version "2.1.10"
	id("com.gradleup.shadow") version("8.3.0")
}

group = "delta.cion"
version = "0.0.0-DEV"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
    mavenCentral()
}

dependencies {
	implementation("com.google.code.gson:gson:2.8.9")
	implementation("ch.qos.logback:logback-classic:1.5.18")
}

tasks {
	build {
		dependsOn(shadowJar)
	}

	shadowJar {
		mergeServiceFiles()
		archiveClassifier.set("")
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
}
