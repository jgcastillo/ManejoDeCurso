package org.goyo.cursos.modelfx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CargaNotaFx {
    private final ObjectProperty<Integer> ci;
    private final ObjectProperty<EstudianteFx> alumno;
    private final ObjectProperty<Double> nota;

    public CargaNotaFx() {
        this.ci = new SimpleObjectProperty<>();
        this.alumno = new SimpleObjectProperty<>();
        this.nota = new SimpleObjectProperty<>();
    }

    public Integer getCi() {
        return ci.get();
    }

    public void setCi(Integer ci) {
        this.ci.set(ci);
    }
    
    public ObjectProperty<Integer> ciProperty(){
        return ci;
    }

    public EstudianteFx getAlumno() {
        return alumno.get();
    }

    public void setAlumno(EstudianteFx alumno) {
        this.alumno.set(alumno);
    }
    
    public ObjectProperty<EstudianteFx> alumnoProperty() {
        return alumno;
    }

    public Double getNota() {
        return nota.get();
    }

    public void setNota(Double nota) {
        this.nota.set(nota);
    }
    
    public ObjectProperty<Double> notaProperty() {
        return nota;
    }
}
