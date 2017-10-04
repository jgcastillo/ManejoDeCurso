package org.goyo.cursos.dao;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.goyo.cursos.model.Periodo;
import org.goyo.cursos.utilities.Utilities;

public class PeriodoFacade extends AbstractFacade<Periodo> {

    public PeriodoFacade() {
        super(Periodo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return (Utilities.EMF).createEntityManager();
    }
    
    public Optional<Periodo> findPeriodoActivo(){
        Optional<Periodo> periodo = Optional.empty();
        try {
            String query = "FROM Periodo p WHERE p.status = '1'";
            TypedQuery<Periodo> q = getEntityManager().createQuery(query, Periodo.class);
            periodo = Optional.ofNullable(q.getSingleResult());
        } catch (Exception e) {
            Logger.getLogger(PeriodoFacade.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return periodo;
    }
}
