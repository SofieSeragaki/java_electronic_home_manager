plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Hibernate Core
    implementation("org.hibernate:hibernate-core:5.6.15.Final")

    // MySQL JDBC Connector
    implementation("mysql:mysql-connector-java:8.0.18")

    // Hibernate Validator (за валидация)
    implementation("org.hibernate.validator:hibernate-validator:6.2.0.Final")

    // Bean Validation API (JSR 380)
    implementation("javax.validation:validation-api:2.0.1.Final")

    // JUnit dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
}
