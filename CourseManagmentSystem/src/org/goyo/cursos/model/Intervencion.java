/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "intervencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intervencion.findAll", query = "SELECT i FROM Intervencion i")
    , @NamedQuery(name = "Intervencion.findById", query = "SELECT i FROM Intervencion i WHERE i.id = :id")
    , @NamedQuery(name = "Intervencion.findByCantidad", query = "SELECT i FROM Intervencion i WHERE i.cantidad = :cantidad")})
public class Intervencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Curso cursoId;
    @JoinColumn(name = "estudiante_ci", referencedColumnName = "ci")
    @ManyToOne(optional = false)
    private Estudiante estudianteCi;

    public Intervencion() {
    }

    public Intervencion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Estudiante getEstudianteCi() {
        return estudianteCi;
    }

    public void setEstudianteCi(Estudiante estudianteCi) {
        this.estudianteCi = estudianteCi;
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
        if (!(object instanceof Intervencion)) {
            return false;
        }
        Intervencion other = (Intervencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.goyo.cursos.model.Intervencion[ id=" + id + " ]";
    }
    
}
