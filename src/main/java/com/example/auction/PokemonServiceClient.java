package com.example.auction;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/pokemon")
@RegisterRestClient(configKey="pokemon-service")
public interface PokemonServiceClient {
    @GET
    @Path("/random")
    Pokemon getRandomPokemon();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    Pokemon getPokemonById(@PathParam("id") Long id);


    //mariem tab3th l mise a prix ta3 l pokemon
    @GET
    @Path("/{id}/price")
    Double getPokemonPriceById(@PathParam("id") Long id);



    @POST
    @Path("/transfer-history/{pokemonId}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response transferAuctionHistory(@PathParam("pokemonId") Long pokemonId, Enchere enchere);

    /*{
    Pokemon pokemon=pokemonservice.findpokemonbyId(pokemonID);
    pokemon.addTOhisoty(enchere);
}
*/
//il faut configurer chaque l'url du service pokemon dans app property
    /*# URL du service Pokémon
    pokemon-service/mp-rest/url=http://localhost:8081
    pokemon-service/mp-rest/scope=javax.inject.Singleton
    et il faut que ca sera le meme pour userserviceclient
    */

}


