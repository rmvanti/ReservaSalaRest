package reservasala.control.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author rmvanti
 * @param <E> Entity type
 * @param <K> Primary key type
 */
public class DaoImpl<E,K> implements IDao<E,K>{

    private final EntityManager em;
    private final Class entityClass;        
    
    public DaoImpl(Class entityClass, EntityManager em){
        this.entityClass = entityClass;        
        this.em = em;
    }//fim construtor   
            
    @Override
    public void insert(E e) {
        this.em.getTransaction().begin();
        this.em.persist(e);
        this.em.getTransaction().commit();
    }    
    
    @Override
    public E update(E e) {
        this.em.getTransaction().begin();
        e = this.em.merge(e);
        this.em.getTransaction().commit();
        return e;
    }

    @Override
    public void delete(E e) {
        this.em.getTransaction().begin();
        this.em.remove(e);
        this.em.getTransaction().commit();
    }
    
    @Override
    public void deleteById(K id) {
        this.em.getTransaction().begin();
        this.em.remove(findById(id));
        this.em.getTransaction().commit();
    }
     
    @Override
    public E retrieve(K id) {
        return findById(id);
    }
    
    @Override
    public long count() {        
        CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
        Root<E> rt = cq.from(this.entityClass);
        cq.select(this.em.getCriteriaBuilder().count(rt));
        Query q = this.em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    
    @Override
    public List<E> findAll() {        
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery();
        query.select(query.from(this.entityClass));
        return new ArrayList(this.em.createQuery(query).getResultList());          
    }

    @Override
    public E findById(K id) {
        return (E)this.em.find(entityClass, id);
    }
        
    @Override
    public List<E> executeNamedQueryWithMultipleResult(String namedQueryName, Map params) {
       Query query = this.em.createNamedQuery(namedQueryName);
        if (params != null) {
            Set set = params.keySet();
            Iterator it = set.iterator();
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                query.setParameter(key, params.get(key));
            }//fim while
        }//fim if
        return new ArrayList(query.getResultList()); 
    }

    @Override    
    public Object executeNamedQueryWithSingleResult(String namedQueryName, Map params) {        
        Query query = this.em.createNamedQuery(namedQueryName);
        if (params != null) {
            Set set = params.keySet();
            Iterator it = set.iterator();
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                query.setParameter(key, params.get(key));
            }//fim while
        }//fim if
        return query.getSingleResult();
    }
    
    @Override
    public List<E> executeNamedNativeQueryWithMultipleResult(String namedNativeQueryName, Map params) {
        Query query = this.em.createNativeQuery(namedNativeQueryName);
        if (params != null) {
            Set set = params.keySet();
            Iterator it = set.iterator();
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                query.setParameter(key, params.get(key));
            }//fim while
        }//fim if
        return new ArrayList(query.getResultList());
    }

    @Override
    public Object executeNamedNativeQueryWithSingleResult(String namedNativeQueryName, Map params) {        
        Query query = this.em.createNativeQuery(namedNativeQueryName);
        if (params != null) {
            Set set = params.keySet();
            Iterator it = set.iterator();
            String key;
            while (it.hasNext()) {
                key = (String) it.next();
                query.setParameter(key, params.get(key));
            }//fim while
        }//fim if
        return query.getSingleResult();
    }

    @Override
    public List<E> executeNativeQueryWithMultipleResult(String nativeQuery, Map params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates. 
    }

    @Override
    public Object executeNativeQueryWithSingleResult(String nativeQuery, Map params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
       
}//fim class
