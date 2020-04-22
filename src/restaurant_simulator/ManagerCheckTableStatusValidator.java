package restaurant_simulator;


/**
 * Classe ManagerCheckTableStatusValidator, gestisce la validazione dei campi relativi 
 * al heck dello stato di un tavolo nell'Agenda di Home Manager
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerCheckTableStatusValidator implements IManagerCheckTableStatusValidator {
    
    /**
     * Metodo validate, permette di validare i campi di ManagerTableList
     * 
     * @param mtl un'istanza di tipo ManagerTableList
     * @throws ManagerTableStatusException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    public boolean validate(ManagerTableList mtl) throws ManagerTableStatusException  {
        StringBuilder errorFields = new StringBuilder();
        
        // Validazione: Nome ristorante
        errorFields.append(ValidatorUtility.notNullString.and(ValidatorUtility.notEmptyString).test(mtl.getInputDate())
        .getThisMessageIfInvalid("Errore: Inserisci una data valida\n").orElse(""));
        
        String errors = errorFields.toString();
        
        if (!errors.isEmpty()) {
            throw new ManagerTableStatusException(errors);
        }
        
        return true;
    }
}

