package com.example.auction;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/Users")
public interface UserServiceClient {
    @GET
    @Path("/{id}")
    User getUser(@PathParam("id") Long id);

    @POST
    @Path("/{userid}/{pokemonid}")
    void addPokemonToUser(@PathParam("userid") Long userid, @PathParam("pokemonid") Long pokemonid);

    @GET
    @Path("/{userid}/{amount}")
    Boolean deductLimcoins(@PathParam("userid") Long userid, @PathParam("amount") double amount);


}