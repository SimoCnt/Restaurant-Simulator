package restaurant_simulator;

/**
 * Classe RestaurantTableConfigValidator, gestisce la validazione dei campi di
 * RestaurantTableConfig
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantTableConfigValidator implements IRestaurantTableConfigValidator {
    
    /**
     * Metodo validate, permette di validare i campi di RestaurantTableConfig
     * 
     * @param rtc un'istanza di tipo RestaurantTableConfig
     * @param flag 0 per validare l'aggiunta dei tavoli, 1 per validare la modifica dei tavoli
     * @throws RestaurantConfigException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(RestaurantTableConfig rtc, int flag) throws RestaurantConfigException {
        StringBuilder errorFields = new StringBuilder();
        
        if (flag == 0 || flag == 1) {
        
            //Validazione: Aggiunta tavoli
            if (flag==0) {
                errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rtc.getInputTableName())           
                .getThisMessageIfInvalid("Inserisci un nome per il tavolo valido\n").orElse(""));

                if(!(ValidatorUtility.isInteger.test(rtc.getInputNumSeatsTable()).isValid())) {
                    errorFields.append("Errore: Il formato del numero di posti del tavolo non è valido");
                }
                else {
                    int numSeatsTable = Integer.parseInt(rtc.getInputNumSeatsTable());
                    errorFields.append(ValidatorUtility.intBetween(0, 11).test(numSeatsTable)
                    .getThisMessageIfInvalid("Il numero di posti inseriti non è valido (Ricorda: intero compreso tra 1 e 10 inclusi)\n").orElse(""));
                }
            }
            // Validazione: Modifica tavoli
            else if (flag==1) {
                errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(rtc.getInputNewTableName())           
                .getThisMessageIfInvalid("Inserisci un nome per il tavolo valido!\n").orElse(""));

                if(!(ValidatorUtility.isInteger.test(rtc.getInputNewNumSeatsTable()).isValid())) {
                    errorFields.append("Errore: Il formato del numero di posti del tavolo non è valido");
                }
                else {
                    int numSeatsTable = Integer.parseInt(rtc.getInputNewNumSeatsTable());
                    errorFields.append(ValidatorUtility.intBetween(0, 11).test(numSeatsTable)
                    .getThisMessageIfInvalid("Il numero di posti inseriti non è valido (Ricorda: intero compreso tra 1 e 10 inclusi)\n").orElse(""));
                }
            }
        
            String errors = errorFields.toString();

            if (!errors.isEmpty()) {
                throw new RestaurantConfigException(errors);
            }
            return true;
        }
        else {
            throw new RestaurantConfigException("Impossibile eseguire la validazione");
        }
    }
}

