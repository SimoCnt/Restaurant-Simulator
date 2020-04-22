package restaurant_simulator;

/**
 * Classe Fruit, rappresenta la categoria della Frutta
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Fruit {
    
    /**
     * Il nome della categoria
     */
    private final String name = "Frutta";

    /**
     * Costruttore senza parametri della classe Fruit
     */
    public Fruit() {}
 

    /**
     * Questo metodo restituisce la descrizione dell'oggetto Fruit, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto Fruit
     */
    @Override
    public String toString(){
        return this.name;
    }
}
