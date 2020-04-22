package restaurant_simulator;

import java.util.Optional;

/**
 * Classe GenericValidationResult, gestisce il risultato della validazione generica
 * effettuata
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class GenericValidationResult {
    
    /**
     * Attributo booleano che specifica il risultato della validazione
     */
    private boolean valid;
    
    
    /**
     * Costruttore con parametri della classe GenericValidationResult
     * 
     * @param valid stato della validazione
     */
    private GenericValidationResult(boolean valid) {
        this.valid = valid;
    }
    
    
    /**
     * Questo metodo restituisce il risultato della validazione
     * 
     * @return true se la validazione ha esito posito, false altrimenti
     */
    public boolean isValid() {
        return valid;
    }
    
    
    /**
     * Metodo approved, viene invocato quando il test di validazione ha esito positivo 
     * e permette di restituire un'istanza di GenericValidationResult con proprietà valid settata a true
     * 
     * @return un'istanza di GenericValidationResult con proprietà valid settata a true
     */
    public static GenericValidationResult approved() {
        return new GenericValidationResult(true);
    }
    
    
    /**
     * Metodo disapproved, viene invocato quando il test di validazione ha esito negativo 
     * e permette di restituire un'istanza di GenericValidationResult con proprietà valid settata a false
     * 
     * @return un'istanza di GenericValidationResult con proprietà valid settata a false
     */
    public static GenericValidationResult disapproved() {
    
        return new GenericValidationResult(false);
    }
    
    
    /**
     * Metodo getThisMessageIfInvalid, restituisce il messaggio passato a parametro nel caso
     * in cui la validazione non vada a buon fine
     * 
     * @param message il messaggio da restituire nel caso in cui la validazione non va a buon fine
     * @return un'istanza di tipo Optional il cui parametro è un'oggetto String
     */
    public Optional<String> getThisMessageIfInvalid(String message) {
        return this.valid ? Optional.empty() : Optional.of(message);
    }
}
