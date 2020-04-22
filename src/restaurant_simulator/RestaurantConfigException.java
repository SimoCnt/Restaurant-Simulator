package restaurant_simulator;

/**
 * Rappresenta l'eccezione RestaurantConfigException
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantConfigException extends Exception {
    
    /**
     * Costruttore con parametri della classe RestaurantGeneralConfigException
     * @param errors la stringa contenente gli errori che si sono verificati
     */
    public RestaurantConfigException(String errors) {
       super(errors);
    }
}
