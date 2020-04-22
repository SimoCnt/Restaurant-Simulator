package restaurant_simulator;

/**
 * Classe Starter, rappresenta la categoria degli Antipasti
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Starter{
    
    /**
     * Il nome della categoria
     */
    private final String name = "Antipasti";

    /**
     * Costruttore senza parametri della classe Starter
     */
    public Starter() {}
 

    /**
     * Questo metodo restituisce la descrizione dell'oggetto Starter, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto Starter
     */
    @Override
    public String toString(){
        return this.name;
    }
}
