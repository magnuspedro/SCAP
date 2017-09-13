/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Evento;
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
public class EventoFacade extends AbstractFacade<Evento> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }

    public List<Evento> findbyInstrutor(Instrutor instrutor) {
        return em.createNamedQuery("Evento.findByInstrutor").setParameter("idinstrutor", 1).getResultList();
    }

    public int vagasFechadas(Evento e) {
        Long valor = (Long) em.createNamedQuery("Matricula.countVagas").setParameter("evento", e).getSingleResult();
        return valor.intValue();
    }
    
    public List<Evento> findMinicursos(){
        return em.createNamedQuery("Evento.findByTipo").setParameter("tipo", "Minicurso").getResultList();
    }
    
    public List<Evento> findPalestra(){
        return em.createNamedQuery("Evento.findByTipo").setParameter("tipo", "Palestra").getResultList();
    }
}
