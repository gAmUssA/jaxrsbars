package com.jamesward.jaxrsbars;

import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class BarResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "<!DOCTYPE html> " +
                "<html>" +
                "<head>" +
                "<script type='text/javascript' src='" + BarServer.contentUrl + "jquery-1.7.min.js'></script>" +
                "<script type='text/javascript' src='" + BarServer.contentUrl + "index.js'></script>" +
                "</head>" +
                "</html>";
    }

    @Path("addBar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bar addBar(Bar bar) {
        WriteResult<Bar, String> result = getJacksonDBCollection().insert(bar);
        return result.getSavedObject();
    }
    
    @Path("listBars")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bar> listBars() {
        return getJacksonDBCollection().find().toArray();
    }


    private JacksonDBCollection<Bar, String> getJacksonDBCollection() {
        return JacksonDBCollection.wrap(BarServer.mongoDB.getCollection(Bar.class.getSimpleName().toLowerCase()), Bar.class, String.class);
    }

}