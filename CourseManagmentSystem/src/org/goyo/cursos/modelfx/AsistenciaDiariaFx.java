package org.goyo.cursos.modelfx;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AsistenciaDiariaFx {

    private final ObjectProperty<LocalDate> fecha;
    private final ObjectProperty<Integer> cantidad;
    
    public AsistenciaDiariaFx() {
        this.fecha = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty(){
        return this.fecha;
    }
    
    public Integer getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad.set(cantidad);
    }
    
    public ObjectProperty<Integer> cantidadProperty(){
        return this.cantidad;
    }
}
