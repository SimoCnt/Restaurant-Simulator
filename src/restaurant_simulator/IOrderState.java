package restaurant_simulator;

/**
 * Interfaccia IOrderState, dichiara i metodi che permettono di cambiare 
 * lo stato di un ordine
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IOrderState {
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in Completato
     * quando la data di consumazione di un ordine diventa minore della data
     * attuale. 
     * 
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsLessThanCurrentDate(Order context);
    
    
    /**
     * Questo metodo permette di settare lo stato di un ordine in pagato
     * quando la data di consumazione di un ordine diventa maggiore della data
     * attuale. 
     * 
     * @param context un'istanza di tipo Order
     */
    public void orderDateIsBiggerThanCurrentDate(Order context);
}
