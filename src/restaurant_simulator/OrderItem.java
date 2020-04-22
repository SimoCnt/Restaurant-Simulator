package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Rappresenta il prodotto finale (prodotto con o senza personalizzazione) 
 * associato ad un ordine
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class OrderItem implements IRepositoryOperation{
    
    private final static String fileOrderItemPath = "data/OrderItem.csv";
    
    /**
     * L'id dell'ordine a cui è associato il prodotto
     */
    private int orderId;
    
    /**
     * L'id del prodotto base (ovvero quello senza personalizzazioni) associato all'ordine
     */
    private int productId;

    /**
     * Il nome del prodotto finale associato all'ordine
     */
    private String productName;
    
    /**
     * Il prezzo del prodotto finale associato all'ordine
     */
    private double price;
    
    
    /**
     * Questo metodo restituisce l'id dell'ordine associato al prodotto finale dell'ordine
     * 
     * @return l'id dell'ordine associato al prodotto finale dell'ordine
     */
    public int getOrderId() {
        return this.orderId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id dell'ordine associato al prodotto
     * finale
     * 
     * @param orderId  l'id dell'ordine associato al prodotto finale da settare
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    
    /**
     * Questo metodo restituisce l'id del prodotto base associato all'ordine
     * 
     * @return l'id del prodotto base
     */
    public int getProductId() {
        return productId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id del prodotto base associato all'ordine
     * 
     * @param productId  l'id del prodotto base associato all'ordine
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
    /**
     * Questo metodo restituisce il nome del prodotto finale associato all'ordine
     * 
     * @return il nome del prodotto finale
     */
    public String getProductName() {
        return this.productName;
    }
    
    
    /**
     * Questo metodo permette di settare il nome del prodotto finale associato
     * all'ordine
     * 
     * @param productName il nome del prodotto finale da settare
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
    /**
     * Questo metodo restituisce il prezzo del prodotto finale
     * 
     * @return il prezzo del prodotto finale
     */
    public double getPrice() {
        return this.price;
    }
    
    
    /**
     * Questo metodo permette di settare il prezzo del prodotto finale associato
     * all'ordine
     * 
     * @param price il prezzo del prodotto finale da settare
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo agli item degli Ordini
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileOrderItemPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getOrderId()));
                line.add(String.valueOf(this.getProductId()));
                line.add(String.valueOf(this.getProductName()));
                line.add(String.valueOf(this.getPrice()));
                
                CsvUtility.addLineToCsv(line, fileOrderItemPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileOrderItemPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo agli item degli Ordini
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileOrderItemPath);
            File oldFile = new File(fileOrderItemPath);
            File newFile = new File("newOrderItemPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;ProductId;FinalProductName;Price");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getOrderId()) {
                    line.add(String.valueOf(this.getOrderId()));
                    line.add(String.valueOf(this.getProductId()));
                    line.add(String.valueOf(this.getProductName()));
                    line.add(String.valueOf(this.getPrice()));
                    CsvUtility.addLineToCsv(line, "newOrderItemPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newOrderItemPath.csv");
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
