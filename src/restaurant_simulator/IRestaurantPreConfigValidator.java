package restaurant_simulator;


/**
 * Interfaccia IRestaurantPreConfigValidator, dichiara un metodo per la 
 * validazione dei dati immessi nella GUI RestaurantPreConfigValidator
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRestaurantPreConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantPreConfig
     * 
     * @param rpc un'istanza di tipo RestaurantPreConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(RestaurantPreConfig rpc) throws RestaurantConfigException;
}
