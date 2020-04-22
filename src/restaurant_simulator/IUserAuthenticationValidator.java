package restaurant_simulator;


/**
 * Interfaccia IUserAuthenticationValidator, dichiara un metodo per la 
 * validazione dei dati immessi nella GUI UserAuthentication
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IUserAuthenticationValidator {
    
    /**
     * Metodo validate, permette di validare i campi di UserAuthentication
     * 
     * @param ua un'istanza di tipo UserAuthentication
     * @throws UserAuthenticationException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(UserAuthentication ua) throws UserAuthenticationException;
}
