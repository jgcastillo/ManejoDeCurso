package org.goyo.cursos.dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Curso;
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
    
    public Optional<List<Estudiante>> findAllEstudianteByCurso(Curso curso){
        Optional<List<Estudiante>> optEstudiantes = Optional.empty();
        try {
            String query = "FROM Estudiante e WHERE e.cursoId = :curso";
            TypedQuery<Estudiante> q = getEntityManager().createQuery(query, Estudiante.class);
            q.setParameter("curso", curso);
            optEstudiantes = Optional.ofNullable(q.getResultList());
        } catch (Exception e) {
            Logger.getLogger(EstudianteFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return optEstudiantes;
    }
}
