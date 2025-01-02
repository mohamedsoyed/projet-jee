package com.example.auction;

import com.example.Pokemon.Pokemon;
import com.example.Pokemon.PokemonService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EnchereManager {
    @Inject
    PokemonService pokemonServiceClient;
    @Inject
    private EntityManager em;

    public List<Enchere> findActiveAuctions(){
               return em.createQuery("select a from Enchere a where a.Status=:status",Enchere.class).setParameter("status","active").getResultList();
    }

    public Enchere findEnchere(Long id){
        return em.createQuery("select e from Enchere e where e.id=:id",Enchere.class).setParameter("id",id).getSingleResult();

    }

    @Transactional
    public void createEncher(Enchere encher){
        em.persist(encher);
    }

    public int countActiveAuctions(){
        return em.createQuery("select count(a) from Enchere a where a.Status=:status",Integer.class).setParameter("status","active").getSingleResult();

    }

    public List<Enchere> findExpiredEncheres(){
        return em.createQuery("select e from Enchere e where e.Status=:status AND e.dateExpiration<:now" ,Enchere.class).setParameter("status","active").setParameter("now", LocalDateTime.now()).getResultList();
    }

    @Transactional
    public void miseAjourEnchere(Enchere encher){
        em.merge(encher);
    }


    public List<Enchere> getEnchereParType(String string){
            List<Enchere> encheres=findActiveAuctions();
            List<Enchere> enchreretourne=new ArrayList<>();
            for(Enchere e:encheres){
                Pokemon pokemon=pokemonServiceClient.trouverPokemon(e.getPokemonId());
                for(String s: pokemon.getTypes()){
                    if(s.equalsIgnoreCase(string)){
                        enchreretourne.add(e);
                    }
                }
            }
         return enchreretourne;
    }
//getEnchereByUserId
}
