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

    public List<Matricula> findByAlunoNPago(Aluno aluno) {
        return em.createNamedQuery("Matricula.findByAlunoNPago").setParameter("aluno", aluno).getResultList();
    }

    public List<Matricula> findByAlunoPago(Aluno aluno) {
        return em.createNamedQuery("Matricula.findByAlunoPago").setParameter("aluno", aluno).getResultList();
    }

    public int vagasFechadas(Evento e) {
        return (int) em.createNamedQuery("Matricula.countVagas").setParameter("evento", e).getSingleResult();
    }

    public Long countPosicao(Matricula matricula) {
        return (Long) em.createNamedQuery("Matricula.contPosicao")
                .setParameter("idmatricula", matricula.getIdmatricula())
                .setParameter("evento", matricula.getIdevento())
                .getSingleResult();
    }
}
