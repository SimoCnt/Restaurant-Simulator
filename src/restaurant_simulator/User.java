package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rappresenta un cliente del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class User implements IRepositoryOperation {
        
    private final static String fileUserPath = "data/User.csv";
    
    /**
     * L'id del cliente
     */
    private int id;
    
    /**
     * L'username del cliente
     */
    private String username;
    
    /**
     * L'email del cliente
     */
    private String email;
    
    /**
     * La password del cliente
     */
    private String password;
    
    /**
     * La lista degli ordini associati al cliente
     */
    private List<Order> orders = new ArrayList<>();
    
    
    /**
     * Costruttore senza parametri della classe User
     */
    public User() {}
    
    
    /**
     * Questo metodo restituisce l'id del cliente
     * 
     * @return l'id del cliente
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id del cliente
     * 
     * @param id l'id del cliente
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce l'username del cliente
     * 
     * @return l'username del cliente
     */
    public String getUsername() {
        return this.username;
    }
    
    
    /**
     * Questo metodo permette di settare l'username del cliente
     * 
     * @param username l'username del cliente
     */
    public void setUsername(String username) {
        this.username = username;
    }
    

    /**
     * Questo metodo restituisce l'email del cliente
     * 
     * @return l'email del cliente
     */
    public String getEmail() {
        return this.email;
    }
    
    
    /**
     * Questo metodo permette di settare l'email del cliente
     * 
     * @param email l'email del cliente
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    /**
     * Questo metodo restituisce la password del cliente
     * 
     * @return la password del cliente
     */
    public String getPassword() {
        return this.password;
    }
    
    
    /**
     * Questo metodo permette di settare la password del cliente
     * 
     * @param password la password del cliente
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * Questo metodo restituisce la lista degli ordini associati al cliente
     * 
     * @return la lista degli ordini associati al cliente
     */
    public List<Order> getOrders(){
        return this.orders;
    }
    

    /**
     * Questo metodo permette di settare gli ordini associati al cliente
     * 
     * @param orders gli ordini associati al cliente
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    
    /**
     * Questo metodo permette di restituire un ordine dell'utente dato l'id
     * 
     * @param id l'id dell'ordine
     * @return l'ordine dell'utente dato l'id
     */
    public Optional<Order> getOrderByOrderId(int id) {
        Order order = null;
        if (this.orders.size()>0) {
            order = this.orders.stream().filter(o -> o.getId() == id).findFirst().get();
        }
        if (Optional.of(order).isPresent())
            return Optional.of(order);
        else
            return Optional.empty();
    }   
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo ai Clienti
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileUserPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getUsername()));
                line.add(String.valueOf(this.getEmail()));
                line.add(String.valueOf(this.getPassword()));
                
                CsvUtility.addLineToCsv(line, fileUserPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileUserPath);
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
            List<List<String>> values = CsvUtility.importDataFromCsv(fileUserPath);
            File oldFile = new File(fileUserPath);
            File newFile = new File("newUserPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;Username;Email;Password\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getUsername()));
                    line.add(String.valueOf(this.getEmail()));
                    line.add(String.valueOf(this.getPassword()));
                    CsvUtility.addLineToCsv(line, "newUserPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newUserPath.csv");
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
