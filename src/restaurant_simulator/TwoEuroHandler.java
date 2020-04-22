package restaurant_simulator;

/**
 * Classe due euro handler
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class TwoEuroHandler implements IBanknoteHandler {
    
    /**
     * Handler successivo
     */
    private IBanknoteHandler next;
    
    
    /**
     * Setta il successivo handler della richiesta
     * 
     * @param handler l'handler da settare come successore
     *
     */
    public void setNext(IBanknoteHandler handler) {
        next = handler;
    }
    
    
    /**
     * Elabora la richiesta del corrente handler
     * 
     * @param banknote la banconota da elaborare
     */
    public void handlerRequest(Banknote banknote) {
        if (banknote.getValue()>=2) {
            double valore = (double) Math.round((banknote.getValue()-2)*100)/100;
            banknote.setValue(valore);
            banknote.addBanknote("2 euro");
            if (banknote.getValue()>0)
                handlerRequest(banknote);
        }
        else {
            if (next != null) {
                next.handlerRequest(banknote);
            }
        }
    }
}
