/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
import entities.Chamada;
import entities.DataEvento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matheus
 */
@Stateless
public class ChamadaFacade extends AbstractFacade<Chamada> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChamadaFacade() {
        super(Chamada.class);
    }

    
    public List<Chamada> findChamadaByEvento(DataEvento dataEvento){
        return em.createNamedQuery("Chamada.nomeEvento").setParameter("idDataEvento", dataEvento.getIddataEvento()).getResultList();
    }

}
