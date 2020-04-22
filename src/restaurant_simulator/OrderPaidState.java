package restaurant_simulator;


/**
 * Classe OrderPaidState, rappresenta lo stato Pagato dell'ordine
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class OrderPaidState implements IOrderState {
    
    /**
     * La descrizione dello stato Pagato dell'ordine
     */
    private final String paid = "Pagato";
    
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in Completato
     * quando la data di consumazione di un ordine diventa minore della data
     * attuale. 
     *  
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsLessThanCurrentDate(Order context) {
        context.setState(new OrderCompletedState());
    }
    
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in pagato
     * quando la data di consumazione di un ordine diventa maggiore della data
     * attuale. 
     * 
     * Nota: In questa classe questo metodo non ha un implementazione significativa
     * 
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsBiggerThanCurrentDate(Order context) {}
    
    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto OrderPaidState, 
     * sotto forma di stringa. In particolare lo stato pagato dell'ordine.
     * 
     * @return la descrizione dell'oggetto OrderPaidState, ovvero lo stato pagato
     * dell'ordine
     */
    @Override
    public String toString() {
        return this.paid;
    }
    
}
