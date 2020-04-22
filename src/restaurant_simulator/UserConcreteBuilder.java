package restaurant_simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Classe UserConcreteBuilder, gestisce la creazione dell'oggetto
 * User
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserConcreteBuilder implements IUserBuilder {
        
    private final String fileOrderPath = "data/Order.csv";
    private final String fileOrderItemPath = "data/OrderItem.csv"; 
    private final String fileUsersPath = "data/User.csv";
    
    /**
     * L'oggetto User da costruire
     */
    private User user;
    
    
    /**
     * Questo metodo crea un oggetto di tipo User
     */
    public void createUser() {
        user = new User();
    }

    
    /**
     * Questo metodo aggiunge i dati di registrazione all'oggetto User 
     * 
     * @param email l'email del cliente loggato
     * @param password la password del cliente loggato
     */
    public void addInfo(String email, String password){
        //System.out.println("Password presa: " + password);
        String hash = PasswordHasher.scramble(password); 
        //System.out.println("Password a seguito dell'hash: " + hash);        	
        Boolean log = false;        
                
        try {           
            String line = "";
            FileReader file = new FileReader(fileUsersPath);
            BufferedReader br = new BufferedReader(file);
            line = br.readLine();
            
            while((line = br.readLine()) != null) {                
                String[] values = line.split(";");                
                if(values[2].equals(email) && values[3].equals(hash)) {                    
                    log = true;                                        
                    this.user.setId(Integer.parseInt(values[0]));
                    this.user.setUsername(String.valueOf(values[1]));
                    this.user.setEmail(String.valueOf(values[2]));
                    this.user.setPassword(String.valueOf(values[3]));
                }                
            }
            if(!log){
                System.out.println("Errore, corrispondenza dell'utente loggato non trovato nel database!");
            }
        } catch(IOException | NumberFormatException e){
            System.out.println("Errore durante la lettura del database users (Log utente)");
        }        
    }
    
    
    /**
     * Questo metodo aggiunge la lista degli ordini associati al cliente loggato
     */
    public void addOrders(){
        try{
            List<List<String>> dataOrder = CsvUtility.importDataFromCsv(fileOrderPath);       
            List<List<String>> dataOrderItem = CsvUtility.importDataFromCsv(fileOrderItemPath);        
        
            List<Order> orders = new ArrayList<>();
        
            // Effettuo il parse degli ordini associati al cliente
            for(int i=0; i<dataOrder.size(); i++){            
                if(Integer.parseInt(dataOrder.get(i).get(1)) == user.getId()){
                    Order order = new Order();
                    Date date = DateUtility.parseDateWithTime(dataOrder.get(i).get(3));
                    
                    // Setto le proprietà dell'oggetto Order
                    order.setId(Integer.parseInt(dataOrder.get(i).get(0)));
                    order.setUserId(Integer.parseInt(dataOrder.get(i).get(1)));
                    order.setTableId(Integer.parseInt(dataOrder.get(i).get(2)));
                    order.setData(date); 
                    order.setTotalPrice(Double.parseDouble(dataOrder.get(i).get(4)));
                    
                    if (String.valueOf(dataOrder.get(i).get(5)).equalsIgnoreCase("Pagato")) {
                        order.setState(new OrderPaidState());
                    }
                    else if(String.valueOf(dataOrder.get(i).get(5)).equalsIgnoreCase("Completato")){
                        order.setState(new OrderCompletedState());
                    }
                    
                    order.setInsertedCash(Double.parseDouble(dataOrder.get(i).get(6)));                    
                    order.setRest(Double.parseDouble(dataOrder.get(i).get(7)));
                    order.setFeedbackReleased(Boolean.parseBoolean(dataOrder.get(i).get(8)));
                    order.setDateSimulated(Boolean.parseBoolean(dataOrder.get(i).get(9)));
                    
                    /*
                        Imposto lo stato dell'ordine su Completato se la data attuale e maggiore di quella di
                        consumazione. Il cambiamento di stato avviene solo se non ho effettuato alcuna simulazione
                    */
                    if (DateUtility.removeSecondsAndMilliseconds(new Date()).after(DateUtility.addHour(order.getData(), 1))) {
                        if (!order.isDateSimulated()) {
                            order.getState().orderDateIsLessThanCurrentDate(order);
                            order.updateRecord();
                        }
                    }

                    // Scorro OrderItem.csv
                    List<OrderItem> items = new ArrayList<>();
                    for(int j=0; j<dataOrderItem.size(); j++){                    
                        if(Integer.parseInt(dataOrderItem.get(j).get(0)) == Integer.parseInt(dataOrder.get(i).get(0))){
                            OrderItem item = new OrderItem();
                            item.setOrderId(Integer.parseInt(dataOrderItem.get(j).get(0)));
                            item.setProductId(Integer.parseInt(dataOrderItem.get(j).get(1)));
                            item.setProductName(String.valueOf(dataOrderItem.get(j).get(2)));
                            item.setPrice(Double.parseDouble(dataOrderItem.get(j).get(3)));
                            items.add(item);
                        }                    
                    }
                                        
                    order.setOrderItems(items);
                    
                    orders.add(order);
                }
            }
            user.setOrders(orders); 
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Errore durante la lettura del database users (creazione lista ordini)");
        }        
    }         
    
    
    /**
     * Questo metodo restituisce l'oggetto User
     * 
     * @return l'oggetto User
     */
    public User getUser() {
        return this.user;
    }  

}
