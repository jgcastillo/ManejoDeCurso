package org.goyo.cursos.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Curso;
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
    
    public List<Intervencion> findAllIntervencionesByCurso(Curso curso){
        List<Intervencion> intervenciones = null;
        try {
            String query = "FROM Intervencion i WHERE i.cursoId = :curso";
            TypedQuery<Intervencion> q = getEntityManager().createQuery(query, Intervencion.class);
            q.setParameter("curso", curso);
            intervenciones = q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(IntervencionFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return intervenciones;
    }
}
