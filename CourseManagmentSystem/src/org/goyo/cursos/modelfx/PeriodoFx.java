package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PeriodoFx {
    private final ObjectProperty<Integer> id;
    private final StringProperty nombre;
    private final StringProperty status;
    
    public static final int ACTIVO = 1;
    public static final String ACTIVOSTR = "Activo";
    public static final int INACTIVO = 0;
    public static final String INACTIVOSTR = "Inactivo";

    public PeriodoFx() {
        this.id = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public ObjectProperty<Integer> idProperty() {
        return this.id;
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
    
    public String getStatus(){
        return this.status.get();
    }
    
    public void setStatus(String status){
        this.status.set(status);
    }
    
    public StringProperty statusProperty(){
        return this.status;
    }

    @Override
    public String toString() {
        return nombre.get();
    }
    
}
