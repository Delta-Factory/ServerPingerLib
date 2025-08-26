plugins {
	id("java")
	id("java-library")
	id("maven-publish")
	kotlin("jvm") version("2.1.10")
	id("com.gradleup.shadow") version("8.3.0")
}

group = "delta.cion"
version = "0.0.0-DEV"

val m = """
	======================================
	Author: @nionim (DeltaCion)
	For: Project~Violette
	Support: https://discord.gg/MEBkvJbe4P
	======================================
	String to Import API:
	%s:%s:%s
	======================================
	"""

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
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}

	build {
		dependsOn(shadowJar)
		dependsOn("publishToMavenLocal")
	}

	shadowJar {
		mergeServiceFiles()
		archiveClassifier.set("")
	}

	register<Jar>("javadocJar") {
		dependsOn(javadoc)
		archiveClassifier.set("javadoc")
		from(javadoc.get().destinationDir)
	}

	register<Jar>("sourcesJar") {
		archiveClassifier.set("sources")
		from(sourceSets["main"].allSource)
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			groupId = group.toString()
			artifactId = "lowcitory"
			version = version

			println(String.format(m, groupId, artifactId, version))

			artifact(tasks["javadocJar"])
			artifact(tasks["sourcesJar"])
		}
	}
	repositories { mavenLocal() }
}

artifacts {
	add("archives", tasks["javadocJar"])
	add("archives", tasks["sourcesJar"])
}

