package restaurant_simulator;

/**
 * Interfaccia relativa ad una consumazione
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IConsumation {
    
    /**
     * Restituisce il nome della consumazione
     * 
     * @return il nome della consumazione
     */
    public String getName();
    
    
    /**
     * Restituisce il prezzo della consumazione
     * 
     * @return il prezzo della consumazione
     */
    public double getPrice();
}
