package restaurant_simulator;

import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;


/**
 * Classe che permette di stampare il contenuto di una JTextPane tramite stampante
 * 
 * @author Danilo Dolce e Simone Contini
 */
public class PrintJTextPaneWithPrinter implements IPrintJTextComponent {
    
    /**
     * Il JTextPane su cui deve essere eseguito il comando
     */
    protected JTextPane textPane;
    
    
    /**
     * Costruttore senza parametri della classe PrintJTextPaneThroghPrinter
     */
    public PrintJTextPaneWithPrinter() {}
    
    /**
     * Costruttore con parametro della classe PrintTextAre
     * 
     * @param textPane l'istanza di una JTextArea
     */
    public PrintJTextPaneWithPrinter(JTextPane textPane) {
        this.textPane = textPane;
    }
    
    public void printJTextComponent() {
        try {
            textPane.print();
        } catch(PrinterException e) {
            JOptionPane.showMessageDialog(textPane, "Impossibile effettuare la stampa!");
        }
    }


}
