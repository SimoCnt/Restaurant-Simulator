package restaurant_simulator;

/**
 * Interfaccia IUserBuilder, dichiara i metodi per la costruzione dell'oggetto
 * User
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IUserBuilder {
    
    /**
     * Questo metodo crea un oggetto di tipo User
     */
    public void createUser();
    
    
    /**
     * Questo metodo aggiunge i dati di registrazione all'oggetto User 
     * 
     * @param email l'email del cliente loggato
     * @param password la password del cliente loggato
     */
    public void addInfo(String email, String password);
    
    
    /**
     * Questo metodo aggiunge la lista degli ordini associati al cliente loggato
     */
    public void addOrders();
    
    
    /**
     * Questo metodo restituisce l'oggetto User
     * 
     * @return l'oggetto User
     */
    public User getUser();
}
