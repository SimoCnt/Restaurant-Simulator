package restaurant_simulator;

/**
 * Rappresenta l'eccezione ManagerTableStatusException
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerTableStatusException extends Exception {
    
    /**
     * Costruttore con parametri della classe ManagerTableStatusException
     * @param errors la stringa contenente gli errori che si sono verificati
     */
    public ManagerTableStatusException(String errors) {
       super(errors);
    }
    
}
