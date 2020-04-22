package restaurant_simulator;


/**
 * Classe UserReservationCheckAvailabilityValidator, gestisce la validazione dei campi relativi 
 * al check della disponibilità di un tavolo in UserReservation
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserReservationCheckAvailabilityValidator implements IUserReservationCheckAvailabilityValidator {
    
    /**
     * Metodo validate, permette di validare i campi di UserReservation
     * 
     * @param ur un'istanza di tipo UserReservation
     * @throws UserReservationException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(UserReservation ur) throws UserReservationException  {
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione: Nome ristorante
        errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(ur.getInputDate())
        .getThisMessageIfInvalid("Errore: Inserisci una data valida\n").orElse(""));
        
        if(!(ValidatorUtility.isInteger.test(ur.getInputNumClients()).isValid())) {
            errorFields.append("Errore: Il formato del numero di posti del tavolo non è valido");
        }
        else {
            int numSeatsTable = Integer.parseInt(ur.getInputNumClients());
            errorFields.append(ValidatorUtility.intBetween(0, 11).test(numSeatsTable)
            .getThisMessageIfInvalid("Il numero di posti inseriti non è valido (Ricorda: intero compreso tra 1 e 10 inclusi)\n").orElse(""));
        }

        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new UserReservationException(errors);
        }
        
        return true;
    }
}

