package restaurant_simulator;


/**
 * Questa interfaccia funzionale dichiara un metodo per l'esecuzione di un comando
 * in ManagerReceipt
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
@FunctionalInterface
public interface IManagerReceiptOperation {
    
    /**
     * Questo metodo permette di eseguire un comando di ManagerReceipt
     */
    public void execute();
}
