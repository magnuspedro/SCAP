/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

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
public class DataEventoFacade extends AbstractFacade<DataEvento> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DataEventoFacade() {
        super(DataEvento.class);
    }
    
    public List<DataEvento> listName() {
        return (List<DataEvento>) em.createNamedQuery("DataEvento.listName");
    }

}
