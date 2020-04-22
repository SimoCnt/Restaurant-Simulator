package restaurant_simulator;

/**
 * Classe cinque centesimi handler
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class FiveCentHandler implements IBanknoteHandler {
    
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
        if (banknote.getValue()>=0.05) {
            // effettuo l'arrotondamento per ricavarmi il corretto valore della banconota
            double valore = (double) Math.round((banknote.getValue()-0.05)*100)/100;
            banknote.setValue(valore);
            banknote.addBanknote("5 centesimi");
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
