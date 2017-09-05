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
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author matheus
 */
@ManagedBean
public class InscricaoController implements Serializable {

    private ArrayList<Matricula> minicursos = null;
    private jpa.MatriculaFacade ejbfacade;
    private Evento evento;

    public InscricaoController() {
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
    
    public void addMini(ValueChangeEvent event){
        EventosInstrutores ei = (EventosInstrutores) event.getNewValue();
        System.err.println(ei.getIdevento().getNome());
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

}
