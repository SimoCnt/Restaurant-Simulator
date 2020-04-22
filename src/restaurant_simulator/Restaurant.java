package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rappresenta un ristornante con i suoi componenti
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Restaurant implements IRepositoryOperation {
    
    private final static String fileRestaurantInfoPath = "data/RestaurantInfo.csv";
    
    /**
     * L'id del ristornate
     */
    private int id;
    
    /**
     * Il nome del ristorante
     */
    private String name;
    
    /**
     * La lista dei tavoli del ristorante
     */
    private List<Table> tables;
    
    /**
     * Il menu del ristorante
     */
    private Menu menu;
    
    /**
     * La lista dei feedback del ristorante
     */
    private List<CategoryFeedback> feedback;
    
    /**
     * La lista di clienti del ristorante
     */
    private List<User> users;
    
    /**
     * La lista di ordini del ristorante
     */
    private List<Order> orders;
    
    /**
     * Costruttore senza parametri della classe Restaurant
     */
    public Restaurant() {}
    
    
    /**
     * Costruttore con parametri della classe Restaurant
     * 
     * @param name il nome del ristorante
     */
    public Restaurant(String name) {
        this.name = name;
    }
    
    
    /**
     * Altro costruttore con parametri della classe Restaurant
     * 
     * @param name il nome del ristorante
     * @param tables la lista dei tavoli
     */
    public Restaurant(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }
    
    
    /**
     * Questo metodo restituisce l'id del ristorante
     * 
     * @return l'id del ristorante
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id del ristorante
     * 
     * @param id l'id del ristorante
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce il nome del ristorante
     * 
     * @return il nome del ristorante
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * Questo metodo permette di settare il nome del ristorante
     * 
     * @param name il nome del ristorante
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Questo metodo restituisce la lista dei tavoli del ristorante
     * 
     * @return la lista dei tavoli del ristorante
     */
    public List<Table> getTables() {
        return this.tables;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei tavoli del ristorante
     * 
     * @param tables la lista dei tavoli del ristorante
     */
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
    
    
    /**
     * Questo metodo restituisce il menu del ristorante
     * 
     * @return il menu del ristorante
     */
    public Menu getMenu() {
        return this.menu;
    }
    
    
    /**
     * Questo metodo permette di settare il menu del ristorante
     * 
     * @param menu il menu del ristorante
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei feedback per categoria assegnati al ristorante
     * 
     * @return la lista dei feedback per categoria assegnati al ristorante
     */
    public List<CategoryFeedback> getFeedback() {
        return feedback;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei feedback per categoria assegnati al ristorante
     * 
     * @param feedback la lista dei feedback per categoria assegnati al ristorante
     */
    public void setFeedback(List<CategoryFeedback> feedback) {
        this.feedback = feedback;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei clienti del ristorante
     * 
     * @return la lista dei clienti del ristorante
     */
    public List<User> getUsers() {
        return users;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei clienti del ristorante
     * 
     * @param users la lista dei clienti del ristorante
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
    /**
     * Questo metodo restituisce la lista degli ordini del ristorante
     * 
     * @return la lista degli ordini del ristorante
     */
    public List<Order> getOrders() {
        return orders;
    }
    
    
    /**
     * Questo metodo permette di settare la lista degli ordini del ristorante
     * 
     * @param orders la lista degli ordini del ristorante
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    
    /**
     * Questo metodo permette di restituire un cliente del ristorante in base all'id
     * 
     * @param userId l'id del cliente da restituire
     * @return il cliente corrispondente all'id passato a parametro
     */
    public User getUserById(int userId) {
        return this.users.stream().filter(o -> o.getId() == userId).findFirst().get();
    }
    
    
    /**
     * Questo metodo permette di restituire un tavolo del ristorante in base all'id
     * 
     * @param tableId l'id del tavolo da restituire
     * @return il cliente corrispondente all'id passato a parametro
     */
    public Table getTableById(int tableId) {
        return this.tables.stream().filter(o -> o.getId() == tableId).findFirst().get();
    }
    
    
    /**
     * Questo metodo permette di restituire la lista degli ordini associati ad un dato 
     * cliente
     * 
     * @param userId l'id del cliente da cui ricavare gli ordini
     * @return la lista degli ordinic corrispondenti all'id del cliente passato a parametro
     */
    public List<Order> getUserOrdersByUserId(int userId) {
        return this.orders.stream().filter(o -> o.getUserId()== userId).collect(Collectors.toList());
    }
    
    /**
     * Questo metodo permette di restituire il cliente che ha prenotato un dato in una
     * data specifica
     * 
     * @param tableId l'id del tavolo occupato
     * @param dateSelected la data in cui risulta occupato il tavolo
     * @return il cliente che ha prenotato un dato in una data specifica
     */
    public User getUserWithTableReservationForSpecificDate(int tableId, Date dateSelected) {
        for (Iterator<Order> it=this.orders.iterator(); it.hasNext();) {
            Order order = it.next();
            if(DateUtility.formatDateWithTime(order.getData()).equals(DateUtility.formatDateWithTime(dateSelected)) && order.getTableId()==tableId) {
                return getUserById(order.getUserId());
            }
        }
        return null;
    }
    
    
    /**
     * Questo metodo permette di verifica lo stato di un tavolo (libero o occupato)
     * per una certa data
     * 
     * @param idTable l'id del tavolo da cui ricavare lo stato
     * @param dateSelected la data in cui controllare il tavolo
     * @return true se il tavolo è disponibile per la data passata a parametro, false altrimenti
     */
    public boolean checkTableStatus(int idTable, Date dateSelected){
        for(int i=0; i<this.orders.size(); i++){
            // Se trovo ordini con idTable e dateSelected uguali il tavolo risulta occupato
            if(this.orders.get(i).getTableId() == idTable && this.orders.get(i).getData().compareTo(dateSelected)==0){
                return false;
            }
        }
        return true;
    }    

    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo ai Clienti
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileRestaurantInfoPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getName()));
                CsvUtility.addLineToCsv(line, fileRestaurantInfoPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileRestaurantInfoPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo ai Clienti
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileRestaurantInfoPath);
            File oldFile = new File(fileRestaurantInfoPath);
            File newFile = new File("newRestaurantInfoPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;Name\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getName()));
                    CsvUtility.addLineToCsv(line, "newRestaurantInfoPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newRestaurantInfoPath.csv");
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
