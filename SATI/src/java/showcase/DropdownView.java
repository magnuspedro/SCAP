/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package showcase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author matheus
 */
@ManagedBean
@ViewScoped
public class DropdownView implements Serializable {

    private Map<String, Boolean> option;
    private String title;
    private boolean selected;

    @PostConstruct
    public void initYesorNo() {
        setOption(new HashMap<>());
        getOption().put("Sim",true);
        getOption().put("Não", false);
    }


    public void displayLocation() {
        FacesMessage msg;
        if (option != null) {
            msg = new FacesMessage("Selecionado", title + " "+String.valueOf(selected));
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "Não selecionado");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the option
     */
    public Map<String, Boolean> getOption() {
        return option;
    }

    /**
     * @param option the option to set
     */
    public void setOption(Map<String, Boolean> option) {
        this.option = option;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
