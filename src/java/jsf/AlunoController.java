package jsf;

import entities.Aluno;
import entities.DataEvento;
import entities.Matricula;
import jsf.util.JsfUtil;
import jsf.util.PaginationHelper;
import jpa.AlunoFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import jpa.MatriculaFacade;
import org.primefaces.component.export.PDFOptions;

@ManagedBean(name = "alunoController")
@SessionScoped
public class AlunoController implements Serializable {

    @EJB
    private MatriculaFacade matriculaFacade;

    private Aluno current;
    private DataModel items = null;
    @EJB
    private jpa.AlunoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<DataEvento> eventos;
    private List<Aluno> alunos;
    private List<Aluno> alunosOrdenado;
    private PDFOptions pdfOp;

    public AlunoController() {
    }

    public Aluno getSelected() {
        if (current == null) {
            current = new Aluno();
            eventos = null;
            selectedItemIndex = -1;
        }
        return current;
    }

    private AlunoFacade getFacade() {
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
        current = (Aluno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String prepareCreate() {
        current = new Aluno();
        selectedItemIndex = -1;
        eventos = new ArrayList<>();
        recreateModel();
        return "Sair.xhtml";
    }

    public String fim() {

        return "Sair.xhtml";
    }

    public String create() {
        try {
            List<Matricula> lm = new ArrayList(); //Lista a Salvar
            List<Matricula> excluir = new ArrayList<>();//Lista a excluir
            Aluno a = ejbFacade.findByCPF(current.getCpf()); //Busca por CPF
            List<Matricula> atual = ejbFacade.findByAluno(a); //List de matricula do Aluno no banco
            boolean flag = false;
            if (a != null) { //Aluno existe no banco
                current.setIdaluno(a.getIdaluno());
                if (!eventos.isEmpty()) {//Se existe algum selecionado
                    //Comparar se já existe ou se devemos adicionar
                    for (DataEvento evento : eventos) {
                        for (Matricula matricula : atual) {
                            flag = false;
                            if (evento.getIdevento().getIdevento().equals(matricula.getIdevento().getIdevento())) {
                                //Caso já existir no banco
                                lm.add(matricula);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            Matricula m = new Matricula();
                            m.setIdaluno(current);
                            m.setIdevento(evento.getIdevento());
                            m.setPago(Boolean.FALSE);
                            lm.add(m);
                        }
                    }
                    for (Matricula matricula : atual) {
                        flag = false;
                        for (DataEvento evento : eventos) {
                            if (matricula.getIdevento().getIdevento().equals(evento.getIdevento().getIdevento())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            if (!matricula.getPago()) {
                                excluir.add(matricula);
                            }
                        }

                    }
                } else {
                    excluir.addAll(atual);
                }
            } else {
                if (!eventos.isEmpty()) {
                    for (DataEvento item : eventos) {
                        Matricula m = new Matricula();
                        m.setIdaluno(current);
                        m.setIdevento(item.getIdevento());
                        m.setPago(Boolean.FALSE);
                        lm.add(m);
                    }
                }
            }
            excluir.forEach(matriculaFacade::remove);
            current.setMatriculaCollection(lm);
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Cadastro de Participante e Minicursos realizado com sucesso.");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }

    }

    public String prepareEdit() {
        current = (Aluno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create_1";
    }

    public String impressao() {

        return "impressao.xhtml";

    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("AlunoUpdated"));
            return "Create_1";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Aluno) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("resources/Bundle").getString("AlunoDeleted"));
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

    public Aluno getAluno(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public Aluno getRA() {
        current = ejbFacade.findRA(current.getRa());
        return current;

    }

    /**
     * @return the alunosOrdenado
     */
    public List<Aluno> getAlunosOrdenado() {
        alunosOrdenado = ejbFacade.listAllOrdenado();
        return alunosOrdenado;
    }

    /**
     * @param alunosOrdenado the alunosOrdenado to set
     */
    public void setAlunosOrdenado(List<Aluno> alunosOrdenado) {
        this.alunosOrdenado = alunosOrdenado;
    }

    @FacesConverter(forClass = Aluno.class)
    public static class AlunoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlunoController controller = (AlunoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "alunoController");
            return controller.getAluno(getKey(value));
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
            if (object instanceof Aluno) {
                Aluno o = (Aluno) object;
                return getStringKey(o.getIdaluno());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Aluno.class.getName());
            }
        }

    }

    /**
     * @return the eventos
     */
    public List<DataEvento> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<DataEvento> eventos) {
        this.eventos = eventos;
    }

    public void carregaMatricula() {
        System.err.println(ejbFacade.findByAlunoPago(current));
        //current.getMatriculaCollection().addAll(ejbFacade.findByAluno(current));
    }

    /**
     * @return the alunos
     */
    public List<Aluno> getAlunos() {
        return alunos;
    }

    /**
     * @param alunos the alunos to set
     */
    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Aluno> completeNome(String query) {
        List<Aluno> list = new ArrayList<>();
        list.add(ejbFacade.findRA(query));
        return list;
    }
}
