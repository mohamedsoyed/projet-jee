package com.example.Pokemon;



import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/pokemons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class PokemonRessource {

    @Inject
    PokemonService pokemonService;


    @GET
    public List<Pokemon> listerPokemons() {
        return pokemonService.listerPokemons();
    }

   /* @GET
    @Path("/random")
    public Pokemon pokemonAleatoire() {
        return pokemonService.genererPokemonAleatoire();
    }*/





    @POST
    public Pokemon creerPokemon(Pokemon pokemon){
        return pokemonService.creerPokemon(pokemon.getNom(), pokemon.getDescription(), pokemon.getValeurReelle());
    }

    @GET
    @Path("/{id}")
    public Pokemon trouverPokemon(@PathParam("id") Long id) {
        return pokemonService.trouverPokemon(id);
    }

    @DELETE
    @Path("/{id}")
    public void supprimerPokemon(@PathParam("id") Long id) {
        pokemonService.supprimerPokemon(id);
    }
}
