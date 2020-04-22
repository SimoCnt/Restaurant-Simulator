package restaurant_simulator;

import java.security.InvalidParameterException;

/**
 * Classe Average Calculator, fornisce un calcolatore per la media tra n numeri
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class AverageCalculator implements IAverageCalculator{
    
    /**
     * Questo metodo consente di calcolare la media tra n numeri in una
     * specifica modalità
     * 
     * @param operation l'operazione di tipo IAverageOperation che permette di
     * calcolare la media in una specifica modalità
     */
    public void calculateAverage(IAverageOperation operation) {
        if(operation == null) {
            throw new InvalidParameterException("Operazione non consentita");
        }
        operation.averageOperation();
    }
}
