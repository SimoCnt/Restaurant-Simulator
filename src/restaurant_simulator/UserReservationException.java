package restaurant_simulator;

/**
 * Rappresenta l'eccezione UserReservationException
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserReservationException extends Exception {
    
    /**
     * Costruttore con parametri della classe UserReservationException
     * @param errors la stringa contenente gli errori che si sono verificati
     */
    public UserReservationException(String errors) {
       super(errors);
    }
    
}
