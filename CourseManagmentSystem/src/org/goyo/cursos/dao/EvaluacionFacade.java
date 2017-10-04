package org.goyo.cursos.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Curso;
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
    
    public Optional<List<Evaluacion>> findAllByCurso(Curso curso){
        Optional<List<Evaluacion>> optEvaluaciones = Optional.empty();
        try {
            String query = "FROM Evaluacion e WHERE e.cursoId = :curso";
            TypedQuery<Evaluacion> q = getEntityManager().createQuery(query, Evaluacion.class);
            q.setParameter("curso", curso);
            optEvaluaciones = Optional.ofNullable(q.getResultList());
        } catch (Exception e) {
            Logger.getLogger(EvaluacionFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return optEvaluaciones;
    }
}
