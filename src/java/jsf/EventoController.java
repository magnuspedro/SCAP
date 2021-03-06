package jsf;

import entities.DataEvento;
import entities.Evento;
import entities.Instrutor;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.EventoFacade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "eventoController")
@SessionScoped
public class EventoController implements Serializable {

    private Evento current;
    private DataModel items = null;
    @EJB
    private jpa.EventoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Evento> eventos = null;
    private List<Evento> eventosListaEspera = null;

    public EventoController() {
    }

    public Evento getSelected() {
        if (current == null) {
            current = new Evento();
            selectedItemIndex = -1;
        }

        return current;
    }

    private EventoFacade getFacade() {
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
        current = (Evento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new Evento();
        selectedItemIndex = -1;
        recreateModel();
        return "Create_1";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Evento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventoUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Evento) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("EventoDeleted"));
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

    public Evento getEvento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public List<Evento> ListaEventoInstrutor(Instrutor instrutor) {
        return ejbFacade.findbyInstrutor(instrutor);
    }

    /**
     * @return the eventos
     */
    public List<Evento> getEventos() {
        carregaMiniCursos();
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Evento> carregaMiniCursos() {
        eventos = ejbFacade.findAll();
        for (Evento item : eventos) {
            int ocupadas = ejbFacade.vagasFechadas(item);
            if ((item.getVagasTotais() - ocupadas) == 0) {
                item.setVagasTotais(0);
            } else {
                item.setVagasTotais(item.getVagasTotais() - ocupadas);

            }
        }
        return eventos;
    }

    /**
     * @return the eventosListaEspera
     */
    public List<Evento> getEventosListaEspera() {
        eventosListaEspera = ejbFacade.findMinicursos();
        return eventosListaEspera;
    }

    /**
     * @param eventosListaEspera the eventosListaEspera to set
     */
    public void setEventosListaEspera(List<Evento> eventosListaEspera) {
        this.eventosListaEspera = eventosListaEspera;
    }

    @FacesConverter(forClass = Evento.class)
    public static class EventoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventoController controller = (EventoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventoController");
            return controller.getEvento(getKey(value));
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
            if (object instanceof Evento) {
                Evento o = (Evento) object;
                return getStringKey(o.getIdevento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Evento.class.getName());
            }
        }

    }
}
