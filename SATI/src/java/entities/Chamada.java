/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author matheus
 */
@Entity
@Table(name = "chamada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chamada.findAll", query = "SELECT c FROM Chamada c")
    , @NamedQuery(name = "Chamada.findByIdchamada", query = "SELECT c FROM Chamada c WHERE c.idchamada = :idchamada")
    , @NamedQuery(name = "Chamada.findByHora", query = "SELECT c FROM Chamada c WHERE c.hora = :hora")
    , @NamedQuery(name = "Chamada.findBySituacao", query = "SELECT c FROM Chamada c WHERE c.situacao = :situacao")})
public class Chamada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idchamada")
    private Integer idchamada;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "situacao")
    private Boolean situacao;
    @JoinColumn(name = "idaluno", referencedColumnName = "idaluno")
    @ManyToOne(optional = false)
    private Aluno idaluno;
    @JoinColumn(name = "iddata_evento", referencedColumnName = "iddata_evento")
    @ManyToOne(optional = false)
    private DataEvento iddataEvento;

    public Chamada() {
    }

    public Chamada(Integer idchamada) {
        this.idchamada = idchamada;
    }

    public Integer getIdchamada() {
        return idchamada;
    }

    public void setIdchamada(Integer idchamada) {
        this.idchamada = idchamada;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Boolean getSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Aluno getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(Aluno idaluno) {
        this.idaluno = idaluno;
    }

    public DataEvento getIddataEvento() {
        return iddataEvento;
    }

    public void setIddataEvento(DataEvento iddataEvento) {
        this.iddataEvento = iddataEvento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idchamada != null ? idchamada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chamada)) {
            return false;
        }
        Chamada other = (Chamada) object;
        if ((this.idchamada == null && other.idchamada != null) || (this.idchamada != null && !this.idchamada.equals(other.idchamada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Chamada[ idchamada=" + idchamada + " ]";
    }
    
}
