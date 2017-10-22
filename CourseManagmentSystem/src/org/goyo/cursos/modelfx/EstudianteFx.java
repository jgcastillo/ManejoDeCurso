package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EstudianteFx {
    private final ObjectProperty<Integer> ci;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty correo;
    private final StringProperty telefono;
    private final StringProperty status;
    private final ObjectProperty<CursoFx> cursoFx;

    public static final int RETIRADO = 0;
    public static final int ACTIVO = 1;
    public static final String ACTIVOSTR = "Activo";
    public static final String RETIRADOSTR = "Retirado";
    
    public EstudianteFx() {
        this.ci = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.apellido = new SimpleStringProperty();
        this.correo = new SimpleStringProperty();
        this.telefono = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.cursoFx = new SimpleObjectProperty<>();
    }

    public Integer getCi() {
        return ci.get();
    }

    public void setCi(Integer id) {
        this.ci.set(id);
    }

    public ObjectProperty<Integer> ciProperty() {
        return this.ci;
    }

    public String getNombre() {
        return this.nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public StringProperty apellidoProperty() {
        return this.apellido;
    }
    
    public String getCorreo() {
        return this.correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public StringProperty correoProperty() {
        return this.correo;
    }
    
    public String getTelefono() {
        return this.telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public StringProperty telefonoProperty() {
        return this.telefono;
    }
    
    public String getStatus() {
        return this.status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return this.status;
    }
    
    public ObjectProperty<CursoFx> getCursoFx(){
        return this.cursoFx;
    }
    
    public void setCursoFx(CursoFx cursoFx){
        this.cursoFx.set(cursoFx);
    }
    
    public ObjectProperty<CursoFx> cursoFxProperty(){
        return this.cursoFx;
    }

    @Override
    public String toString() {
        return apellido.get() + ", " + nombre.get() ;
    }
    
    
}
