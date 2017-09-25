/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import entities.Chamada;
import entities.ChamadaEvento;
import entities.DataEvento;
import entities.Evento;
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

    public List<Chamada> fingByIdEvento(DataEvento dataEvento) {
        String sql = "SELECT * FROM data_evento \n"
                + "INNER JOIN evento ON data_evento.idevento = evento.idevento \n"
                + "INNER JOIN eventos_instrutores ON evento.idevento = eventos_instrutores.idevento \n"
                + "INNER JOIN instrutor ON eventos_instrutores.idinstrutor = instrutor.idinstrutor \n"
                + "WHERE instrutor.idinstrutor = " + dataEvento.getIdevento().getIdevento();
        return em.createQuery(sql).getResultList();
    }

    public List<DataEvento> retornaDataEvento() {
        List<DataEvento> list = (List<DataEvento>) em.createNamedQuery("DataEvento.findByHoraEvento").setParameter("idinstrutor", 1).getResultList();
        return list;

    }

    public DataEvento uniqueDataEvento(int id) {
        return (DataEvento) em.createNamedQuery("DataEvento.findByEvento").setParameter("idevento", id).getSingleResult();
    }

    public List<Chamada> carregaChamada(DataEvento dataEvento) {
        return em.createNamedQuery("Chamada.findByIddataEvento").setParameter("iddataEvento", dataEvento).getResultList();
    }

    public List<ChamadaEvento> carregaChamadaPalestra(DataEvento dataEvento) {
        return em.createNamedQuery("ChamadaEvento.findByIddataEvento").setParameter("iddataEvento", dataEvento).getResultList();
    }

    public List<DataEvento> carregaPaletras() {
        return em.createNamedQuery("DataEvento.findPalestra").setParameter("tipo", "Palestra").getResultList();
    }

    public List<DataEvento> listAllOrd() {
        return em.createNamedQuery("DataEvento.listAll").getResultList();
    }

    public int vagasFechadas(Evento e) {
        Long valor = (Long) em.createNamedQuery("Matricula.countVagas").setParameter("evento", e).getSingleResult();
        return valor.intValue();
    }
    
    public List<DataEvento> listPalestras(){
        return em.createNamedQuery("DataEvento.findPalestra").setParameter("tipo", "Palestra").getResultList();
    }
}
