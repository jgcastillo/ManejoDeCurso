package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.TipoEvaluacion;
import org.goyo.cursos.utilities.Utilities;

public class TipoEvaluacionFacade extends AbstractFacade<TipoEvaluacion>{

    public TipoEvaluacionFacade() {
        super(TipoEvaluacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
    
}
