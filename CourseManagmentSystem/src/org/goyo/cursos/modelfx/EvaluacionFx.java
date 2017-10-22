package org.goyo.cursos.modelfx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
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
    private final ObjectProperty<TipoEvaluacionFx> tipoEvaluacionFx;
    private final ObjectProperty<LocalDate> fecha;
    private final IntegerProperty nota;
    private final ObjectProperty<Double> peso;
    private final ObjectProperty<Double> pesoAcumulado;
    private final StringProperty status;
    private final ObjectProperty<CursoFx> cursoFx;

    public EvaluacionFx() {
        this.id = new SimpleObjectProperty<>();
        this.nombre = new SimpleStringProperty();
        this.tipoEvaluacionFx = new SimpleObjectProperty();
        this.fecha = new SimpleObjectProperty<>();
        this.nota = new SimpleIntegerProperty();
        this.peso = new SimpleObjectProperty<>();
        this.pesoAcumulado = new SimpleObjectProperty<>();
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
    
    public TipoEvaluacionFx getTipoEvaluacionFx() {
        return this.tipoEvaluacionFx.get();
    }
    
    public void setTipoEvaluacionFx(TipoEvaluacionFx tipo){
        this.tipoEvaluacionFx.set(tipo);
    }

    public ObjectProperty<TipoEvaluacionFx> tipoEvaluacionFxProperty(){
        return this.tipoEvaluacionFx;
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
    
    public Double getPesoAcmulado() {
        return this.pesoAcumulado.get();
    }
    
    public void setPesoAcumulado(Double pesoAcumulado){
        this.pesoAcumulado.set(pesoAcumulado);
    }
    
    public ObjectProperty<Double> pesoAcumuladoProperty(){
        return this.pesoAcumulado;
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(nombre.get()).append( " - ");
        sb.append(fecha.get().format(formatter));
        return sb.toString();
    }
    
    
}
