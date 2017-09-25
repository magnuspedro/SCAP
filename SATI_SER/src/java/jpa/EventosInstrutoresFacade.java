/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Evento;
import entities.EventosInstrutores;
import entities.Instrutor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matheus
 */
@Stateless
public class EventosInstrutoresFacade extends AbstractFacade<EventosInstrutores> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<EventosInstrutores> teste(){
        return em.createNamedQuery("EventosInstrutores.findAll").getResultList();
    }

    public EventosInstrutoresFacade() {
        super(EventosInstrutores.class);
    }

    public List<EventosInstrutores> buscaInstrutor(int id) {
        javax.persistence.Query q = getEntityManager().createNamedQuery("EventosIntrutores.findByIdinstrutor");
        return q.getResultList();
    }
    
    public List<Evento> findByInstrutor(Instrutor instrutor){
        return em.createNamedQuery("EventosIntrutores.findByIdinstrutor")
                .setParameter("idinstrutor", instrutor)
                .getResultList();
    }

    public List<EventosInstrutores> uniqueMiniCurso(){        
        return em.createNamedQuery("EventosIntrutores.unitMiniCurso").getResultList();
    }
}
