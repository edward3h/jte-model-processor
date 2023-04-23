plugins {
    java
    id("gg.jte.gradle") version "2.3.1"
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor(project(":processor"))
    implementation("gg.jte:jte:2.3.1")
    implementation(project(":runtime"))
}

jte {
    generate()
}

tasks.compileJava {
//    dependsOn("generateJte")
    options.compilerArgs.addAll(listOf("-XprintProcessorInfo", "-XprintRounds"))
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.9.1")
        }
    }
}