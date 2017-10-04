package org.goyo.cursos.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.model.Periodo;
import org.goyo.cursos.utilities.Utilities;

public class CursoFacade extends AbstractFacade<Curso>{

    public CursoFacade() {
        super(Curso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
    
    public Optional<List<Curso>> findAllCursoByPeriodo(Periodo periodo){
        Optional<List<Curso>> optCursos = Optional.empty();
        try {
            String query = "FROM Curso c WHERE c.periodoId = :periodo";
            TypedQuery<Curso> q = getEntityManager().createQuery(query, Curso.class);
            q.setParameter("periodo", periodo);
            optCursos = Optional.ofNullable(q.getResultList());
        } catch (Exception e) {
            Logger.getLogger(CursoFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return optCursos;
    }
    
}
