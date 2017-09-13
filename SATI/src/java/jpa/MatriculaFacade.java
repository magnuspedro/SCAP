/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
import entities.Evento;
import entities.Matricula;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author matheus
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> {

    @PersistenceContext(unitName = "SATIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }

    public void edit(MatriculaFacade entity) {
        for (Object object : super.findAll()) {
            
        }
        getEntityManager().merge(entity);
    }

    public List<Matricula> findByAluno(Aluno aluno){
        return em.createNamedQuery("Matricula.findByAluno").setParameter("aluno", aluno).getResultList();
    }
    
    public int vagasFechadas(Evento e){
        return (int) em.createNamedQuery("Matricula.countVagas").setParameter("evento", e).getSingleResult();
    }
}
