package restaurant_simulator;

/**
 * Questa classe rappresenta la concretizzazione del "decoratore"
 * ExtraAddictionDecorator in relazione ai Dessert
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class DessertDecorator extends ExtraAddictionDecorator {
    
    /**
    * Un'istanza della lista degli ingredienti decoratori associati ai Dessert
    */
    protected DessertIngredients dessertIngredient;
    
    
    /**
     * Costruttore della classe FirstCourseDecorator
     * 
     * @param decorateConsumation la consumazione da decorare
     * @param dessertIngredient   gli ingredienti dei Primi che figurano come decoratori della consumazione
     */
    public DessertDecorator(IConsumation decorateConsumation, DessertIngredients dessertIngredient) {
        super(decorateConsumation);
        this.dessertIngredient= dessertIngredient;
    }
    
    
    /**
     * Questo metodo restituisce il nome della consumazione "decorata"
     * 
     * @return il nome della consumazione "decorata"
     */
    public String getName() {
        return consumationDecorator.getName() + " con aggiunta di: " + dessertIngredient;
    }
    
    
    /**
     * Questo metodo restituisce il prezzo della consumazione "decorata"
     * 
     * @return il prezzo della consumazione "decorata"
     */
    public double getPrice() {
        return consumationDecorator.getPrice()+1.5d;
    }
    
}
