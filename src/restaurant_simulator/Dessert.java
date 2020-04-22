package restaurant_simulator;

/**
 * Classe Dessert, rappresenta la categoria dei Dessert
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Dessert {
    
    /**
     * Il nome della categoria
     */
    private final String name = "Dessert";

    /**
     * Costruttore senza parametri della classe Dessert
     */
    public Dessert() {}

    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto Dessert, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto Dessert
     */
    @Override
    public String toString(){
        return this.name;
    }
}
