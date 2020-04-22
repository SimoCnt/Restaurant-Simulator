package restaurant_simulator;

/**
 * Classe SecondCourse, rappresenta la categoria dei Secondi
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class SecondCourse {
    
    /**
     * Il nome della categoria
     */
    private final String name = "Secondi";

    /**
     * Costruttore senza parametri della classe SecondCourse
     */
    public SecondCourse() {}

    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto SecondCourse, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto SecondCourse
     */
    @Override
    public String toString(){
        return this.name;
    }
}
