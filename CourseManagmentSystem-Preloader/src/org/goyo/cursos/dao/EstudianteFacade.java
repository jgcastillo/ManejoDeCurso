package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Estudiante;
import org.goyo.cursos.utilities.Utilities;

public class EstudianteFacade extends AbstractFacade<Estudiante> {

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
}
