package restaurant_simulator;

/**
 * Interfaccia IRestaurantBuilder, dichiara i metodi per la costruzione dell'oggetto
 * Restaurant (ovvero il ristorante)
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRestaurantBuilder {
    
    /**
     * Crea un oggetto di tipo Restaurant
     */
    public void createRestaurant();
    
    
    /**
     * Questo metodo aggiunge le informazioni base del ristorante (id, nome ristorante) all'oggetto
     * Restaurant
     */
    public void addInfoRestaurant();
    
    
    /**
     * Questo metodo aggiunge una lista di tavoli all'oggetto Restaurant
     */
    public void addTables();
    
    
    /**
     * Questo metodo aggiunge il menu all'oggetto Restaurant
     */
    public void addMenu();
    
    
    /**
     * Questo metodo aggiunge la lista dei clienti all'oggetto Restaurant
     */
    public void addUsers();
    
    
    /**
     * Questo metodo aggiunge la lista degli ordini all'oggetto Restaurant
     */
    public void addOrders();
    
    
    /**
     * Questo metodo aggiunge la lista dei feedback all'oggetto Restaurant
     */
    public void addFeedback();
    
    
    /**
     * Questo metodo restituisce l'oggetto Restaurant 
     * 
     * @return l'oggetto Restaurant
     */
    public Restaurant getRestaurant();
}
