/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Aluno;
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

    public Aluno findRA(Object id) {
        return (Aluno) getEntityManager().createNamedQuery("Aluno.findByRa").getSingleResult();
    }

    public Aluno findIdByRa(String RA){
        return (Aluno) em.createNamedQuery("Aluno.findByRa").setParameter("ra", RA).getSingleResult();
    }


    public List<Matricula> findByAlunoPago(Aluno aluno) {
        return em.createNamedQuery("Matricula.findByAlunoPago").setParameter("aluno", aluno).getResultList();
    }

    public List<Matricula> findByAluno(Aluno aluno) {
        return em.createNamedQuery("Matricula.findByAluno").setParameter("aluno", aluno).getResultList();
    }

    
    public Aluno findByCPF(String cpf) {//Exemplo
        List<Aluno> aluno = em.createNamedQuery("Aluno.findByCpf").setParameter("cpf", cpf).getResultList();
        if (aluno.isEmpty()) {
            return null;
        }
        return aluno.get(0);
    }
    
}
