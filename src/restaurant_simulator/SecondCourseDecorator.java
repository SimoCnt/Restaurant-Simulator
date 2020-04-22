package restaurant_simulator;

/**
 * Questa classe rappresenta la concretizzazione del "decoratore"
 * ExtraAddictionDecorator in relazione ai Secondi
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class SecondCourseDecorator extends ExtraAddictionDecorator {
    
    /**
    * Un'istanza della lista degli ingredienti decoratori associati ai Secondi
    */
    protected SecondCourseIngredients secondCourseIngredient;

    
    /**
     * Costruttore della classe SecondCourseDecorator
     * 
     * @param decorateConsumation la consumazione da decorare
     * @param secondCourseIngredient gli ingredienti dei Secondi che figurano come decoratori della consumazione
     */
    public SecondCourseDecorator(IConsumation decorateConsumation, SecondCourseIngredients secondCourseIngredient) {
        super(decorateConsumation);
        this.secondCourseIngredient = secondCourseIngredient;
    }
    
    /**
     * Questo metodo restituisce il nome della consumazione "decorata"
     * 
     * @return il nome della consumazione "decorata"
     */
    public String getName() {
        return consumationDecorator.getName() + " con aggiunta di: " + secondCourseIngredient;
    }
    
    
    /**
     * Questo metodo restituisce il prezzo della consumazione "decorata"
     * 
     * @return il prezzo della consumazione "decorata"
     */
    public double getPrice() {
        return consumationDecorator.getPrice()+3.0d;
    }
    
}
