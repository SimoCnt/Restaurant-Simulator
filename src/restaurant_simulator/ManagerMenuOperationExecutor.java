package restaurant_simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe ricevitori dei comandi per MenuReceipt
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerMenuOperationExecutor {
    
    /**
     * La lista delle operazioni di ManagerMenu
     */
    private final List<IManagerMenuOperation> managerMenuOperations = new ArrayList<>();
    
    
    /**
     * Questo metodo permette di eseguire il comando passato a parametro
     * 
     * @param managerMenuOperation  l'operazione\comando che deve essere eseguito
     */
    public void executeOperation(IManagerMenuOperation managerMenuOperation) {
        this.managerMenuOperations.add(managerMenuOperation);
        managerMenuOperation.execute();
    }
}
