package jsf;

import entities.Chamada;
import entities.DataEvento;
import entities.Evento;
import entities.Instrutor;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.ChamadaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import jpa.DataEventoFacade;
import jpa.EventoFacade;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "chamadaController")
@ViewScoped
public class ChamadaController implements Serializable {

    @EJB
    private EventoFacade eventoFacade;

    @EJB
    private DataEventoFacade dataEventoFacade;

    private Chamada current;
    private DataModel items = null;
    @EJB
    private jpa.ChamadaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Evento> eventosInstrutor = new ArrayList<>();
    private List<Chamada> chamada = new ArrayList<>();
    private Instrutor currentIns;
    private jpa.InstrutorFacade ejbFacadeIns;

    public ChamadaController() {

    }
    
    @PostConstruct
    public void init(){
        eventosInstrutor = (List<Evento>) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("lista");
    }

    public Chamada getSelected() {
        if (current == null) {
            current = new Chamada();
            current.setIddataEvento(new DataEvento());
            current.getIddataEvento().setIdevento(new Evento());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ChamadaFacade getFacade() {
        return ejbFacade;
    }

    public String autenticar() throws Exception {
        Instrutor i;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            i = ejbFacade.findByCPF(currentIns.getCpf()).get(0);
            if ((i.getCpf() == null ? currentIns.getCpf() != null : !i.getCpf().equals(currentIns.getCpf()))
                    || (i.getSenha() == null ? currentIns.getSenha() != null : !i.getSenha().equals(currentIns.getSenha()))) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario ou Senha incorretos"));
                return "Error.xhtml";
            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario ou Senha incorretos"));
            System.err.println("Passou catch");
            return "Error.xhtml";
        }

        currentIns = i;
        eventosInstrutor = eventoFacade.findbyInstrutor(i);
        context.getExternalContext().getFlash().put("lista", eventosInstrutor);
        if (i.getAdministrador()) {
            return "/chamada/ChamadaEvento.xhtml";
        } else {
            return "/chamada/Create_1.xhtml";
        }

    }

    public String logout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.invalidate();

        return "index.xhtml?faces-redirect=true";

    }

    public Integer getIdByCPF(String CPF) {
        return ejbFacade.findByCPF(CPF).get(0).getIdinstrutor();
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(100) {

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
        current = (Chamada) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new Chamada();
        recreateModel();
        selectedItemIndex = -1;
        return "Create_1";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Chamada) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String Edit() {
        current = (Chamada) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return null;
    }

    public int index() {
        return pagination.getPageFirstItem() + getItems().getRowIndex();
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Chamada) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("ChamadaDeleted"));
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

    public Chamada getChamada(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public void onCellEdit(CellEditEvent event) {
        int newValue = (int) event.getNewValue();
        current = chamada.get(event.getRowIndex());
        current.setFaltas(newValue);
        getFacade().edit(current);
    }

    public void carregaChamada(ValueChangeEvent event) {
        Evento e = (Evento) event.getNewValue();
        setChamada(dataEventoFacade.carregaChamada(dataEventoFacade.uniqueDataEvento(e.getIdevento())));
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

    public String salvar() {
        for (Chamada item : chamada) {
            current = ejbFacade.find(item.getIdchamada());
            if (!current.equals(item)) {
                ejbFacade.edit(item);
            }
        }
        JsfUtil.addSuccessMessage("Chamada realizada com sucesso!");
        return "login.xhtml";
    }

    /**
     * @return the eventosInstrutor
     */
    public List<Evento> getEventosInstrutor() {
        return eventosInstrutor;
    }

    /**
     * @param eventosInstrutor the eventosInstrutor to set
     */
    public void setEventosInstrutor(List<Evento> eventosInstrutor) {
        this.eventosInstrutor = eventosInstrutor;
    }

    public Instrutor getInstrutor(java.lang.Integer id) {
        return ejbFacadeIns.find(id);
    }

    public Instrutor getInstrutor() {
        if (currentIns == null) {
            currentIns = new Instrutor();
        }
        return currentIns;
    }

    @FacesConverter(forClass = Chamada.class)
    public static class ChamadaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ChamadaController controller = (ChamadaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "chamadaController");
            return controller.getChamada(getKey(value));
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
            if (object instanceof Chamada) {
                Chamada o = (Chamada) object;
                return getStringKey(o.getIdchamada());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Chamada.class.getName());
            }
        }

    }
}
