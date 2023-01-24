package org.acme;

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
import org.jboss.logging.Logger;

import io.quarkus.infinispan.client.Remote;

@Path("/api")
@ApplicationScoped
public class DataGridApiEndPoint {
	
	private static final Logger LOGGER = Logger.getLogger(DataGridApiEndPoint.class);

    @Inject
    @Remote("example")
    RemoteCache<String, Person> cache;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
    	
    	Person p = cache.get(id);
    	
    	LOGGER.info("Selected " + p.toString());
    	
    	return Response.ok(p).status(200).build();
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
