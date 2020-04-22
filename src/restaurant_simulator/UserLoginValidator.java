package restaurant_simulator;

/**
 * Classe UserLoginValidator, gestisce la validazione dei campi relativi al login
 * di UserAuthentication
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserLoginValidator implements IUserAuthenticationValidator {
    
    /**
     * Metodo validate, permette di validare i campi di UserAuthentication relativi al login
     * 
     * @param ua un'istanza di tipo UserAuthentication
     * @throws UserAuthenticationException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(UserAuthentication ua) throws UserAuthenticationException  {
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione Email
        if ((ValidatorUtility.notEmptyString.and(ValidatorUtility.notNullString).test(ua.getInputEmailLogin()).isValid())) {
            errorFields.append(ValidatorUtility.isValidEmail.test(ua.getInputEmailLogin())
            .getThisMessageIfInvalid("Errore Email: Il formato dell'Email inserita non è corretto\n").orElse(""));
        }
        else {
            errorFields.append("Errore Email: Campo vuoto\n");
        }
        
        // Validazione password
        errorFields.append(ValidatorUtility.notEmptyString.and(ValidatorUtility.notNullString).test(ua.getInputPasswordLogin())
            .getThisMessageIfInvalid("Errore Password: Campo vuoto\n").orElse(""));
     
        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new UserAuthenticationException(errors);
        }
        return true;
    }
    
}
