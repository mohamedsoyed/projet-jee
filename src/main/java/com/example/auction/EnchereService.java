package com.example.auction;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class EnchereService {

    @Inject
    private EnchereManager em;

    @Inject
    @RestClient
    PokemonServiceClient pokemonService;

    @Inject
    private EnchereManager enem;

    @Inject
    private userService userservice;

   private Random random=new java.util.Random();

   @Transactional
   public void createAuctionAutomatically() {
       Pokemon pokemon=pokemonService.getRandomPokemon();
       double baseprice=pokemonService.getPokemonPriceById(pokemon.getId());

       Enchere enchere=new Enchere();
       enchere.setPokemonId(pokemon.getId());
       enchere.setStartingPrice(baseprice);
       enchere.setHighestBid(0);
       enchere.setHighestBidderId(null);
       enchere.setDateExpiration(LocalDateTime.now().plusHours(24));
       enchere.setStatus("active");
       pokemonService.transferAuctionHistory(enchere.getPokemonId(), enchere);
       em.createEncher(enchere);


   }

    @Transactional
    public void closeExpiredEnchere() {
        List<Enchere> expiredEncheres = enem.findExpiredEncheres();

        for (Enchere e : expiredEncheres) {
            if (e.getHighestBid() != 0 && e.getHighestBidderId() != null) {
                boolean paymentSuccessful = userservice.deductLimcoins(e.getHighestBidderId(), e.getHighestBid());

                if (paymentSuccessful) {
                    // Transférer le Pokémon au gagnant
                    userservice.addPokemonToUser(e.getHighestBidderId(), e.getPokemonId());
                    // Notifier le service Pokémon
                    pokemonService.transferAuctionHistory(e.getPokemonId(), e);
                } else {
                    // Passer au second meilleur enchérisseur
                    handleNextHighestBidder(e);
                }
            }
            e.setStatus("closed");
            enem.miseAjourEnchere(e);
        }
    }

    private void handleNextHighestBidder(Enchere enchere) {
        List<Bid> allBids = enchere.getBids(); // Récupère toutes les enchères associées
        allBids.sort((b1, b2) -> Double.compare(b2.getAmount(), b1.getAmount())); // Trier par montant décroissant

        for (int i = 1; i < allBids.size(); i++) { // Commence par le second enchérisseur
            Bid nextBid = allBids.get(i);
            boolean paymentSuccessful = userservice.deductLimcoins(nextBid.getUserId(), nextBid.getAmount());

            if (paymentSuccessful) {
                userservice.addPokemonToUser(nextBid.getUserId(), enchere.getPokemonId());
                break;
            }
        }
    }



    @Transactional
    public String placerBid(Long enchereId,Long userId,double amount){
       Enchere enchere=enem.findEnchere(enchereId);
       User user =userservice.getUser();
       if(enchere==null || !"active".equals(enchere.getStatus())){
           return "enchere not found!";
       }
       if(amount<enchere.getHighestBid()){
           return "Mise trop faible";
       }
        if (user.getPlAF) {
        }
       enchere.setHighestBid(amount);
       enchere.setHighestBidderId(userId);
       em.miseAjourEnchere(enchere);
       pokemonService.transferAuctionHistory(enchere.getPokemonId(), enchere);
       return "enchere mis";
   }

   //chercher enchere par type de pokemon
}
