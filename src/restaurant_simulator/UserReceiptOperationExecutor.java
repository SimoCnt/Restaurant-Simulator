package restaurant_simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe invocatore dei comandi per UserReceipt
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserReceiptOperationExecutor {
    
    /**
     * La lista delle operazioni di UserReceipt
     */
    private final List<IUserReceiptOperation> userReceiptOperations = new ArrayList<>();
    
    
    /**
     * Questo metodo permette di eseguire il comando passato a parametro
     * 
     * @param userReceiptOperation l'operazione\comando che deve essere eseguito
     */
    public void executeOperation(IUserReceiptOperation userReceiptOperation) {
        this.userReceiptOperations.add(userReceiptOperation);
        userReceiptOperation.execute();
    }
}
