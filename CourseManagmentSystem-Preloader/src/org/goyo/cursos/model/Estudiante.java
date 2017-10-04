/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goyo.cursos.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findByCi", query = "SELECT e FROM Estudiante e WHERE e.ci = :ci")
    , @NamedQuery(name = "Estudiante.findByNombre", query = "SELECT e FROM Estudiante e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Estudiante.findByApellido", query = "SELECT e FROM Estudiante e WHERE e.apellido = :apellido")
    , @NamedQuery(name = "Estudiante.findByCorreo", query = "SELECT e FROM Estudiante e WHERE e.correo = :correo")
    , @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "Estudiante.findByStatus", query = "SELECT e FROM Estudiante e WHERE e.status = :status")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ci")
    private Integer ci;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Curso cursoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteCi")
    private Collection<Asistencia> asistenciaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteCi")
    private Collection<Intervencion> intervencionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudianteCi")
    private Collection<Nota> notaCollection;

    public Estudiante() {
    }

    public Estudiante(Integer ci) {
        this.ci = ci;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    @XmlTransient
    public Collection<Asistencia> getAsistenciaCollection() {
        return asistenciaCollection;
    }

    public void setAsistenciaCollection(Collection<Asistencia> asistenciaCollection) {
        this.asistenciaCollection = asistenciaCollection;
    }

    @XmlTransient
    public Collection<Intervencion> getIntervencionCollection() {
        return intervencionCollection;
    }

    public void setIntervencionCollection(Collection<Intervencion> intervencionCollection) {
        this.intervencionCollection = intervencionCollection;
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
        hash += (ci != null ? ci.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.ci == null && other.ci != null) || (this.ci != null && !this.ci.equals(other.ci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.goyo.cursos.model.Estudiante[ ci=" + ci + " ]";
    }
    
}
