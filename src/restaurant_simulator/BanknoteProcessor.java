package restaurant_simulator;

/**
 * Classe client che contiene un processor per la catena delle richieste handler
 * delle banconote
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class BanknoteProcessor {
    
    /**
     * Handler della banconota corrente
     */
    private IBanknoteHandler currentBanknoteHandler;
    
    
    /**
     * Aggiunge un handler successore all'handler corrente
     * 
     * @param newBanknoteHandler l'handler di una banconota
     */
    public void addHandler(IBanknoteHandler newBanknoteHandler) {
        if (currentBanknoteHandler != null) {
            newBanknoteHandler.setNext(currentBanknoteHandler);
        }
        currentBanknoteHandler = newBanknoteHandler;
    }
    
    
    /**
     * Processa la banconota passandola all'opportuno handler
     * 
     * @param banknote la banconota che deve essere passata alla richiesta handler
     */
    public void process(Banknote banknote) {
        currentBanknoteHandler.handlerRequest(banknote);
    }
 
}
