package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Feedback: rappresenta un feedback rilasciato da un utente per una
 * certa categoria
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Feedback implements IRepositoryOperation {
    
    private final static String fileFeedbackPath = "data/Feedback.csv";
    
    /**
     * L'id del feedback rilasciato
     */
    private int id;
    
    /**
     * L'id della categoria a cui si riferisce il feedback
     */
    private int categoryId;
    
    /**
     * L'id dell'ordine per cui è stato rilasciato il feedback
     */
    private int orderId;
    
    /**
     * L'id dell'utente che ha rilasciato il feedback
     */
    private int userId;
    
    /**
     * Il valore (punteggio) del feedback
     */
    private double value;
    
        
    /**
     * Costruttore senza parametri della classe Feedback
     */
    public Feedback(){};
    
    
    /**
     * Questo metodo restituisce l'id del feedback rilasciato dall'utente
     * 
     * @return l'id del feedback rilasciatpo dall'utente
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id del feedback rilasciato dall'utente
     * 
     * @param id l'id del feedback rilasciato dall'utente
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Questo metodo restituisce l'id dell'ordine a cui di riferisce il feedback
     * 
     * @return l'id dell'ordine a cui si riferisce il feedback
     */
    public int getOrderId() {
        return orderId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id dell'ordine a cui si riferisce il
     * feedback
     * 
     * @param orderId l'id dell'ordine a cui si riferisce il feedback da settare
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    
    /**
     * Questo metodo restituisce l'id dell'utente che ha rilasciato il feedback
     * 
     * @return l'id dell'utente che ha rilasciato il feedback
     */
    public int getUserId() {
        return userId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id dell'utente che ha rilasciato il
     * feedback
     * 
     * @param userId l'id dell'utente che ha rilasciato il feedback da settare
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    

    /**
     * Questo metodo restituisce l'id della categoria a cui si riferisce il feedback
     * 
     * @return l'id della categoria a cui si riferisce il feedback
     */
    public int getCategoryId() {
        return categoryId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id della categoria a cui si riferisce il feedback
     * 
     * @param categoryId l'id della categoria a cui si riferisce il feedback da settare
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    
    /**
     * Questo metodo restituisce il valore (punteggio) attribuito al feedback
     * 
     * @return il valore (punteggio) attribuito al feedback
     */
    public double getValue() {
        return value;
    }
    
    
    /**
     * Questo metodo permette di settare il valore (punteggio) attribuito al feedback
     * 
     * @param value il valore (punteggio) attribuito al feedback da settare
     */
    public void setValue(double value) {
        this.value = value;
    }
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo ai Feedback
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileFeedbackPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getCategoryId()));
                line.add(String.valueOf(this.getOrderId()));
                line.add(String.valueOf(this.getUserId()));
                line.add(String.valueOf(this.getValue()));
                CsvUtility.addLineToCsv(line, fileFeedbackPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileFeedbackPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo ai Feedback
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileFeedbackPath);
            File oldFile = new File(fileFeedbackPath);
            File newFile = new File("newFeedbackPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;CategoryFeedbackId;OrderId;UserId;Value\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getCategoryId()));
                    line.add(String.valueOf(this.getOrderId()));
                    line.add(String.valueOf(this.getUserId()));
                    line.add(String.valueOf(this.getValue()));
                    CsvUtility.addLineToCsv(line, "newFeedbackPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newFeedbackPath.csv");
                }
            }
            oldFile.delete();
            newFile.renameTo(oldFile); 
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
     }
}
