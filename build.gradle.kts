plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "obed.mz"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
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
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("io.r2dbc:r2dbc-h2")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("io.r2dbc:r2dbc-h2")

	annotationProcessor("org.projectlombok:lombok")
	testRuntimeOnly("org.projectlombok:lombok")
	implementation ("org.springframework.cloud:spring-cloud-starter")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
	implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")

	testImplementation("org.springframework.security:spring-security-test")

}
dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.2")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	jvmArgs("-XX:+EnableDynamicAgentLoading")
}
