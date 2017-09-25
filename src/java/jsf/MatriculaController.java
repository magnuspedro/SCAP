package jsf;

import entities.Aluno;
import entities.Chamada;
import entities.ChamadaEvento;
import entities.Evento;
import entities.Matricula;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.MatriculaFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.ChamadaEventoFacade;
import jpa.ChamadaFacade;
import jpa.DataEventoFacade;
import jpa.EventoFacade;

@ManagedBean(name = "matriculaController")
@SessionScoped
public class MatriculaController implements Serializable {

    @EJB
    private ChamadaEventoFacade chamadaEventoFacade;

    @EJB
    private EventoFacade eventoFacade;

    @EJB
    private DataEventoFacade dataEventoFacade;

    @EJB
    private ChamadaFacade chamadaFacade;

    private Matricula current;
    private DataModel items = null;
    @EJB
    private jpa.MatriculaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Matricula> matricula = null;
    private List<Matricula> pago = null;

    public MatriculaController() {
    }

    public Matricula getSelected() {
        if (current == null) {
            current = new Matricula();
            selectedItemIndex = -1;
        }
        return current;
    }

    private MatriculaFacade getFacade() {
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
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new Matricula();
        selectedItemIndex = -1;
        recreateModel();
        return "Create_1";
    }

    public String create() {
        try {
            getFacade().create(current);
            if (current.getPago()) {
                if (current.getIdevento().getTipo().equalsIgnoreCase("Minicurso")) {
                    Chamada c = new Chamada();
                    c.setFaltas(0);
                    c.setIdaluno(current.getIdaluno());
                    System.out.println(current.getIdevento().getIdevento());

                    //c.setIddataEvento(de);
                    //chamadaFacade.create(c);
                }
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("MatriculaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("MatriculaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        prepareCreate();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("MatriculaDeleted"));
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

    public Matricula getMatricula(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Matricula.class)
    public static class MatriculaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatriculaController controller = (MatriculaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matriculaController");
            return controller.getMatricula(getKey(value));
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
            if (object instanceof Matricula) {
                Matricula o = (Matricula) object;
                return getStringKey(o.getIdmatricula());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Matricula.class.getName());
            }
        }

    }

    public void carregaMatricula(ValueChangeEvent event) {
        current.setIdaluno((Aluno) event.getNewValue());
        matricula = ejbFacade.findByAluno(current.getIdaluno());
        List<Matricula> realoficial = new ArrayList<>();
        for (Matricula item : matricula) {
            int pos = ejbFacade.countPosicao(item).intValue();
            if (!(pos >= item.getIdevento().getVagasTotais())) {
                realoficial.add(item);
            }
        }
        matricula = realoficial;
    }

    /**
     * @return the chamada
     */
    public List<Matricula> getMatricula() {
        return matricula;
    }

    /**
     * @param matricula
     */
    public void setChamada(List<Matricula> matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the pago
     */
    public List<Matricula> getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(List<Matricula> pago) {
        this.pago = pago;
    }

    public String pagando() {
        ArrayList<Chamada> ch = new ArrayList<>();

        for (Matricula item : pago) {
            item.setPago(Boolean.TRUE);
            ejbFacade.edit(item);
           
                Chamada c = new Chamada();
                c.setIdaluno(item.getIdaluno());
                c.setFaltas(0);
                c.setIddataEvento(dataEventoFacade.uniqueDataEvento(item.getIdevento().getIdevento()));
                ch.add(c);
            }

        for (Chamada chamada : ch) {
            chamadaFacade.create(chamada);
        }

        matricula = null;
        pago = null;
        JsfUtil.addSuccessMessage("Matricula atualizada com sucesso");
        return prepareCreate();
    }
}
