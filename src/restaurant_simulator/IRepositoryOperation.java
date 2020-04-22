package restaurant_simulator;

/**
 * Interfaccia che dichiara le principali operazioni per la modifica del file
 * CSV relativo ad un entità
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IRepositoryOperation {
    
    /**
     * Questo metodo permette di appendere un record su un file CSV
     * 
     * @return true se il record viene aggiunto al file, false altrimenti
     */
    public boolean addRecord();
    
    
    /**
     * Questo metodo permette di sostituire un record di un file CSV con i valori
     * ricavati dall'oggetto passato a parametro
     * 
     * @return true se l'oggetto viene sostituito, false altrimenti
     */
    public boolean updateRecord();
}
