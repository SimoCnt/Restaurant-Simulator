package restaurant_simulator;

/**
 * Interfaccia IRestaurantTableConfigValidator, dichiara un metodo per la 
 * validazione dei dati immessi nella GUI RestaurantTableConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRestaurantTableConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantTableConfig
     * 
     * @param rtc un'istanza di tipo RestaurantTableConfig
     * @param flag 0 per validare l'aggiunta dei tavoli, 1 per validare la modifica dei tavoli
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(RestaurantTableConfig rtc, int flag) throws RestaurantConfigException;
}
