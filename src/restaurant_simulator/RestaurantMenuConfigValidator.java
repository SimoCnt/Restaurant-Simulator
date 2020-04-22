package restaurant_simulator;

/**
 * Classe RestaurantMenuConfigValidator, gestisce la validazione dei campi di
 * RestaurantMenuConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantMenuConfigValidator implements IRestaurantMenuConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantMenuConfig
     * 
     * @param rmc un'istanza di tipo RestaurantGeneralConfig
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(RestaurantMenuConfig rmc) throws RestaurantConfigException {
        
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione: Nome prodotto
        errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rmc.getInputProductName())
        .getThisMessageIfInvalid("Errore: Nome prodotto non inserito\n").orElse(""));
        
        // Validazione: Prezzo prodotto
        
        if ((ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rmc.getInputProductPrice()).isValid())) {            
            if(!(ValidatorUtility.isDouble.test(rmc.getInputProductPrice().replace(",", ".")).isValid())) {
                errorFields.append("Errore: Prezzo non valido");
            }
        }
        else {
            errorFields.append("Errore: Il prezzo del prodotto non è stato inserito\n");
        }
        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new RestaurantConfigException(errors);
        }
        return true;
    }
 
}
