package restaurant_simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe ricevitori dei comandi per MenuReceipt
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerReceiptOperationExecutor {
    
    /**
     * La lista delle operazioni di ManagerReceipt
     */
    private final List<IManagerReceiptOperation> managerReceiptOperations = new ArrayList<>();
    
    
    /**
     * Questo metodo permette di eseguire il comando passato a parametro
     * 
     * @param managerReceiptOperation l'operazione\comando che deve essere eseguito
     */
    public void executeOperation(IManagerReceiptOperation managerReceiptOperation) {
        this.managerReceiptOperations.add(managerReceiptOperation);
        managerReceiptOperation.execute();
    }
}
