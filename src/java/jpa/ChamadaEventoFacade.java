/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
import entities.ChamadaEvento;
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
public class ChamadaEventoFacade extends AbstractFacade<ChamadaEvento> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChamadaEventoFacade() {
        super(ChamadaEvento.class);
    }

    public ChamadaEvento findChamadaEventoByRa(int id) {
        return (ChamadaEvento) em.createNamedQuery("ChamadaEvento.findByIdAluno").setParameter("idaluno", id).getSingleResult();
    }
    
    public Long TotalPalestra(int id){
        return (Long) em.createNamedQuery("ChamadaEvento.CountTotal").setParameter("dataEvento", id).getSingleResult();
    }

    public ChamadaEvento findSituacao(ChamadaEvento chamadaEvento) {
        List<ChamadaEvento> list = em.createNamedQuery("ChamadaEvento.findByAlunoandDataEvento")
                .setParameter("aluno", chamadaEvento.getIdaluno())
                .setParameter("dataEvento", chamadaEvento.getIddataEvento())
                .getResultList();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public boolean TotalPalestra(DataEvento dataEvento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
