package restaurant_simulator;

/**
 * Rappresenta l'eccezione UserAuthenticationException
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserAuthenticationException extends Exception {
    
    /**
     * Costruttore con parametri della classe UserAuthenticationException
     * @param errors la stringa contenente gli errori che si sono verificati
     */
    public UserAuthenticationException(String errors) {
       super(errors);
    }
}
