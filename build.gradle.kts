import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61" apply false
    kotlin("plugin.jpa") version "1.3.61" apply false
    idea
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.springframework.boot") version "2.2.4.RELEASE" apply false
    java
    id("com.google.protobuf") version "0.8.11" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("idea")
        plugin("io.spring.dependency-management")
    }

    configure<DependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    group = "com.hojongs"
    version = ""

    dependencies {
        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.projectlombok:lombok:0.11.0")
        implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")

        implementation ("org.apache.logging.log4j:log4j-api")
        implementation ("org.apache.logging.log4j:log4j-core")
        testImplementation(kotlin("test"))
        testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
        testImplementation("io.kotlintest:kotlintest-core:3.1.9")
        testImplementation("io.kotlintest:kotlintest-assertions:3.1.9")
        testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.9")
        testImplementation ("org.junit.platform:junit-platform-runner:1.9.1")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
