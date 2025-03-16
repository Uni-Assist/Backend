plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.liquibase.gradle") version "2.2.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-json")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation ("org.hibernate.orm:hibernate-core:6.2.7.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.postgresql:postgresql")
	liquibaseRuntime("info.picocli:picocli:4.6.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Test> {
	enabled = false
}

tasks.register("docker", Copy::class) {
	dependsOn("build")
	from(layout.buildDirectory.dir("libs"))
	into("src/main/resources")
}