package restaurant_simulator;

/**
 * Interfaccia IAverageCalculator, fornisce un metodo per il calcolo della media
 * tra n numeri attraverso una modalità di tipo IAverageOperation
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IAverageCalculator {
    
    /**
     * Questo metodo consente di calcolare la media tra n numeri in una
     * specifica modalità
     * 
     * @param operation l'operazione di tipo IAverageOperation che permette di
     * calcolare la media in una specifica modalità
     */
    public void calculateAverage(IAverageOperation operation);
}
