package restaurant_simulator;

/**
 * Classe IUserReservationCheckAvailabilityValidator, gestisce la validazione dei campi relativi 
 * al check della disponibilità di un tavolo in UserReservation
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IUserReservationCheckAvailabilityValidator {
    
    /**
     * Metodo validate, permette di validare i campi di UserReservation
     * 
     * @param ur un'istanza di tipo UserReservation
     * @throws UserReservationException se si verifica almeno un errore nella validazione
     * @return true se tutti i dati sono validi, false altrimenti
     */
    boolean validate(UserReservation ur) throws UserReservationException;
}
