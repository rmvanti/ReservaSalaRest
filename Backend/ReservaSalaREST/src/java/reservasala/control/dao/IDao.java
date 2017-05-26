package reservasala.control.dao;

import java.util.List;
import java.util.Map;

/**
 * @author rmvanti
 * @param <E> Entity type
 * @param <K> Primary key type
 */
public interface IDao<E,K> {

    public void insert(E t);
    public E update(E t);
    
    public void delete(E t);
    public void deleteById(K id);
    
    public E retrieve(K id);
        
    public long count();
        
    public List<E> findAll();
    
    public E findById(K id);
    
    public List<E> executeNamedQueryWithMultipleResult(String namedQueryName, Map params);
    public Object executeNamedQueryWithSingleResult(String namedQueryName, Map params);
        
    public List<E> executeNamedNativeQueryWithMultipleResult(String namedNativeQueryName, Map params);
    public Object executeNamedNativeQueryWithSingleResult(String namedNativeQueryName, Map params);

    public List<E> executeNativeQueryWithMultipleResult(String nativeQuery, Map params);
    public Object executeNativeQueryWithSingleResult(String nativeQuery, Map params);
    
}
