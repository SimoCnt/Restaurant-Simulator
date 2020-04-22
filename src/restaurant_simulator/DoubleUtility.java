package restaurant_simulator;

/**
 * Classe DoubleUtility, fornisce dei metodi Utility per il tipo Double
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class DoubleUtility {
    
    /**
     * Questo metodo permette di arrontondare un double e di restituirne solo
     * le cifre decimali richieste
     * 
     * @param value il valore da arrotondare
     * @param places il numero di cifre decimali da ottenere
     * @return il double passato a parametro, arrotondato e con n cifre decimali
     * 
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    
}
