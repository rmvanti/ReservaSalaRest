

package reservasala.service;

/**
 * @author rmvanti
 */
public class ServiceError {

    private String message;
    private String method;

    public ServiceError(){}//fim construtor

    public ServiceError(String method, String message) {
        this.message = message;
        this.method = method;
    }//fim construtor

    public String getMessage() {
        return message;
    } 

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return String.format("Method: %s, Message: %s.", getMethod(), getMessage());                
    }
    
    
        
}//fim class
