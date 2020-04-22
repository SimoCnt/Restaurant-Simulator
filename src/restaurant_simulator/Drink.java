package restaurant_simulator;

/**
 * Classe Drink, rappresenta la categoria delle Bevande
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Drink {
    
    /**
     * Il nome della categoria
     */
    private final String name = "Bevande";

    /**
     * Costruttore senza parametri della classe Drink
     */
    public Drink() {}

    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto Drink, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto Drink
     */
    
    @Override
    public String toString(){
        return this.name;
    }
}
