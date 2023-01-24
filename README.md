# How to use Data Grid Proto Buffer in a quarkus native cloud application 

This smart start demostrates how to use a Data Grid Proto Buffer running in a quarkus native cloud application usinh hotrod protocol. This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Requeriments
* OpenJDK 17
* Apache Maven 3.8.5
* VSCode or any IDE maven supportable
* Red Hat Data Grid 8.4.0

## Summary
* [Starting Red Hat Data Grid](#starting-red-hat-data-grid)
* [Running the application in dev mode](#running-the-application-in-dev-mode)
* [Testing API application](#testing-api-application)
* [Understanding Java code](#understanding-java-code)
    * [Java Classes](#java-classes)
        * [Person.java](#person.java)
        * [PersonContextInitializer.java](#personcontextinitializer.java)
        * [DataGridApiEndPoint.java](#datagridapiendpoint.java)
    * [Application.properties](#application.properties)    

## Starting Red Hat Data Grid

## Running the application in dev mode

## Testing API application

## Understanding Java code

### Java Classes

#### Person.java
#### PersonContextInitializer.java
#### DataGridApiEndPoint.java
### Application.properties    


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-datagrid-sample-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Infinispan Client ([guide](https://quarkus.io/guides/infinispan-client)): Connect to the Infinispan data grid for distributed caching
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
