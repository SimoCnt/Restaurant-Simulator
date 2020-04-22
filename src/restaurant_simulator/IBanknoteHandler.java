package restaurant_simulator;

/**
 * Interfaccia IBanknoteHandler, fornisce i metodi relativi al settaggio del 
 * successivo handler e all'elaborazione della richiesta
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IBanknoteHandler {
    /**
     * Setta il successivo handler della richiesta
     * 
     * @param handler l'handler da settare come successore
     */
    public void setNext(IBanknoteHandler handler);
    
    /**
     * Elabora la richiesta del corrente handler
     * 
     * @param banknote la banconota da elaborare
     */
    public void handlerRequest(Banknote banknote);  
}
