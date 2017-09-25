/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
import entities.ChamadaEvento;
import entities.DataEvento;
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

    public ChamadaEvento findSituacao(ChamadaEvento chamadaEvento) {
        return (ChamadaEvento) em.createNamedQuery("ChamadaEvento.findByAlunoandDataEvento")
                .setParameter("aluno", chamadaEvento.getIdaluno())
                .setParameter("dataEvento", chamadaEvento.getIddataEvento())
                .getResultList().get(0);
    }

    public ChamadaEvento findSituacao(Aluno a, DataEvento de) {
        return (ChamadaEvento) em.createNamedQuery("ChamadaEvento.findByAlunoandDataEvento")
                .setParameter("aluno", a)
                .setParameter("dataEvento", de).getSingleResult();
    }
}
