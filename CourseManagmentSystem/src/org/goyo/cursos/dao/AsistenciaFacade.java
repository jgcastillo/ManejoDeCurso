package org.goyo.cursos.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Asistencia;
import org.goyo.cursos.model.Curso;
import org.goyo.cursos.modelfx.AsistenciaFx;
import org.goyo.cursos.utilities.Utilities;

public class AsistenciaFacade extends AbstractFacade<Asistencia> {

    public AsistenciaFacade() {
        super(Asistencia.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> findAsistentesByCurso(Curso curso){
        List<Object[]> lista = null;
        try {
            String query = "SELECT DISTINCT a.fecha, COUNT(a.estudianteCi) "
                    + "FROM Asistencia a "
                    + "WHERE a.cursoId = :curso AND a.asistencia = :param "
                    + "GROUP BY a.fecha";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("curso", curso);
            q.setParameter("param", AsistenciaFx.ASISTIO);
            lista = q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(AsistenciaFacade.class.getName()).log(Level.SEVERE,
                    e.getMessage());
        }
        return lista;
    }
    
    public List<Asistencia> findAsistentesByCursoAndDate(Curso curso, Date fecha){
        List<Asistencia> lista = null;
        try {
            String query = "FROM Asistencia a "
                    + "WHERE a.cursoId = :curso AND a.fecha = :fecha";
            TypedQuery<Asistencia> q = getEntityManager().createQuery(query, Asistencia.class);
            q.setParameter("curso", curso);
            q.setParameter("fecha", fecha);
            lista = q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(AsistenciaFacade.class.getName()).log(Level.SEVERE,
                    e.getMessage());
        }
        return lista;
    }
}
