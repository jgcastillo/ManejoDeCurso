package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IntervencionFx {
    private final ObjectProperty<Integer> id;
    private final ObjectProperty<Integer> cantidad;
    private final ObjectProperty<CursoFx> cursoFx;
    private final ObjectProperty<EstudianteFx> estudianteFx;
    private final ObjectProperty<Integer> ciEstudiante;
    private final StringProperty apellidoEstudiante;
    private final StringProperty nombreEstudiante;

    public IntervencionFx() {
        this.id = new SimpleObjectProperty<>();
        this.cantidad = new SimpleObjectProperty<>();
        this.cursoFx = new SimpleObjectProperty<>();
        this.estudianteFx = new SimpleObjectProperty<>();
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
    
    public Integer getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad.set(cantidad);
    }

    public ObjectProperty<Integer> cantidadProperty() {
        return this.cantidad;
    }

    public CursoFx getCursoFx() {
        return cursoFx.get();
    }
    
    public void setCursoFx(CursoFx cursoFx){
        this.cursoFx.set(cursoFx);
    }
    
    public ObjectProperty<CursoFx> cursoFxProperty() {
        return cursoFx;
    }

    public EstudianteFx getEstudianteFx() {
        return estudianteFx.get();
    }
    
    public void setEstudianteFx(EstudianteFx estudianteFx) {
        this.estudianteFx.set(estudianteFx);
    }
    
    public ObjectProperty<EstudianteFx> estudianteFxProperty() {
        return estudianteFx;
    }
    
    public Integer getCiEstudiante() {
        return this.ciEstudiante.get();
    }

    public void setCiEstudianteFx(Integer ciEstudiante) {
        this.ciEstudiante.set(ciEstudiante);
    }

    public ObjectProperty<Integer> ciEstudianteFxProperty() {
        return this.ciEstudiante;
    }

    public String getNombreEstudiante() {
        return this.nombreEstudiante.get();
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante.set(nombreEstudiante);
    }

    public StringProperty nombreEstudianteProperty() {
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

    @Override
    public String toString() {
        return apellidoEstudiante.get() + ", " + nombreEstudiante.get() ;
    }
    
    
}
