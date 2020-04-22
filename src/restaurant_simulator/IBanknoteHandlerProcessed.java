package restaurant_simulator;

import java.util.Stack;

/**
 * Interfaccia per le banconote processate dal BanknoteProcessor
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface IBanknoteHandlerProcessed {
    /**
     * Restuisce la pila dell'insieme delle banconote processate dal BanknoteProcessor
     * 
     * @return la pila dell'insieme delle banconote processate dal BanknoteProccesor
     */
    public Stack<String> getBanknote();
    
    /**
     * Aggiunge una banconota in formato stringa (es. 5 euro) nella pila banknotes
     * 
     * @param banknoteString la banconota in formato stringa da inserire nella pila
     */
    public void addBanknote(String banknoteString);
}
