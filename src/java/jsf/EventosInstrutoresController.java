package jsf;

import entities.Evento;
import entities.EventosInstrutores;
import entities.Instrutor;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.EventosInstrutoresFacade;

import java.io.Serializable;
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

@ManagedBean(name = "eventosInstrutoresController")
@SessionScoped
public class EventosInstrutoresController implements Serializable {

    /**
     * @return the eventosinstrutores
     */
    public List<EventosInstrutores> getEventosinstrutores() {
        return eventosinstrutores;
    }

    /**
     * @param eventosinstrutores the eventosinstrutores to set
     */
    public void setEventosinstrutores(List<EventosInstrutores> eventosinstrutores) {
        this.eventosinstrutores = eventosinstrutores;
    }
    
    private EventosInstrutores current;
    private DataModel items = null;
    @EJB
    private jpa.EventosInstrutoresFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Evento> eventos = null;
    private List<EventosInstrutores> eventosinstrutores = null;
    
    public EventosInstrutoresController() {
    }
    
    public EventosInstrutores getSelected() {
        if (current == null) {
            current = new EventosInstrutores();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    private EventosInstrutoresFacade getFacade() {
        return ejbFacade;
    }
    
    public void test(){
        System.out.print(""+ejbFacade.teste().get(0).getIdevento().getDataEventoCollection());
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
        current = (EventosInstrutores) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }
    
    public String prepareCreate() {
        current = new EventosInstrutores();
        selectedItemIndex = -1;
        recreateModel();
        return "Create_1";
    }
    
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventosInstrutoresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String prepareEdit() {
        current = (EventosInstrutores) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventosInstrutoresUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String destroy() {
        current = (EventosInstrutores) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventosInstrutoresDeleted"));
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
    
    public EventosInstrutores getEventosInstrutores(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    @FacesConverter(forClass = EventosInstrutores.class)
    public static class EventosInstrutoresControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventosInstrutoresController controller = (EventosInstrutoresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventosInstrutoresController");
            return controller.getEventosInstrutores(getKey(value));
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
            if (object instanceof EventosInstrutores) {
                EventosInstrutores o = (EventosInstrutores) object;
                return getStringKey(o.getIdeventosInstrutor());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EventosInstrutores.class.getName());
            }
        }
        
    }
    
    public SelectItem[] selecionaEventos(ValueChangeEvent event) {
        //current.setIdinstrutor((Instrutor) event.getNewValue());
        System.err.println((Instrutor) event.getNewValue());
        if (current.getIdinstrutor() != null && current.getIdinstrutor().getIdinstrutor() > 0) {
            return JsfUtil.getSelectItems(ejbFacade.buscaInstrutor(current.getIdinstrutor().getIdinstrutor()), true);
        }
        return JsfUtil.getSelectItems(ejbFacade.buscaInstrutor(0), true);
    }
    
    public void selectOneMenuListener(ValueChangeEvent event){
        current.setIdinstrutor((Instrutor) event.getNewValue());
        setEventos(ejbFacade.findByInstrutor(current.getIdinstrutor()));
    }
    
    public void carregaMiniCursos(){
        setEventosinstrutores(ejbFacade.uniqueMiniCurso());
    }

    /**
     * @return the eventos
     */
    public List<Evento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
