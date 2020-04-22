package restaurant_simulator;

import java.util.Stack;

/**
 * Classe Banknote, rappresenta una banconota
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Banknote implements IBanknoteHandlerProcessed {
    
    /**
     * La pila delle banconote processate
     */
    private Stack<String> banknotes;
    
    /**
     * Il valore attuale della banconota
     */
    protected double value;
    
    
    /**
     * Costruttore della classe Banknote
     * 
     * @param value il valore della banconota
     */
    public Banknote(double value) {
        this.value = value;
        banknotes = new Stack<>();
    }

    
    /**
    * Ritorna il valore della banconota
    * 
    * @return il valore della banconota
    */
    public double getValue() {
        return value;
    }

    
    /**
    * Setta un nuovo valore per la variabile valore
    * 
    * @param value il valore da assegnare alla banconota
    */
    public void setValue(double value) {
            this.value = value;
    }
    
    
    /**
     * Restuisce la pila dell'insieme delle banconote processate dal BanknoteProcessor
     * 
     * @return la pila dell'insieme delle banconote processate dal BanknoteProccesor
     */
    public Stack<String> getBanknote() {
        return this.banknotes;
    }
    
    
    /**
     * Aggiunge una banconota in formato stringa (es. 5 euro) nella pila banknotes
     * 
     * @param banknoteString la banconota in formato stringa da inserire nella pila
     */
    public void addBanknote(String banknoteString) {
        this.banknotes.push(banknoteString);
    }
    
    
}
