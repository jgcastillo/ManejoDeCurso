package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Asistencia;
import org.goyo.cursos.utilities.Utilities;

public class AsistenciaFacade extends AbstractFacade<Asistencia> {

    public AsistenciaFacade() {
        super(Asistencia.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
