package restaurant_simulator;

/**
 * Classe astratta ExtraAddictionDecorator: rappresenta l'astrazione per effettuare
 * la "decorazione" delle consumazioni 
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public abstract class ExtraAddictionDecorator implements IConsumation {
    
    /**
     * Un'istanza di tipo Consumazione
     */
    protected IConsumation consumationDecorator;
    
    
    /**
     * 
     * @param consumationDecorator un'istanza di tipo IConsumation
     */
    public ExtraAddictionDecorator(IConsumation consumationDecorator) {
        super();
        this.consumationDecorator = consumationDecorator;
    }
}
