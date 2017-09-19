package jsf;

import entities.Chamada;
import entities.ChamadaEvento;
import entities.DataEvento;
import entities.Evento;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.DataEventoFacade;

import java.io.Serializable;
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
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "dataEventoController")
@SessionScoped
public class DataEventoController implements Serializable {

    private DataEvento current;
    private DataModel items = null;
    @EJB
    private jpa.DataEventoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Chamada> chamada;
    private List<ChamadaEvento> chamadaPalestra;
    private List<DataEvento> paletras;

    public DataEventoController() {
        this.chamada = null;
    }

    public DataEvento getSelected() {
        if (current == null) {
            current = new DataEvento();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DataEventoFacade getFacade() {
        return ejbFacade;
    }

    public Date teste() {
        List<DataEvento> list = ejbFacade.retornaDataEvento();
        return list.get(0).getData();
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
        current = (DataEvento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new DataEvento();
        selectedItemIndex = -1;
        return "Create_1";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("DataEventoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (DataEvento) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("DataEventoUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (DataEvento) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("DataEventoDeleted"));
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

    public DataEvento getDataEvento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     * @return the chamada
     */
    public List<Chamada> getChamada() {
        return chamada;
    }

    /**
     * @param chamada the chamada to set
     */
    public void setChamada(List<Chamada> chamada) {
        this.chamada = chamada;
    }

    public List<DataEvento> getPalestras() {
        return ejbFacade.carregaPaletras();
    }

    public void setPalestras(List<DataEvento> palestras) {
        this.paletras = palestras;
    }

    public DataEvento uniqueDataEvento(int id) {
        return ejbFacade.uniqueDataEvento(id);
    }

    public void carregaChamada(ValueChangeEvent event) {
        Evento e = (Evento) event.getNewValue();
        current = ejbFacade.uniqueDataEvento(e.getIdevento());
        chamada = ejbFacade.carregaChamada(current);
    }

    public void onCellEdit(CellEditEvent event) {
        int wor = event.getRowIndex();
        int value = (int) event.getNewValue();
        System.err.println("Linha: " + wor + " Value: " + value);
    }

    public void salvar() {
        System.err.println(chamada);
        for (Chamada item : chamada) {
            System.err.println(item.getIdaluno().getNome());
            System.err.println(item.getFaltas());
        }
    }

    /**
     * @return the chamadaPalestra
     */
    public List<ChamadaEvento> getChamadaPalestra() {
        return chamadaPalestra;
    }

    /**
     * @param chamadaPalestra the chamadaPalestra to set
     */
    public void setChamadaPalestra(List<ChamadaEvento> chamadaPalestra) {
        this.chamadaPalestra = chamadaPalestra;
    }

    @FacesConverter(forClass = DataEvento.class)
    public static class DataEventoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DataEventoController controller = (DataEventoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dataEventoController");
            return controller.getDataEvento(getKey(value));
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
            if (object instanceof DataEvento) {
                DataEvento o = (DataEvento) object;
                return getStringKey(o.getIddataEvento());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DataEvento.class.getName());
            }
        }

    }

}
