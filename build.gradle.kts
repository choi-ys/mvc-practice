plugins {
    id("java")
}

group = "io.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.apache.tomcat.embed:tomcat-embed-core:8.5.42")
    implementation ("org.apache.tomcat.embed:tomcat-embed-jasper:8.5.42")

    implementation ("javax.servlet:javax.servlet-api:4.0.1")
    implementation ("javax.servlet:jstl:1.2")

    implementation ("org.reflections:reflections:0.9.12")

    implementation ("ch.qos.logback:logback-classic:1.2.3")

    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation ("org.assertj:assertj-core:3.25.3")
}

tasks.test {
    useJUnitPlatform()
}
