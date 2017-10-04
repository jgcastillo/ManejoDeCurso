package org.goyo.cursos.dao;

import javax.persistence.EntityManager;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.utilities.Utilities;

public class CursoFacade extends AbstractFacade<Curso>{

    public CursoFacade() {
        super(Curso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
    
}
