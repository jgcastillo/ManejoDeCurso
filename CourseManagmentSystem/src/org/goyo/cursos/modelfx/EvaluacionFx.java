package org.goyo.cursos.modelfx;

import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EvaluacionFx {
    
    public static final int QUIZ = 1;
    public static final int PARCIAL = 2;
    public static final int FINAL = 3;
    public static final int PROYECTO = 4;
    public static final int REPARACION = 5;
    public static final int TAREA = 6;
    
    public static final int PLANIFICADO = 0;
    public static final int REALIZADO = 1;
    public static final int EVALUADO = 2;
    
    private final ObjectProperty<Integer> id;
    private final StringProperty nombre;
    private final IntegerProperty tipo;
    private final ObjectProperty<LocalDate> fecha;
    private final IntegerProperty nota;
    private final ObjectProperty<Double> peso;
    private final StringProperty status;
    private final ObjectProperty<CursoFx> cursoFx;

    public EvaluacionFx() {
        this.id = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.tipo = new SimpleIntegerProperty();
        this.fecha = new SimpleObjectProperty<>();
        this.nota = new SimpleIntegerProperty();
        this.peso = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
        this.cursoFx = new SimpleObjectProperty<>();
    }

    public Integer getId() {
        return this.id.get();
    }
    
    public void setId(Integer id){
        this.id.set(id);
    }

    public ObjectProperty<Integer> idProperty(){
        return this.id;
    }
    
    public String getNombre() {
        return this.nombre.get();
    }
    
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty(){
        return this.nombre;
    }
    
    public Integer getTipo() {
        return this.tipo.get();
    }
    
    public void setTipo(Integer tipo){
        this.tipo.set(tipo);
    }

    public IntegerProperty tipoProperty(){
        return this.tipo;
    }
    
    public LocalDate getFecha() {
        return this.fecha.get();
    }
    
    public void setFecha(LocalDate fecha){
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty(){
        return this.fecha;
    }
    
    public Integer getNota() {
        return this.nota.get();
    }
    
    public void setNota(Integer nota){
        this.nota.set(nota);
    }

    public IntegerProperty notaProperty(){
        return this.nota;
    }
    
    public Double getPeso() {
        return this.peso.get();
    }
    
    public void setPeso(Double peso){
        this.peso.set(peso);
    }
    
    public ObjectProperty<Double> pesoProperty(){
        return this.peso;
    }
    
    public CursoFx getCursoFx() {
        return this.cursoFx.get();
    }

    public void setCursoFx(CursoFx cursoFx) {
        this.cursoFx.set(cursoFx);
    }

    public ObjectProperty<CursoFx> cursoFxProperty() {
        return this.cursoFx;
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
}
