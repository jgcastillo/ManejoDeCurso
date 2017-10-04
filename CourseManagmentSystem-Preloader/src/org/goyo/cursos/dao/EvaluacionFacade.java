package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Evaluacion;
import org.goyo.cursos.utilities.Utilities;

public class EvaluacionFacade extends AbstractFacade<Evaluacion> {

    public EvaluacionFacade() {
        super(Evaluacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
