package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Rappresenta l'ordine (prenotazione) di un cliente nel ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Order implements IRepositoryOperation {
    
    private final static String fileOrderPath = "data/Order.csv";
    
    /**
     * L'id dell'ordine
     */
    private int id;
    
    /**
     * L'id del cliente che ha effettuato l'ordine
     */
    private int userId;
    
    /**
     * L'id del tavolo assegnato al cliente
     */
    private int tableId;
    
    /**
     * La data in cui si svolge la consumazione
     */
    private Date data;

    /**
     * La lista dei prodotti finali associati all' ordine
     */
    private List<OrderItem> orderItems;
    
    /**
     * Il totale dell'ordine
     */
    private double totalPrice;
    
    /**
     * Pagamento contante
     */
    private double insertedCash;
    
    /**
     * Il resto
     */
    private double rest;
    
    /**
     * Stato dell'ordine (Pagato|Completato)
     */
    private IOrderState state;
    
    /**
     * Stato rilascio del feedback (true = rilasciato, false = non rilasciato)
     */
    private boolean feedbackReleased;
    
    /**
     * Stato data simulata (true = il completamento dell'ordine è stato simulato, false = data non simulata)
     */
    private boolean dateSimulated;
    
    
    /**
     * Questo metodo restituisce l'id dell'ordine
     * 
     * @return l'id dell'ordine
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id all'ordine
     * 
     * @param id l'id da assegnare all'ordine
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce l'id dell'utente che ha effettuato l'ordine
     * 
     * @return l'id dell'utente che ha effettuato l'ordine
     */
    public int getUserId() {
        return this.userId;
    }
    
    
    /**
     * Questo metodo permette di settare l'id dell'utente che ha effettuato l'ordine
     * 
     * @param userId l'id dell'utente da settare 
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    /**
     * Questo metodo permette di restituire la data in cui si svolge la consumazione
     * dell'ordine 
     * 
     * @return la data della consumazione
     */
    public Date getData() {
        return this.data;
    }
    
    
    /**
     * Questo metodo permette di settare la data in cui si svolge la consumazione
     * 
     * @param data la data da settare
     */
    public void setData(Date data) {
        this.data = data;
    }
    
    
    /**
     * Questo metodo restituisce l'id del tavolo assegnato al cliente per la
     * consumazione dell'ordine
     * 
     * @return l'id del tavolo assegnato al cliente per la consumazione dell'ordine
     */
    public int getTableId() {
        return this.tableId;
    }
    
    
    /**
     * Questo metodo permette si settare l'id del tavolo assegnato al cliente per la
     * consumazione dell'ordine
     * 
     * @param tableId l'id del tavolo da settare
     */
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei prodotti
     * 
     * @return la lista dei prodotti ordinati
     */
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei prodotti finali dell'ordine
     * 
     * @param orderItems la lista dei prodotti finali dell'ordine da settare
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    
    /**
     * Questo metodo restituisce il totale dell'ordine
     * 
     * @return il totale dell'ordine
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }
    
    
    /**
     * Questo metodo permette di settare il prezzo totale dell'ordine
     * 
     * @param totalPrice il prezzo totale dell'ordine da settare
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
    /**
     * Questo metodo restituisce il pagamento in contante effettuato dal cliente
     * 
     * @return il pagamento in contante effettuato dal cliente
     */
    public double getInsertedCash(){
        return this.insertedCash;
    }
    
    
    /**
     * Questo metodo permette di settare il pagamento in contanto effettuato dal cliente
     * 
     * @param insertedCash il pagamaneto in contante effettuato dal cliente da settare
     */
    public void setInsertedCash(double insertedCash){
        this.insertedCash = insertedCash;
    }
    
    
    /**
     * Questo metodo restituisce il resto dato al cliente in seguito al suo pagamento
     * 
     * @return il resto dato al cliente in seguito al suo pagamento
     */
    public double getRest(){
        return this.rest;
    }
    
    
    /**
     * Questo metodo permette di settare il resto dato al cliente in seguito al suo
     * pagamento
     * 
     * @param rest il resto da settare
     */
    public void setRest(double rest){
        this.rest = rest;
    }
    
    
    /**
     * Questo metodo restituisce lo stato dell'ordine
     * 
     * @return lo stato dell'ordine
     */
    public IOrderState getState() {
        return this.state;
    }
    
    
    /**
     * Questo metodo permette di settare lo stato dell'ordine
     * 
     * @param state lo stato dell'ordine da settare
     */
    public void setState(IOrderState state) {
        this.state = state;
    }

    
    /**
     * Questo metodo verifica se è stato rilasciato un feedback per l'ordine
     * 
     * @return true se il feedback è stato rilasciato, false altrimenti
     */
    public boolean isFeedbackReleased() {
        return this.feedbackReleased;
    }
    
    
    /**
     * Questo metodo permette di settare lo stato del feedback relativo all'ordine
     * 
     * @param feedbackReleased lo stato del feedback rilasciato da settare 
     */
    public void setFeedbackReleased(boolean feedbackReleased) {
        this.feedbackReleased = feedbackReleased;
    }
    
    
    /**
     * Questo metodo verifica se l'ordine (ovvero il suo completamento) è stato simulato
     * 
     * @return true se il completamento dell'ordine è stato simulato, false altrimenti
     */
    public boolean isDateSimulated() {
        return dateSimulated;
    }
    
    
    /**
     * Questo metodo permette di settare lo stato della simulazione dell'ordine
     * 
     * @param dateSimulated lo stato della simulazione da settare
     */
    public void setDateSimulated(boolean dateSimulated) {
        this.dateSimulated = dateSimulated;
    }
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo agli Ordini
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileOrderPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getUserId()));
                line.add(String.valueOf(this.getTableId()));
                line.add(DateUtility.formatDateWithTime(this.getData()));
                line.add(String.valueOf(this.getTotalPrice()));
                line.add(String.valueOf(this.getState()));
                line.add(String.valueOf(this.getInsertedCash()));
                line.add(String.valueOf(this.getRest()));
                line.add(String.valueOf(this.isFeedbackReleased()));
                line.add(String.valueOf(this.isDateSimulated()));
                CsvUtility.addLineToCsv(line, fileOrderPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileOrderPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo agli ordini
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileOrderPath);
            File oldFile = new File(fileOrderPath);
            File newFile = new File("newOrderPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;UserId;TableId;Date;Total;State;InsertedImport;Rest;ReleasedFeedback;DateSimulated\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getUserId()));
                    line.add(String.valueOf(this.getTableId()));
                    line.add(String.valueOf(DateUtility.formatDateWithTime(this.getData())));
                    line.add(String.valueOf(this.getTotalPrice()));
                    line.add(String.valueOf(this.getState()));
                    line.add(String.valueOf(this.getInsertedCash()));
                    line.add(String.valueOf(this.getRest()));
                    line.add(String.valueOf(this.isFeedbackReleased()));
                    line.add(String.valueOf(this.isDateSimulated()));
                    CsvUtility.addLineToCsv(line, "newOrderPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newOrderPath.csv");
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
