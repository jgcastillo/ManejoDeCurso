package org.goyo.cursos.modelfx;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AsistenciaFx {
    private final ObjectProperty<Integer> id;
    private final ObjectProperty<LocalDate> fecha;
    private final BooleanProperty asistencia;
    private final ObjectProperty<Integer> ciEstudiante;
    private final StringProperty apellidoEstudiante;
    private final StringProperty nombreEstudiante;
    
    public static final int ASISTIO = 1;
    public static final int NOASISTIO = 0;
    
    public AsistenciaFx(){
        this.id = new SimpleObjectProperty<>();
        this.fecha = new SimpleObjectProperty<>();
        this.asistencia = new SimpleBooleanProperty();
        this.ciEstudiante = new SimpleObjectProperty<>();
        this.apellidoEstudiante = new SimpleStringProperty();
        this.nombreEstudiante = new SimpleStringProperty();
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

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }
    
    public ObjectProperty<LocalDate> fechaProperty() {
        return this.fecha;
    }

    public Boolean getAsistencia() {
        return asistencia.get();
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia.set(asistencia);
    }
    
    public BooleanProperty asistenciaProperty() {
        return this.asistencia;
    }
    
    public Integer getCiEstudiante(){
        return this.ciEstudiante.get();
    }
    
    public void setCiEstudianteFx(Integer ciEstudiante){
        this.ciEstudiante.set(ciEstudiante);
    }
    
    public ObjectProperty<Integer> ciEstudianteFxProperty(){
        return this.ciEstudiante;
    }
    
    public String getNombreEstudiante(){
        return this.nombreEstudiante.get();
    }
    
    public void setNombreEstudiante(String nombreEstudiante){
        this.nombreEstudiante.set(nombreEstudiante);
    }
    
    public StringProperty nombreEstudianteProperty(){
        return this.nombreEstudiante;
    }
    
    public String getApellidoEstudiante() {
        return this.apellidoEstudiante.get();
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante.set(apellidoEstudiante);
    }

    public StringProperty apellidoEstudianteProperty() {
        return this.apellidoEstudiante;
    }
}
