/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matheus
 */
@Stateless
public class AlunoFacade extends AbstractFacade<Aluno> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(Aluno.class);
    }
    
}
