package restaurant_simulator;

/**
 * Classe IManagerCheckTableStatusValidator, gestisce la validazione dei campi relativi 
 * al check dello stato di un tavolo nell'Agenda di Home Manager
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IManagerCheckTableStatusValidator {
    
    /**
     * Metodo validate, permette di validare i campi di ManagerTableList
     * 
     * @param mtl un'istanza di tipo ManagerTableList
     * @throws ManagerTableStatusException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(ManagerTableList mtl) throws ManagerTableStatusException;
}
