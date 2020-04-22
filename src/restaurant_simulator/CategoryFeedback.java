package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe CategoryFeedback, rappresenta la categoria dei feedback 
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class CategoryFeedback implements IRepositoryOperation {
    
    private final static String fileCategoryFeedbackPath = "data/CategoryFeedback.csv";
    
    /**
     * L'id della categoria dei feedback
     */
    private int id;
    
    /**
     * Il nome della categoria dei feedback
     */
    private String name;
    
    /**
     * La lista dei feedback associati alla categoria
     */
    private List<Feedback> feedback = new ArrayList<>();
    
    /**
     * Costruttore senza parametri della classe CategoryFeedback
     */
    public CategoryFeedback(){};
    
    
    /**
     * Costruttore con parametri della classe CategoryFeedback
     * 
     * @param id l'id della categoria dei feedback
     */
    public CategoryFeedback(int id) {
        this.id = id;
    }
    
    
    /**
     * Altro costruttore con parametri della classe CategoryFeedback
     * 
     * @param id l'id della categoria dei feedback
     * @param name il nome della categoria dei feedback
     */
    public CategoryFeedback(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    /**
     * Altro costruttore con parametri della classe CategoryFeedback
     * 
     * @param id l'id della categoria dei Feedback
     * @param name il nome della categoria dei feedback
     * @param feedback la lista dei feedback associati alla categoria
     */
    public CategoryFeedback(int id, String name, List<Feedback> feedback) {
        this.id = id;
        this.name = name;
        this.feedback = feedback;
    }
    
    
    /**
     * Questo metodo restituisce l'id della categoria dei feedback
     * 
     * @return l'id della categoria dei feedback
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id della categoria dei feedback
     * 
     * @param id l'id della categoria dei feedback
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce il nome della categoria dei feedback
     * 
     * @return il nome della categoria dei feedback
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Questo metodo permette di settare il nome della categoria dei feedback
     * 
     * @param name il nome della categoria dei feedback
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei feedback associati alla categoria
     * 
     * @return la lista dei feedback associati alla categoria
     */
    public List<Feedback> getFeedback() {
        return feedback;
    }

    
    /**
     * Questo metodo permette di settare la lista dei feedback associati alla categoria
     * 
     * @param feedback la lista dei feedback associati alla categoria
     */
    public void setFeedback(List<Feedback> feedback) {
        this.feedback = feedback;
    }
    
    /**
     * Questo metodo permette di appendere un record nel file relativo alle Categorie dei Feedback
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileCategoryFeedbackPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getName()));
                CsvUtility.addLineToCsv(line, fileCategoryFeedbackPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileCategoryFeedbackPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo alle Categorie dei Feedback
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileCategoryFeedbackPath);
            File oldFile = new File(fileCategoryFeedbackPath);
            File newFile = new File("newCategoryFeedbackPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;Name\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getName()));
                    CsvUtility.addLineToCsv(line, "newCategoryFeedbackPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newCategoryFeedbackPath.csv");
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
