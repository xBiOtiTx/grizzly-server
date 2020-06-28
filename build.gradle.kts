plugins {
    kotlin("jvm") version "1.3.72"
}

group = "org.org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.glassfish.grizzly:grizzly-websockets:2.4.0")
    implementation("org.glassfish.grizzly:grizzly-http-server:2.4.0")
    implementation("org.glassfish.grizzly:grizzly-http-servlet:2.4.0")

    implementation("org.springframework.boot:spring-boot:2.3.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-jersey:2.3.0.RELEASE")
//    {
//        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
//    }

//    implementation("org.springframework.boot:spring-boot-autoconfigure:2.3.0.RELEASE")
    implementation("org.yaml:snakeyaml")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.springframework:spring-websocket:5.2.2.RELEASE")
    testImplementation("org.springframework:spring-messaging:5.2.2.RELEASE")
//    testImplementation("javax.websocket:javax.websocket-api:1.1")
//    testImplementation("org.springframework.boot:spring-boot-starter-web")
//    testImplementation("org.springframework.boot:spring-boot-starter-websocket")
    testImplementation("org.springframework.boot:spring-boot-starter-websocket:2.3.0.RELEASE")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}