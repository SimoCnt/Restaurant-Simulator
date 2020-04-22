package restaurant_simulator;


/**
 * Interfaccia ICheckAvailabilityUser, dichiara i metodi per i check delle corrispondenze
 * in User.csv
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface ICheckUser {
    
    /**
     * Questo metodo permette di effettuare un check in User.csv dell'email e della
     * password digitati dall'utente nella fase di login
     * 
     * @param emailLogin l'email digitata per il login
     * @param passwordLogin password digitata per il login
     * @return true se viene trovato un utente in User.csv con la email e password a parametro,
     * false altrimenti
     */
    boolean checkUserCorrispondenceLogin(String emailLogin, String passwordLogin);
    
    
    /**
     * Questo metodo permette di verificare che l'username scelto in fase di registrazione
     * non sia già stato utilizzato da un altro utente
     * 
     * @param username username scelto per la registrazione
     * @return true se non esiste l'username scelto in User.csv, false viceversa
     */
    boolean checkCorrispondenceUsername(String username);
    
    
    /**
     * Questo metodo permette di verificare che l'email scelta in fase di registrazione
     * non sia già stata utilizzata da un altro utente
     * 
     * @param email email scelta per la registrazione
     * @return true se non esiste l'email scelta in User.csv, false altrimenti
     */
    boolean checkCorrispondenceEmail(String email);
}
