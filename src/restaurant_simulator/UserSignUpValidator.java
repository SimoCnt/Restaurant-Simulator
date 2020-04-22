package restaurant_simulator;
        
/**
 * Classe UserSignUpValidator, gestisce la validazione dei campi relativi alla registrazione
 * di UserAuthentication
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserSignUpValidator implements IUserAuthenticationValidator {
    
    /**
     * Metodo validate, permette di validare i campi di UserAuthentication relativi alla registrazione
     * 
     * @param ua un'istanza di tipo UserAuthentication
     * @throws UserAuthenticationException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(UserAuthentication ua) throws UserAuthenticationException  {
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione Username
        if (ValidatorUtility.notEmptyString.and(ValidatorUtility.notNullString).test(ua.getInputUsernameRegistration()).isValid()) {
            errorFields.append(ValidatorUtility.notFirstCharIsDigit.test(ua.getInputUsernameRegistration())
            .getThisMessageIfInvalid("Errore Username: Il primo carattere non può essere una cifra\n").orElse(""));
            
            errorFields.append(ValidatorUtility.notEmptyString.and(ValidatorUtility.stringBetween(3, 17)).test(ua.getInputUsernameRegistration())
            .getThisMessageIfInvalid("Errore Username: Inserisci un Username compreso tra 4 e 16 caratteri\n").orElse(""));
        }
        else {
            errorFields.append("Errore Username: Campo vuoto\n");
        }
        
        // Validazione Email
        if ((ValidatorUtility.notEmptyString.and(ValidatorUtility.notNullString).test(ua.getInputEmailRegistration()).isValid())) {
            errorFields.append(ValidatorUtility.isValidEmail.test(ua.getInputEmailRegistration())
            .getThisMessageIfInvalid("Errore Email: Il formato dell'Email inserita non è corretto\n").orElse(""));
        }
        else {
            errorFields.append("Errore Email: Campo vuoto\n");
        }
        
        // Validazione password
        if((ValidatorUtility.notEmptyString.and(ValidatorUtility.notNullString).test(ua.getInputPasswordRegistration()).isValid())) {
            errorFields.append(ValidatorUtility.stringMoreThan(5).test(ua.getInputPasswordRegistration())
            .getThisMessageIfInvalid("Errore Password: La password deve contenere almeno 6 caratteri\n").orElse(""));
        }
        else {
            errorFields.append("Errore Password: Campo vuoto\n");
        }
        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new UserAuthenticationException(errors);
        }
        return true;
    }
    
}
