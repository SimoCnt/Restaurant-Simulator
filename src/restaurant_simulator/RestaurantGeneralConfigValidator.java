package restaurant_simulator;


/**
 * Classe RestaurantGeneralConfigValidator, gestisce la validazione dei campi di
 * RestaurantGeneralConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantGeneralConfigValidator implements IRestaurantGeneralConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantGeneralConfig
     * 
     * @param rgc un'istanza di tipo RestaurantGeneralConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(RestaurantGeneralConfig rgc) throws RestaurantConfigException {
        
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione: Nome ristorante
        errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rgc.getInputRestaurantName())
        .getThisMessageIfInvalid("Errore: Perfavore specifica un nome per il tuo ristorante valido\n").orElse(""));

        errorFields.append(ValidatorUtility.stringBetween(0, 20).test(rgc.getInputRestaurantName())
        .getThisMessageIfInvalid("Errore: Il nome del tuo ristorante deve essere compreso tra 1 e 20 caratteri inclusi\n").orElse(""));        
        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new RestaurantConfigException(errors);
        }
        return true;
    }
}

