package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Nota;
import org.goyo.cursos.utilities.Utilities;

public class NotaFacade extends AbstractFacade<Nota> {

    public NotaFacade() {
        super(Nota.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
