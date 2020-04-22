package restaurant_simulator;


/**
 * Questa interfaccia funzionale dichiara un metodo per l'esecuzione di un comando
 * in ManagerMenu
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
@FunctionalInterface
public interface IManagerMenuOperation {
    
    /**
     * Questo metodo permette di eseguire un comando di ManagerMenu
     */
    public void execute();
}
