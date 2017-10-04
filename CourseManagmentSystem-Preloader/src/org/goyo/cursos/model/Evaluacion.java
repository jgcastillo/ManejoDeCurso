/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.model;

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
 * @author jgcastillo
 */
@Entity
@Table(name = "evaluacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")
    , @NamedQuery(name = "Evaluacion.findById", query = "SELECT e FROM Evaluacion e WHERE e.id = :id")
    , @NamedQuery(name = "Evaluacion.findByNombre", query = "SELECT e FROM Evaluacion e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Evaluacion.findByFecha", query = "SELECT e FROM Evaluacion e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Evaluacion.findByPeso", query = "SELECT e FROM Evaluacion e WHERE e.peso = :peso")
    , @NamedQuery(name = "Evaluacion.findByStatus", query = "SELECT e FROM Evaluacion e WHERE e.status = :status")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso")
    private Double peso;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Curso cursoId;
    @JoinColumn(name = "tipo_evaluacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoEvaluacion tipoEvaluacionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacionId")
    private Collection<Nota> notaCollection;

    public Evaluacion() {
    }

    public Evaluacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public TipoEvaluacion getTipoEvaluacionId() {
        return tipoEvaluacionId;
    }

    public void setTipoEvaluacionId(TipoEvaluacion tipoEvaluacionId) {
        this.tipoEvaluacionId = tipoEvaluacionId;
    }

    @XmlTransient
    public Collection<Nota> getNotaCollection() {
        return notaCollection;
    }

    public void setNotaCollection(Collection<Nota> notaCollection) {
        this.notaCollection = notaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.goyo.cursos.model.Evaluacion[ id=" + id + " ]";
    }
    
}
