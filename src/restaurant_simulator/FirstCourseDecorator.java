package restaurant_simulator;

/**
 * Questa classe rappresenta la concretizzazione del "decoratore"
 * ExtraAddictionDecorator in relazione ai Primi
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class FirstCourseDecorator extends ExtraAddictionDecorator {
    
    /**
    * Un'istanza della lista degli ingredienti decoratori associati ai Primi
    */
    protected FirstCourseIngredients firstCourseIngredient;
    
    
    /**
     * Costruttore della classe FirstCourseDecorator
     * 
     * @param decorateConsumation la consumazione da decorare
     * @param firstCourseIngredient  gli ingredienti dei Primi che figurano come decoratori della consumazione
     */
    public FirstCourseDecorator(IConsumation decorateConsumation, FirstCourseIngredients firstCourseIngredient) {
        super(decorateConsumation);
        this.firstCourseIngredient = firstCourseIngredient;
    }
    
        
    /**
     * Questo metodo restituisce il nome della consumazione "decorata"
     * 
     * @return il nome della consumazione "decorata"
     */
    public String getName() {
        return consumationDecorator.getName() + " con aggiunta di: " + firstCourseIngredient;
    }
    
    /**
     * Questo metodo restituisce il prezzo della consumazione "decorata"
     * 
     * @return il prezzo della consumazione "decorata"
     */
    public double getPrice() {
        return consumationDecorator.getPrice()+2.0d;
    }
    
}
