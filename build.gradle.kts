plugins {
  java
  id("org.springframework.boot") version "3.5.6"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.anateam"
version = "0.0.1-SNAPSHOT"
description = "Delivery company Spring Application"

// INFO: cause:
// > Could not resolve all dependencies for configuration ':compileClasspath'.
// > Failed to calculate the value of task ':compileJava' property 'javaCompiler'.
// > Cannot find a Java installation on your machine (Linux 6.17.1 amd64) matching: {languageVersion=17, vendor=any vendor, implementation=vendor-specific, nativeImageCapable=false}. Toolchain download repositories have not been configured.
//
// java {
//   toolchain {
//     languageVersion = JavaLanguageVersion.of(17)
//   }
// }

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa" )
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
  implementation("jakarta.persistence:jakarta.persistence-api")
  implementation("io.jsonwebtoken:jjwt-api:0.12.5")

  compileOnly("org.projectlombok:lombok")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

  annotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
