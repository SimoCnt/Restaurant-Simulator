package restaurant_simulator;

/**
 * Classe FirstCourse, rappresenta la categoria dei Primi
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class FirstCourse {
    
    /**
     * Il nome della categoria
     */
    private final String name = "Primi";

    /**
     * Costruttore senza parametri della classe FirstCourse
     */
    public FirstCourse() {}

    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto FirstCourse, ovvero
     * il nome della categoria dei prodotti.
     * 
     * @return la descrizione dell'oggetto FirstCourse
     */
    
    @Override
    public String toString(){
        return this.name;
    }
}
