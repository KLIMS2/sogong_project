plugins {
	java
	id("org.springframework.boot") version "3.5.10"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.ysj"
version = "0.0.1-SNAPSHOT"
description = "web project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// 스피링시큐리티
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	
	// 타임리프 + 레이아웃
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// DB (mariaDB)
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

	// DB (h2)
	runtimeOnly("com.h2database:h2")

	// OAuth2
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	// JSoup
	implementation("org.jsoup:jsoup:1.15.4")

	// valid
	implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
