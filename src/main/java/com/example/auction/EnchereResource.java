package com.example.auction;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/Enchere")
public class EnchereResource {

    @Inject
    private EnchereManager manager;

    @Inject
    private EnchereService es;

    //on doit develppé une intrface pour le client rest
    @Inject
    @RestClient
    private PokemonServiceClient pokemonServiceClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/{Bid}")
    @Transactional
    public Response placerBid(@PathParam("id") Long enchereid,@QueryParam("userId") Long userID ,@QueryParam("Bid") double bid){

        Enchere enchere=manager.findEnchere(enchereid);
        String msg=es.placerBid(enchereid,userID,bid);

     if ("ecnhere not found".equals(msg)) {
        return Response.status(Response.Status.NOT_FOUND).entity("enchere not found").build();
    }
    if("Mise trop faible".equals(msg)){
        return Response.status(Response.Status.BAD_REQUEST).entity("mise en place trés faible").build();
    }
    return Response.ok(enchere).build();


    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Enchere getEncherebyId(@PathParam("id") Long id){
        Enchere e= manager.findEnchere(id);

        return e;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Enchere> getAllEncheres(){
        return manager.findActiveAuctions();
    }

    @GET
    @Path("/{type}")
    public List<Enchere> getAllEncheresByType(@PathParam("type") String type){
        return es.getEnchereParType(type);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnchere(@PathParam("id") Long id){
        Enchere e= manager.findEnchere(id);
        if(e==null){return Response.status(Response.Status.NOT_FOUND).entity("enchere not found").build();}
        return Response.ok(e).build();
    }



}
