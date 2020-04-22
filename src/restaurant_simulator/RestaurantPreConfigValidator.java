package restaurant_simulator;


/**
 * Classe RestaurantPreConfigValidator, gestisce la validazione dei campi di
 * RestaurantPreConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantPreConfigValidator implements IRestaurantPreConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantPreConfig
     * 
     * @param rpc un'istanza di tipo RestaurantPreConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(RestaurantPreConfig rpc) throws RestaurantConfigException {
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione: Nome ristorante
        errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rpc.getInputRestName())
        .getThisMessageIfInvalid("Errore: Perfavore specifica un nome per il tuo ristorante valido\n").orElse(""));

        errorFields.append(ValidatorUtility.stringBetween(0, 20).test(rpc.getInputRestName())
        .getThisMessageIfInvalid("Errore: Il nome del tuo ristorante deve essere compreso tra 1 e 20 caratteri inclusi\n").orElse(""));

        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new RestaurantConfigException(errors);
        }
        
        return true;
    }
}

