package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoEvaluacionFx {

    private final ObjectProperty<Integer> id;
    private final StringProperty nombre;
    private final StringProperty acumulado;
    
    public static final int NOACUMULADO = 0;
    public static final int ACUMULADO = 1;
    public static final String NOACUMULADOSTR = "No";
    public static final String ACUMULADOSTR = "Si";
    
    public TipoEvaluacionFx(){
        this.id = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.acumulado = new SimpleStringProperty();
    }

    public Integer getId() {
        return id.get();
    }
    
    public void setId(Integer id){
        this.id.set(id);
    }
    
    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }
    
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }
    
    public String getAcumulado() {
        return acumulado.get();
    }

    public void setAcumulado(String acumulado) {
        this.acumulado.set(acumulado);
    }
    
    public StringProperty acumuladoProperty() {
        return acumulado;
    }
}
