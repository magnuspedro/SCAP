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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author matheus
 */
@Entity
@Table(name = "evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findByIdevento", query = "SELECT e FROM Evento e WHERE e.idevento = :idevento")
    , @NamedQuery(name = "Evento.findByNome", query = "SELECT e FROM Evento e WHERE e.nome = :nome")
    , @NamedQuery(name = "Evento.findByTipo", query = "SELECT e FROM Evento e WHERE e.tipo = :tipo")
    , @NamedQuery(name = "Evento.findByVagasTotais", query = "SELECT e FROM Evento e WHERE e.vagasTotais = :vagasTotais")
    , @NamedQuery(name = "Evento.findByHoraInicio", query = "SELECT e FROM Evento e WHERE e.horaInicio = :horaInicio")
    , @NamedQuery(name = "Evento.findByHoraTermino", query = "SELECT e FROM Evento e WHERE e.horaTermino = :horaTermino")
    , @NamedQuery(name = "Evento.findByInstrutor", query = "SELECT e FROM Evento e, EventosInstrutores ei, Instrutor i WHERE e = ei.idevento AND ei.idinstrutor = i AND i.idinstrutor = :idinstrutor")})
public class Evento implements Serializable {

    @JoinColumn(name = "idlocal", referencedColumnName = "idlocal")
    @ManyToOne(optional = false)
    private Local idlocal;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idevento")
    private Integer idevento;
    @Size(max = 200)
    @Column(name = "nome")
    private String nome;
    @Size(max = 60)
    @Column(name = "tipo")
    private String tipo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "vagas_totais")
    private Integer vagasTotais;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "hora_termino")
    @Temporal(TemporalType.TIME)
    private Date horaTermino;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idevento")
    private Collection<EventosInstrutores> eventosInstrutoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idevento")
    private Collection<Matricula> matriculaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idevento")
    private Collection<DataEvento> dataEventoCollection;

    public Evento() {
    }

    public Evento(Integer idevento) {
        this.idevento = idevento;
    }

    public Integer getIdevento() {
        return idevento;
    }

    public void setIdevento(Integer idevento) {
        this.idevento = idevento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getVagasTotais() {
        return vagasTotais;
    }

    public void setVagasTotais(Integer vagasTotais) {
        this.vagasTotais = vagasTotais;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    @XmlTransient
    public Collection<EventosInstrutores> getEventosInstrutoresCollection() {
        return eventosInstrutoresCollection;
    }

    public void setEventosInstrutoresCollection(Collection<EventosInstrutores> eventosInstrutoresCollection) {
        this.eventosInstrutoresCollection = eventosInstrutoresCollection;
    }

    @XmlTransient
    public Collection<Matricula> getMatriculaCollection() {
        return matriculaCollection;
    }

    public void setMatriculaCollection(Collection<Matricula> matriculaCollection) {
        this.matriculaCollection = matriculaCollection;
    }

    @XmlTransient
    public Collection<DataEvento> getDataEventoCollection() {
        return dataEventoCollection;
    }

    public void setDataEventoCollection(Collection<DataEvento> dataEventoCollection) {
        this.dataEventoCollection = dataEventoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idevento != null ? idevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idevento == null && other.idevento != null) || (this.idevento != null && !this.idevento.equals(other.idevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

    public Local getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(Local idlocal) {
        this.idlocal = idlocal;
    }

}
