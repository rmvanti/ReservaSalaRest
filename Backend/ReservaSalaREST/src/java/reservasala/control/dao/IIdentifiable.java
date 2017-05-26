package reservasala.control.dao;

/**
 * @author rmvanti
 * @param <T>
 */
public interface IIdentifiable<T> {

    public T getId();
    public void setId(T id);
    
}//fim interface
