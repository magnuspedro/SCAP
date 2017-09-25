package jsf;

import entities.Aluno;
import entities.ChamadaEvento;
import entities.DataEvento;
import entities.Evento;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.ChamadaEventoFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.DataEventoFacade;

@ManagedBean(name = "chamadaEventoController")
@SessionScoped
public class ChamadaEventoController implements Serializable {

    @EJB
    private DataEventoFacade dataEventoFacade;
    private ChamadaEvento current;
    private DataModel items = null;
    @EJB
    private jpa.ChamadaEventoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<ChamadaEvento> chamada;
    private Aluno aluno = new Aluno();
    private DataEvento dataEvento = new DataEvento();
    @EJB
    private jpa.AlunoFacade ejbFacadeAluno;

    public ChamadaEventoController() {
    }

    public ChamadaEvento getSelected() {
        if (current == null) {
            current = new ChamadaEvento();
            current.setIdaluno(new Aluno());
            current.setIddataEvento(new DataEvento());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ChamadaEventoFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "Create_1";
    }

    public String prepareView() {
        current = (ChamadaEvento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new ChamadaEvento();
        selectedItemIndex = -1;
        recreateModel();
        return "Create_1";
    }

    public void selectOneMenuListener(ValueChangeEvent event) {
        dataEvento = (DataEvento) event.getNewValue();
    }

    public String create() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaEventoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ChamadaEvento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaEventoUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ChamadaEvento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "Create_1";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "Create_1";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "Create_1";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaEventoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "Create_1";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "Create_1";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ChamadaEvento getChamadaEvento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the chamada
     */
    public List<ChamadaEvento> getChamada() {
        return chamada;
    }

    /**
     * @param chamada the chamada to set
     */
    public void setChamada(List<ChamadaEvento> chamada) {
        this.chamada = chamada;
    }

    /**
     * @return the aluno
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * @param aluno the aluno to set
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void pegaEvento(ValueChangeEvent event) {
        current.setIddataEvento((DataEvento) event.getNewValue());
        setChamada(dataEventoFacade.carregaChamadaPalestra(current.getIddataEvento()));
    }

    public String findExactRA() {
        if (!aluno.getRa().isEmpty()) {
            aluno = ejbFacadeAluno.findIdByRa(aluno.getRa());
            System.out.println(aluno.getCpf());
            Date d = new Date(System.currentTimeMillis());
            System.err.println(d);
            current.setHora(d);
            System.out.println(dataEvento.getIddataEvento());
            current.setIddataEvento(dataEvento);
            current.setIdaluno(aluno);
            create();
            recreateModel();
            return "ChamadaEvento";
        }
        return null;
    }

    @FacesConverter(forClass = ChamadaEvento.class)
    public static class ChamadaEventoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ChamadaEventoController controller = (ChamadaEventoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "chamadaEventoController");
            return controller.getChamadaEvento(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ChamadaEvento) {
                ChamadaEvento o = (ChamadaEvento) object;
                return getStringKey(o.getIdchamada());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ChamadaEvento.class.getName());
            }
        }

    }

}