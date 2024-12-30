package com.example.auction;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EnchereManager {
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

}
