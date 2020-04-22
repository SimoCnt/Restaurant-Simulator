package restaurant_simulator;


/**
 * Questa interfaccia funzionale dichiara un metodo per l'esecuzione di un comando
 * in UserReceipt
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
@FunctionalInterface
public interface IUserReceiptOperation {
    
    /**
     * Questo metodo permette di eseguire un comando di UserReceipt
     */
    public void execute();
}
