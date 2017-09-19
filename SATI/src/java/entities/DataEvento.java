/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author matheus
 */
@Entity/*SELECT e FROM Evento e INNER JOIN DataEvento de ON de.idevento = e.idevento*/
@Table(name = "data_evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataEvento.findAll", query = "SELECT d FROM DataEvento d")
    , @NamedQuery(name = "DataEvento.findByIddataEvento", query = "SELECT d FROM DataEvento d WHERE d.iddataEvento = :iddataEvento")
    , @NamedQuery(name = "DataEvento.findByData", query = "SELECT d FROM DataEvento d WHERE d.data = :data")
    , @NamedQuery(name = "DataEvento.findByAberto", query = "SELECT d FROM DataEvento d WHERE d.aberto = :aberto")
    , @NamedQuery(name = "DataEvento.findByHoraAberto", query = "SELECT d FROM DataEvento d WHERE d.horaAberto = :horaAberto")
    , @NamedQuery(name = "DataEvento.findByHoraFechamento", query = "SELECT d FROM DataEvento d WHERE d.horaFechamento = :horaFechamento")
    , @NamedQuery(name = "DataEvento.listName", query = "SELECT d FROM DataEvento d")
    , @NamedQuery(name = "DataEvento.findByEvento", query = "SELECT d FROM DataEvento d INNER JOIN Evento e ON d.idevento = e WHERE d.idevento.idevento = :idevento")
    , @NamedQuery(name = "DataEvento.listChamada", query = "SELECT c FROM Chamada c INNER JOIN DataEvento d ON c.iddataEvento = d.iddataEvento WHERE c.iddataEvento = :iddataEvento")
    ,@NamedQuery(name = "DataEvento.listPaletra", query = "SELECT d FROM DataEvento d INNER JOIN Evento e ON d.idevento = e WHERE e.tipo = :tipo")
//,@NamedQuery(name = "DataEvento.findByHoraEvento", query ="SELECT d FROM DataEvento d, Evento e, EventosInstrutores ei, Instrutor i WHERE d.idevento = e.idevento AND e.idevento = ei.idevento AND ei.idinstrutor = i.idinstrutor AND i.idinstrutor = :idinstrutor")
/*,@NamedQuery(name = "Chamada.findByIddataEvento", query ="SELECT c FROM Chamada c INNER JOIN DataEvento de ON c.iddataEvento = de.idevento WHERE de.idevento = (SELECT de.iddataEvento FROM DataEvento de INNER JOIN Evento e ON de.idevento = e.idevento WHERE e.idevento = :idevento AND de.data = :data ")*/
})
public class DataEvento implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddataEvento")
    private Collection<ChamadaEvento> chamadaEventoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddata_evento")
    private Integer iddataEvento;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "aberto")
    private Boolean aberto;
    @Column(name = "hora_aberto")
    @Temporal(TemporalType.TIME)
    private Date horaAberto;
    @Column(name = "hora_fechamento")
    @Temporal(TemporalType.TIME)
    private Date horaFechamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddataEvento")
    private Collection<Chamada> chamadaCollection;
    @JoinColumn(name = "idevento", referencedColumnName = "idevento")
    @ManyToOne(optional = false)
    private Evento idevento;

    public DataEvento() {
    }

    public DataEvento(Integer iddataEvento) {
        this.iddataEvento = iddataEvento;
    }

    public Integer getIddataEvento() {
        return iddataEvento;
    }

    public void setIddataEvento(Integer iddataEvento) {
        this.iddataEvento = iddataEvento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getAberto() {
        return aberto;
    }

    public void setAberto(Boolean aberto) {
        this.aberto = aberto;
    }

    public Date getHoraAberto() {
        return horaAberto;
    }

    public void setHoraAberto(Date horaAberto) {
        this.horaAberto = horaAberto;
    }

    public Date getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(Date horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    @XmlTransient
    public Collection<Chamada> getChamadaCollection() {
        return chamadaCollection;
    }

    public void setChamadaCollection(Collection<Chamada> chamadaCollection) {
        this.chamadaCollection = chamadaCollection;
    }

    public Evento getIdevento() {
        return idevento;
    }

    public void setIdevento(Evento idevento) {
        this.idevento = idevento;
    }

    public String getNomeEvento() {
        return this.idevento.getNome();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddataEvento != null ? iddataEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataEvento)) {
            return false;
        }
        DataEvento other = (DataEvento) object;
        return !((this.iddataEvento == null && other.iddataEvento != null) || (this.iddataEvento != null && !this.iddataEvento.equals(other.iddataEvento)));
    }

    @Override
    public String toString() {
        return idevento.getNome();
    }

    @XmlTransient
    public Collection<ChamadaEvento> getChamadaEventoCollection() {
        return chamadaEventoCollection;
    }

    public void setChamadaEventoCollection(Collection<ChamadaEvento> chamadaEventoCollection) {
        this.chamadaEventoCollection = chamadaEventoCollection;
    }

}
