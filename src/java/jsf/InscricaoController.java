/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import entities.Evento;
import entities.EventosInstrutores;
import entities.Matricula;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import jpa.EventosInstrutoresFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author matheus
 */
@ManagedBean
public class InscricaoController implements Serializable {

    @EJB
    private EventosInstrutoresFacade eventosInstrutoresFacade;

    private ArrayList<Matricula> minicursos = null;
    private jpa.MatriculaFacade ejbfacade;
    private Matricula current;
    private Evento evento;
    private final Map<Evento, Boolean> minicurso = null;

    public InscricaoController() {

    }

    public String create() {
        
        return "";
    }
    

    /**
     * @return the minicursos
     */
    public ArrayList<Matricula> getMinicursos() {
        return minicursos;
    }

    /**
     * @param minicursos the minicursos to set
     */
    public void setMinicursos(ArrayList<Matricula> minicursos) {
        this.minicursos = minicursos;
    }

    /**
     * @return the ejbfacade
     */
    public jpa.MatriculaFacade getEjbfacade() {
        return ejbfacade;
    }

    /**
     * @param ejbfacade the ejbfacade to set
     */
    public void setEjbfacade(jpa.MatriculaFacade ejbfacade) {
        this.ejbfacade = ejbfacade;
    }

    public void salvarMiniCursos() {
        getMinicursos().forEach((Matricula minicurso) -> {
            getEjbfacade().create(minicurso);
        });
    }

    public void addMini(ValueChangeEvent event) {
        EventosInstrutores ei = (EventosInstrutores) event.getNewValue();
        System.err.println(ei.getIdevento().getNome());
    }

    public void carregaEventoIntrutores() {
        List<EventosInstrutores> ei = eventosInstrutoresFacade.uniqueMiniCurso();
        ei.forEach((_item) -> {
            minicurso.put(evento, Boolean.FALSE);
        });

    }

    /**
     * @return the evento
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * @return the minicurso
     */
    public Map<Evento, Boolean> getMinicurso() {
        return minicurso;
    }

}
