package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Rappresenta un tavolo del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Table implements IRepositoryOperation{
    
    private final static String fileTablePath = "data/Table.csv";
    
    // *** Attributi ***
    
    /**
     * L'id del tavolo
     */
    private int id;
    
    /**
     * Il nome identificativo del tavolo
     */
    private String name;
    
    /**
     * Il numero di posti del tavolo
     */
    private int numSeats;
    
    
    /**
     * Questo metodo restituisce l'id del tavolo
     * 
     * @return l'id del tavolo
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Questo metodo permette di assegnare un id al tavolo
     * 
     * @param id l'id del tavolo
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce il nome del tavolo
     * 
     * @return il nome del tavolo
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * Questo metodo permette di assegnare un nome al tavolo
     * 
     * @param name il nome del tavolo
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Questo metodo restituisce il numero di posti del tavolo
     * 
     * @return il numero di posti del tavolo
     */
    public int getNumSeats() {
        return this.numSeats;
    }
    
    
    /**
     * Questo metodo permette di assegnare il numero di posti del tavolo
     * 
     * @param numSeats il numero di posti del tavolo
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo ai Tavoli
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileTablePath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getName()));
                line.add(String.valueOf(this.getNumSeats()));
                CsvUtility.addLineToCsv(line, fileTablePath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileTablePath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo ai Tavoli
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileTablePath);
            File oldFile = new File(fileTablePath);
            File newFile = new File("newTablePath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;Name;NumberSeats\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(this.getName());
                    line.add(String.valueOf(this.getNumSeats()));
                    CsvUtility.addLineToCsv(line, "newTablePath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newTablePath.csv");
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
