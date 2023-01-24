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
        * [Person.java](#person-java)
        * [PersonContextInitializer.java](#personcontextinitializer-java)
        * [DataGridApiEndPoint.java](#datagridapiendpoint-java)
    * [Application.properties](#application-properties)    
* [Related Guides](#Related-guides)    

## Starting Red Hat Data Grid
You can run Red Hat Data Grid executing command
```shell
$ ${DATAGRID_HOME}/bin/./server.sh
```
> **_NOTE:_** This smart start does not have intended to teach how to install the Data Grid or best practices. For information on installation and configuration at https://access.redhat.com/documentation/en-us/red_hat_data_grid/8.4 .
## Running the application in dev mode
You can run your application in dev mode that enables live coding using:
```shell
$ ./mvnw compile quarkus:dev
```
> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Testing API application

## Understanding Java code

### Java Classes

#### Person.java
#### PersonContextInitializer.java
#### DataGridApiEndPoint.java
### Application.properties    
## Related Guides

- Infinispan Client ([guide](https://quarkus.io/guides/infinispan-client)): Connect to the Infinispan data grid for distributed caching
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
