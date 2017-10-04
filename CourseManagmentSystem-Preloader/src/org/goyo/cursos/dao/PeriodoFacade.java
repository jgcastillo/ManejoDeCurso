package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Periodo;
import org.goyo.cursos.utilities.Utilities;

public class PeriodoFacade extends AbstractFacade<Periodo> {

    public PeriodoFacade() {
        super(Periodo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
