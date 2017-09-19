/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author matheus
 */
@Entity
@Table(name = "instrutor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Instrutor.findAll", query = "SELECT i FROM Instrutor i")
    , @NamedQuery(name = "Instrutor.findByIdinstrutor", query = "SELECT i FROM Instrutor i WHERE i.idinstrutor = :idinstrutor")
    , @NamedQuery(name = "Instrutor.findByNome", query = "SELECT i FROM Instrutor i WHERE i.nome = :nome")
    , @NamedQuery(name = "Instrutor.findByTipo", query = "SELECT i FROM Instrutor i WHERE i.tipo = :tipo")
    , @NamedQuery(name = "Instrutor.findByRg", query = "SELECT i FROM Instrutor i WHERE i.rg = :rg")
    , @NamedQuery(name = "Instrutor.findByOrgaoExpeditor", query = "SELECT i FROM Instrutor i WHERE i.orgaoExpeditor = :orgaoExpeditor")
    , @NamedQuery(name = "Instrutor.findByCpf", query = "SELECT i FROM Instrutor i WHERE i.cpf = :cpf")
    , @NamedQuery(name = "Instrutor.findBySenha", query = "SELECT i FROM Instrutor i WHERE i.senha = :senha")
    , @NamedQuery(name = "Instrutor.findByAdministrador", query = "SELECT i FROM Instrutor i WHERE i.administrador = :administrador")})
public class Instrutor implements Serializable {

    @Pattern(regexp= "[a-zA-Z0-9_-.]*[@][a-zA-Z0-9_-.]")
    @Size(max = 70)
    @Column(name = "email")
    private String email;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinstrutor")
    private Integer idinstrutor;
    @Size(max = 100)
    @Column(name = "nome")
    @Pattern(regexp= "[a-zA-Z\\s^~´`]*")
    private String nome;
    @Column(name = "tipo")
    private Boolean tipo;
    @Size(max = 45)
    @Column(name = "RG")
    @Pattern(regexp= "[a-zA-Z0-9]*")
    private String rg;
    @Size(max = 20)
    @Column(name = "orgao_expeditor")
    private String orgaoExpeditor;
    @Size(max = 15)
    @Column(name = "CPF")
    private String cpf;
    @Size(max = 255)
    @Column(name = "senha")
    private String senha;
    @Column(name = "administrador")
    private Boolean administrador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idinstrutor")
    private Collection<EventosInstrutores> eventosInstrutoresCollection;

    public Instrutor() {
    }

    public Instrutor(Integer idinstrutor) {
        this.idinstrutor = idinstrutor;
    }

    public Integer getIdinstrutor() {
        return idinstrutor;
    }

    public void setIdinstrutor(Integer idinstrutor) {
        this.idinstrutor = idinstrutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoExpeditor() {
        return orgaoExpeditor;
    }

    public void setOrgaoExpeditor(String orgaoExpeditor) {
        this.orgaoExpeditor = orgaoExpeditor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    @XmlTransient
    public Collection<EventosInstrutores> getEventosInstrutoresCollection() {
        return eventosInstrutoresCollection;
    }

    public void setEventosInstrutoresCollection(Collection<EventosInstrutores> eventosInstrutoresCollection) {
        this.eventosInstrutoresCollection = eventosInstrutoresCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinstrutor != null ? idinstrutor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Instrutor)) {
            return false;
        }
        Instrutor other = (Instrutor) object;
        if ((this.idinstrutor == null && other.idinstrutor != null) || (this.idinstrutor != null && !this.idinstrutor.equals(other.idinstrutor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
