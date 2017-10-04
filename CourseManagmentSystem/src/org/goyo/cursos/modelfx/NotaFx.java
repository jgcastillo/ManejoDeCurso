package org.goyo.cursos.modelfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class NotaFx {
    private final ObjectProperty<Integer> id;
    private final IntegerProperty nota;

    public NotaFx() {
        this.id = new SimpleObjectProperty<>();
        this.nota = new SimpleIntegerProperty();
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

    public Integer getNota() {
        return nota.get();
    }

    public void setNota(Integer nota) {
        this.nota.set(nota);
    }

    public IntegerProperty notaProperty() {
        return this.nota;
    }
}
