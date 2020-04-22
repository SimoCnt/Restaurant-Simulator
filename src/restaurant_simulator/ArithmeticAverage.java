package restaurant_simulator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe ArithmeticAverage: questa classe gestisce il calcolo della media
 * aritmetica tra n numeri
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ArithmeticAverage implements IAverageOperation {
    
    /**
     * La lista dei valori per la quale deve essere calcolata la media
     */
    public List<Double> values;
    
    /**
     * Il risultato della media aritmetica tra n numeri
     */
    public double result;
    
    
    /**
     * Costruttore con parametri della classe ArithmeticAverage
     * 
     * @param values la lista dei valori per la quale deve essere calcolata la media
     */
    public ArithmeticAverage(List<Double> values) {
        this.values = values;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei valori per la quale deve essere calcolata
     * la media
     * 
     * @return la lista dei valori per la quale deve essere calcolata la media
     */
    public List<Double> getValues() {
        return values;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei valori per la quale deve essere
     * calcolata la media
     * 
     * @param values la lista dei valori per la quale deve essere calcolata la media
     */
    public void setValues(List<Double> values) {
        this.values = values;
    }
    
    
    /**
     * Questo metodo restituisce il risultato della media aritmetica tra n numeri
     * 
     * @return il risultato della media aritmetica tra n numeri
     */
    public double getResult() {
        return result;
    }
    
    
    /**
     * Questo metodo permette di settare il risultato della media aritmetica tra n numeri
     * 
     * @param result il risultato della media aritmetica tra n numeri
     */
    public void setResult(double result) {
        this.result = result;
    }
    
    
    /**
     * Questo metodo effettua il calcolo della media aritmetica tra gli n valori di {@link #values}
     */
    public void averageOperation() {
        this.result = values.stream().collect(Collectors.summarizingDouble(Double::doubleValue)).getAverage();
    }
}
