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
        * [Person](#person)
        * [PersonContextInitializer](#personcontextinitializers)
        * [DataGridApiEndPoint](#datagridapiendpoint)
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
This session you'll run insert, select, update, and delete a data into Data Grid using an application API. You can use Swagger-ui at http://localhost:8080/q/swagger-ui/ or curl to execute. The nexts commands are demonstrated on curl.

* Inserting
```shell
$ curl -X 'POST' \
  'http://localhost:8080/api' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Joe",
  "surname": "Tribiani",
  "birthYear": 1980
}'
```
```console
{"id":"1","name":"Joe","surname":"Tribiani","birthYear":1980
```
```shell
$ curl -X 'POST' \
  'http://localhost:8080/api' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "2",
  "name": "Maria",
  "surname": "Alt",
  "birthYear": 1980
}'
```
```console
{"id":"2","name":"Maria","surname":"Alt","birthYear":1980}[
```
```shell
$ curl -X 'POST' \
  'http://localhost:8080/api' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "3",
  "name": "Daniel",
  "surname": "Bush",
  "birthYear": 1985
}'
```
```console
{"id":"3","name":"Daniel","surname":"Bush","birthYear":1985}
```
```shell
$ curl -X 'POST' \
  'http://localhost:8080/api' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "4",
  "name": "Angelica",
  "surname": "Santos",
  "birthYear": 19853
}'
```
```console
{"id":"4","name":"Angelica","surname":"Santos","birthYear":19853}
```
* Updating
```shell
$ curl -X 'PUT' \
  'http://localhost:8080/api' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "4",
  "name": "Angelica",
  "surname": "Santos",
  "birthYear": 1983
}'
```
```console
{"id":"4","name":"Angelica","surname":"Santos","birthYear":1983}
```
* Getting by KEY
```shell
$ curl -X 'GET' \
  'http://localhost:8080/api/2' \
  -H 'accept: */*'
```
```console
{"id":"2","name":"Maria","surname":"Alt","birthYear":1980}
```
* Getting by birthYear
```shell
$ curl -X 'GET' \
  'http://localhost:8080/api/query/1980' \
  -H 'accept: */*'
```
```console
[{"id":"1","name":"Joe","surname":"Tribiani","birthYear":1980},{"id":"2","name":"Maria","surname":"Alt","birthYear":1980}]
```
* Deleting
```shell
$ curl -X 'DELETE' \
  'http://localhost:8080/api/1' \
  -H 'accept: */*'
```
## Understanding Java code
In this session you will understand Java code, annotations, and comunication with Red Hat Data Grid.
### Java Classes
#### Person
```java
package org.acme;

import java.util.Objects;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Indexed (1)
public class Person {

	private String id;
	private String name;
	private String surname;
	private Integer birthYear;

	@ProtoFactory (2)
	public Person(String id, String name, String surname, Integer birthYear) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthYear = birthYear;
	}

	@ProtoField(number = 1) (3)
	public String getId() {
		return id;
	}

	@ProtoField(number = 2)
	public String getName() {
		return name;
	}

	@ProtoField(number = 3)
	public String getSurname() {
		return surname;
	}

	@ProtoField(number = 4)
	@Keyword(projectable = true, sortable = true) (4)
	public Integer getBirthYear() {
		return birthYear;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", birthYear=" + birthYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthYear, id, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(birthYear, other.birthYear) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}	
}
```
(1)
(2)
(3)
(4)
#### PersonContextInitializer
#### DataGridApiEndPoint
### Application.properties    
## Related Guides

- Infinispan Client ([guide](https://quarkus.io/guides/infinispan-client)): Connect to the Infinispan data grid for distributed caching
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
