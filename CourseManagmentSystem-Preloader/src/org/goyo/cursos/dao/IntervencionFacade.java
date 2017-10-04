package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Intervencion;
import org.goyo.cursos.utilities.Utilities;

public class IntervencionFacade extends AbstractFacade<Intervencion> {

    public IntervencionFacade() {
        super(Intervencion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
