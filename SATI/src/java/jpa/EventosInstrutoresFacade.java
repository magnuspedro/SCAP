/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Evento;
import entities.EventosInstrutores;
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

    public EventosInstrutoresFacade() {
        super(EventosInstrutores.class);
    }

    public List<EventosInstrutores> buscaInstrutor(int id) {
        javax.persistence.Query q = getEntityManager().createNamedQuery("EventosIntrutores.findByIdinstrutor");
        return q.getResultList();
    }

}
