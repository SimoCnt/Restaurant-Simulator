package restaurant_simulator;

/**
 * Classe OrderPaidState, rappresenta lo stato Completato dell'ordine
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class OrderCompletedState implements IOrderState {
    private final String completed = "Completato";
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in Completato
     * quando la data di consumazione di un ordine diventa minore della data
     * attuale. 
     * 
     * Nota: In questa classe questo metodo non ha un implementazione significativa
     * 
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsLessThanCurrentDate(Order context) {}
    
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in pagato
     * quando la data di consumazione di un ordine diventa maggiore della data
     * attuale. 
     * 
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsBiggerThanCurrentDate(Order context) {
        context.setState(new OrderPaidState());
    }
    
    
    /**
     * Questo metodo restituisce la descrizione dell'oggetto OrderCompletedState, 
     * sotto forma di stringa. In particolare lo stato pagato dell'ordine.
     * 
     * @return la descrizione dell'oggetto OrderCompletedState, ovvero lo stato completato
     * dell'ordine
     */
    @Override
    public String toString() {
        return this.completed;
    }
}
