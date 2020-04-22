package restaurant_simulator;

/**
 * Questa classe rappresenta la concretizzazione del "decoratore"
 * ExtraAddictionDecorator in relazione agli Antipasti
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class StarterDecorator extends ExtraAddictionDecorator {
    
   /**
    * Un'istanza della lista degli ingredienti decoratori associati agli antipasti
    */
    protected StarterIngredients starterIngredient;
    
    
    /**
     * Costruttore della classe StarterDecorator
     * 
     * @param decorateConsumation la consumazione da decorare
     * @param starterIngredient gli ingredienti degli Antipasti che figurano come decoratori della consumazione
     */
    public StarterDecorator(IConsumation decorateConsumation, StarterIngredients starterIngredient) {
        super(decorateConsumation);
        this.starterIngredient = starterIngredient;
    }
    
    
    /**
     * Questo metodo restituisce il nome della consumazione "decorata"
     * 
     * @return il nome della consumazione "decorata"
     */
    public String getName() {
        return consumationDecorator.getName() + " con aggiunta di: " + starterIngredient;
    }
    
    
    /**
     * Questo metodo restituisce il prezzo della consumazione "decorata"
     * 
     * @return il prezzo della consumazione "decorata"
     */
    public double getPrice() {
        return consumationDecorator.getPrice()+1.0d;
    }
    
}
