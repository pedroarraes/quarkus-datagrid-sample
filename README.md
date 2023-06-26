# How to use Data Grid Proto Buffer in a quarkus native cloud application 

This smart start demostrates how to use a Data Grid Proto Buffer running in a quarkus native cloud application using hotrod protocol. This project uses Quarkus, the Supersonic Subatomic Java Framework.
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
* (1) Indexed class, means that attributes will be sorted by the attribute annotated with Keyword
* (2) ProtoFactory, all attributes in constructor will be automatically serializable in the marshal and unmarshal will be doing automatically by the framework.
* (3) ProtoField, means this attribute will be serializable, and marshal/unmarshal will be doing automatically by the framework.
* (4) Keyword, index attribute.
#### PersonContextInitializer
```java
package org.acme;

import org.infinispan.protostream.SerializationContextInitializer;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = { Person.class }, schemaPackageName = "person_list") 
interface PersonContextInitializer extends SerializationContextInitializer {

}
```
* This interface will create the file person_list.proto automatically.
#### DataGridApiEndPoint
```java
package org.acme;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.jboss.logging.Logger;

import io.quarkus.infinispan.client.Remote;

@Path("/api")
@ApplicationScoped
public class DataGridApiEndPoint {
	
	private static final Logger LOGGER = Logger.getLogger(DataGridApiEndPoint.class);

    @Inject
    @Remote("person-data")
    RemoteCache<String, Person> cache;
    
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
    	
    	Person p = cache.get(id);
    	
    	LOGGER.info("Selected " + p.toString());
    	
    	return Response.ok(p).status(200).build();
    }
    
    
    @GET
    @Path("query/{birthYear}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getByBirthYear(@PathParam("birthYear") Integer birthYear) {
    	
    	QueryFactory qf = org.infinispan.client.hotrod.Search.getQueryFactory(cache);
    	Query<Person> q = qf.create("FROM person_list.Person WHERE birthYear = :birthYear ORDER BY name ASC");
    	q.setParameter("birthYear", birthYear);
    	
    	QueryResult<Person> qr = q.execute();
    	
    	List<Person> persons = qr.list();

    	return Response.ok(persons).status(200).build();
    }
    
       
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Person p) {
    	
    	cache.put(p.getId(), p);
    	
    	LOGGER.info("Inserted " + p.toString());
    	
    	return Response.ok(p).status(201).build();
    }
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
    	
    	cache.remove(id);
    	
    	LOGGER.info("Deleted " + id);
    	
    	return Response.ok().status(202).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Person p) {
    	
    	cache.put(p.getId(), p);
    	
    	LOGGER.info("Updated " + p.toString());
    	
    	return Response.ok(p).status(202).build();
    }
    
}
```
* This class expose API rest to test the application methods.
### Application.properties    
```properties
quarkus.infinispan-client.server-list=${SERVER_LIST:localhost:11222}
quarkus.infinispan-client.auth-username=${USER_NAME:admin}
quarkus.infinispan-client.auth-password=${PASSWORD:changeme}
quarkus.infinispan-client.client-intelligence=BASIC
quarkus.infinispan-client.auth-realm=default
quarkus.infinispan-client.sasl-mechanism=SCRAM-SHA-512
quarkus.http.access-log.pattern=common
```
* This file enable the quarkus application connect to Data Grid using hot rod protocol, for OpenShift or container environments is recommended use secrets as system properties.

## Related Guides
- Infinispan Client ([guide](https://quarkus.io/guides/infinispan-client)): Connect to the Infinispan data grid for distributed caching.
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI.
- RESTful Web Services ([guide](https://quarkus.io/guides/getting-started#the-jax-rs-resources)): Easily start your RESTful Web Services.



