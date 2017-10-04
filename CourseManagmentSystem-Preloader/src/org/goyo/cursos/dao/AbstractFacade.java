package org.goyo.cursos.dao;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;
    
    public AbstractFacade(Class<T> entityClass){
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();
    
    public <T> T create(T entity){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            if(em != null){
                em.close();
            }
        }
        return entity;
    }
    
    public void batchSave(Collection<T> col){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            int count = 0;
            for(T element : col){
                em.persist(element);
                if(count % 50 == 0){
                    em.flush();
                    em.clear();
                }
                count++;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public <T> T edit(T entity){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entity;
    }
    
    public void batchEdit(Collection<T> col) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            int count = 0;
            for (T element : col) {
                em.merge(element);
                if (count % 50 == 0) {
                    em.flush();
                    em.clear();
                }
                count++;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void remove(T entity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(entity));
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public T find(Object id){
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll(){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public int count(){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long)q.getSingleResult()).intValue();
    }
}
