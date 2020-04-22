package restaurant_simulator;


/**
 * Classe UserBuilderDirector, dirige la creazione di un oggetto User
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserBuilderDirector {
    
    /**
     * Questo metodo costruisce un cliente loggato al ristorante dirigendo 
     * l'assemblaggio delle varie parti dell'oggetto
     * 
     * @param builder istanza di tipo IUserBuilder
     * @param email l'email del cliente
     * @param password la password del cliente
     * @return l'oggetto User creato
     */
    public User buildLoggedUser(IUserBuilder builder, String email, String password) {
        builder.createUser();
        builder.addInfo(email, password);
        builder.addOrders();
        return builder.getUser();
    }
}
