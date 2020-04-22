package restaurant_simulator;

/**
 * Interfaccia IRestaurantGeneralConfigValidator, dichiara un metodo per la 
 * validazione dei dati immessi nella GUI RestaurantGeneralConfigValidator
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRestaurantGeneralConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantGeneralConfig
     * 
     * @param rgc un'istanza di tipo RestaurantGeneralConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(RestaurantGeneralConfig rgc) throws RestaurantConfigException;
}
