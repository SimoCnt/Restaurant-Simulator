package restaurant_simulator;


/**
 * Classe RestaurantBuilderDirector, dirige la creazione di un oggetto Restaurant
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantBuilderDirector {
    
    
    /**
     * Questo metodo costruisce un ristorante dirigendo l'assemblaggio 
     * delle varie parti dell'oggetto
     * 
     * @param builder istanza di tipo IRestaurantBuilder
     * @return l'oggetto Restaurant creato
     */
    public Restaurant buildRestaurant(IRestaurantBuilder builder) {
        builder.createRestaurant();
        builder.addInfoRestaurant();
        builder.addTables();
        builder.addMenu();
        builder.addMenu();
        builder.addOrders();
        builder.addUsers();
        builder.addFeedback();
        return builder.getRestaurant();
    }
}