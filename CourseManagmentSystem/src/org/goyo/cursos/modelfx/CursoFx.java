package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CursoFx {
    private final ObjectProperty<Integer> id;
    private final StringProperty nombre;
    private final StringProperty nrc;
    private final StringProperty status;
    private final ObjectProperty<PeriodoFx> periodoFx;
    
    public static final int INACTIVO = 0;
    public static final int ACTIVO = 1;
    public static final String ACTIVOSTR = "Activo";
    public static final String INACTIVOSTR = "Inactivo";

    public CursoFx() {
        this.id = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.nrc = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.periodoFx = new SimpleObjectProperty<>();
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
    
    public String getNrc() {
        return this.nrc.get();
    }

    public void setNrc(String nrc) {
        this.nrc.set(nrc);
    }

    public StringProperty nrcProperty() {
        return this.nrc;
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
    
    public PeriodoFx getPeriodoFx(){
        return this.periodoFx.get();
    }
    
    public void setPeriodoFx(PeriodoFx periodoFx){
        this.periodoFx.set(periodoFx);
    }
    
    public ObjectProperty<PeriodoFx> periodoFxProperty(){
        return this.periodoFx;
    }
    
    @Override
    public String toString(){
        return this.nombre.get();
    }
}
