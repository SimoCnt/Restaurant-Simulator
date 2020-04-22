package restaurant_simulator;

/**
 * Interfaccia IRestaurantMenuConfigValidator, dichiara un metodo per la 
 * validazione dei dati immessi nella GUI RestaurantMenuConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRestaurantMenuConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantMenuConfig
     * 
     * @param rmc un'istanza di tipo RestaurantGeneralConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(RestaurantMenuConfig rmc) throws RestaurantConfigException;
}
