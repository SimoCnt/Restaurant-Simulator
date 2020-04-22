package restaurant_simulator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Classe RestaurantConcreteBuilder, gestisce la creazione dell'oggetto
 * Restaurant
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantConcreteBuilder implements IRestaurantBuilder {
    
    private final String fileUserPath = "data/User.csv";
    private final String fileRestaurantInfoPath = "data/RestaurantInfo.csv";
    private final String fileTablePath = "data/Table.csv";
    private final String fileProductPath = "data/Product.csv";
    private final String fileCategoryPath = "data/Category.csv";
    private final String fileOrderPath = "data/Order.csv";
    private final String fileOrderItemPath = "data/OrderItem.csv";
    private final String fileCategoryFeedbackPath = "data/CategoryFeedback.csv";
    private final String fileFeedbackPath = "data/Feedback.csv";
    
    /**
     * Oggetto restaurant da costruire
     */
    private Restaurant restaurant;
    
    
    /**
     * Crea un oggetto di tipo Restaurant
     */
    public void createRestaurant() {
        restaurant = new Restaurant();
    }
    
    
    /**
     * Questo metodo aggiunge le informazioni base del ristorante (id, nome ristorante) all'oggetto
     * Restaurant
     */
    public void addInfoRestaurant() {
        List<List<String>> data = CsvUtility.importDataFromCsv(fileRestaurantInfoPath);
        for (int i=0; i<data.size(); i++) {
            try {
                restaurant.setId(Integer.parseInt(data.get(i).get(0)));
                restaurant.setName(String.valueOf(data.get(i).get(1)));
            } catch (NumberFormatException e) {
                System.out.println("Si è verificato un errore nel parsing del file: "+fileRestaurantInfoPath);
            }
        }
            
    }
    
    
    /**
     * Questo metodo aggiunge una lista di tavoli all'oggetto Restaurant
     */
    public void addTables() {
        List<List<String>> data = CsvUtility.importDataFromCsv(fileTablePath);        
        List<Table> tables = new ArrayList<Table>();
        try {
            for (int i=0; i<data.size(); i++) {
                Table table = new Table();
                table.setId(Integer.parseInt(data.get(i).get(0)));
                table.setName(String.valueOf(data.get(i).get(1)));
                table.setNumSeats(Integer.parseInt(data.get(i).get(2)));
                tables.add(table);
            }
            restaurant.setTables(tables);
        } catch (NumberFormatException e) {
            System.out.println("Si è verificato un errore nel parsing dei tavoli del ristorante");
        }
    }
    
        
    /**
     * Questo metodo aggiunge il menu all'oggetto Restaurant
     */
    public void addMenu()  {
        
        File fileMenu = new File(fileProductPath);
        File fileCategories = new File(fileCategoryPath);
        
        if (fileMenu.exists() && fileCategories.exists()) {  
            
            // Importo i dati
            List<List<String>> dataCategory = CsvUtility.importDataFromCsv(fileCategoryPath);
            List<List<String>> data = CsvUtility.importDataFromCsv(fileProductPath);
            
            // Creo un oggetto di tipo Menu
            Menu menu = new Menu();
            
            ProductCategory category;
            
            // Ricavo le categorie dei prodotti
            for (int c=0; c<dataCategory.size(); c++) {
                try {
                    Class categoryClass = Class.forName("restaurant_simulator."+String.valueOf(dataCategory.get(c).get(1)));
                    Object categoryClass2 = categoryClass.getDeclaredConstructor().newInstance();
                    category = new ProductCategory(Integer.parseInt(dataCategory.get(c).get(0)),categoryClass2);
                    menu.addProductCategory(category);
                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException | ClassNotFoundException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            
            // Ricavo i prodotti e le assegno alle corrispettive categorie
            for (int i=0; i<data.size(); i++) {
                Product product = new Product();
                try {

                    // Configuro l'oggetto Product
                    product.setId(Integer.parseInt(data.get(i).get(0)));
                    product.setName(String.valueOf(data.get(i).get(2)));
                    if (!data.get(i).get(3).isEmpty())
                        product.setIngredients(Arrays.asList(data.get(i).get(3).split(", ")));
                    else
                        product.setIngredients(new ArrayList<>());
                    product.setPrice(Double.parseDouble(data.get(i).get(4)));

                    // Ricavo la posizione della categoria in cui devo inserire il prodotto
                    int posCategory = menu.getIndexOfCategoryById(Integer.parseInt(data.get(i).get(1)));

                    // Controllo se la posizione è valida e in caso di esito positivo aggiungo il prodotto alla categoria
                    if (menu.getIndexOfCategoryById(Integer.parseInt(data.get(i).get(1)))!=-1) {
                        menu.getProductCategory().get(posCategory).addProduct(product);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Si è verificato un errore nel parsing del menu");
                }
            }
            
            // Setto il menu del ristorante
            restaurant.setMenu(menu);
        }
        else {
            System.out.println("Uno dei seguenti file non esiste: Product.csv e Category.csv");
        }
    }
    
    
    /**
     * Questo metodo aggiunge la lista dei clienti all'oggetto Restaurant
     */
    public void addUsers(){
        List<List<String>> dataUser = CsvUtility.importDataFromCsv(fileUserPath);
        List<List<String>> dataOrder = CsvUtility.importDataFromCsv(fileOrderPath);
        List<List<String>> dataOrderItem = CsvUtility.importDataFromCsv(fileOrderItemPath);
        List<User> users = new ArrayList<>();

        try {
            for(int i=0; i<dataUser.size(); i++){
                User user = new User();
                List<Order> orders = new ArrayList<>();

                // Aggiungo i dati di registrazione all'oggetto User
                user.setId(Integer.parseInt(dataUser.get(i).get(0)));
                user.setUsername(String.valueOf(dataUser.get(i).get(1)));
                user.setEmail(String.valueOf(dataUser.get(i).get(2)));
                user.setPassword(String.valueOf(dataUser.get(i).get(3)));
                
                for(int j=0; j<this.restaurant.getUserOrdersByUserId(user.getId()).size(); j++){ 
                    if(Integer.parseInt(dataOrder.get(i).get(1)) == user.getId()){
                        Order order = new Order();
                        Date date = DateUtility.parseDateWithTime(String.valueOf(dataOrder.get(i).get(3)));
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
                        // Effettuo il parsing degli OrderItem relativi all'ordine
                        List<OrderItem> items = new ArrayList<>();
                        for(int z=0; z<dataOrderItem.size(); z++){ 
                            if(Integer.parseInt(dataOrderItem.get(z).get(0)) == Integer.parseInt(dataOrder.get(j).get(0))){
                                OrderItem item = new OrderItem();
                                item.setOrderId(Integer.parseInt(dataOrderItem.get(z).get(0)));
                                item.setProductId(Integer.parseInt(dataOrderItem.get(z).get(1)));
                                item.setProductName(String.valueOf(dataOrderItem.get(z).get(2)));
                                item.setPrice(Double.parseDouble(dataOrderItem.get(z).get(3)));
                                items.add(item);
                            }                    
                        }
                        order.setOrderItems(items);
                        orders.add(order);
                    }
                }
                user.setOrders(orders);
                users.add(user);
            }
            // Aggiungo i clienti a Restaurant
            restaurant.setUsers(users);
        } catch (NumberFormatException e) {
            System.out.println("Si è verificato un errore nel parsing dei clienti del ristorante");
        }
    }
    
    
    /**
     * Questo metodo aggiunge la lista degli ordini all'oggetto Restaurant
     */
    public void addOrders(){
        try{ 
            List<List<String>> dataOrder = CsvUtility.importDataFromCsv(fileOrderPath);       
            List<List<String>> dataOrderItem = CsvUtility.importDataFromCsv(fileOrderItemPath);        
        
            List<Order> orders = new ArrayList<>();
        
            // Effettuo il parsing di tutti ordini 
            for(int i=0; i<dataOrder.size(); i++){   
                Order order = new Order();
                Date date = DateUtility.parseDateWithTime(dataOrder.get(i).get(3));
                
                // Costruisco l'oggetto Order
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

                // Effettuo il parsing degli OrderItem relativi all'ordine
                List<OrderItem> items = new ArrayList<>();
                for(int j=0; j<dataOrderItem.size(); j++){                    
                    if(Integer.parseInt(dataOrderItem.get(j).get(0)) == Integer.parseInt(dataOrder.get(i).get(0))){
                        OrderItem item = new OrderItem();

                        item.setOrderId(Integer.parseInt(dataOrderItem.get(j).get(0)));
                        item.setProductId(Integer.parseInt(dataOrderItem.get(j).get(1)));
                        item.setProductName(String.valueOf(dataOrderItem.get(j).get(2)));
                        item.setPrice(Double.parseDouble(dataOrderItem.get(j).get(3)));
                        items.add(item);
                        //System.out.println("Items: " + item.getIdOrder() + " | " + item.getIdProduct() + " | " + item.getNameProduct() + " | " + item.getPrice());
                    }                    
                }
                order.setOrderItems(items);
                orders.add(order);
                //System.out.println("Dettagli ordine: " + order.getId() + " | " + order.getIdUser() + " | " + order.getIdTable() + " | " + order.getData()  + " | " /*+ order.getOrderItems() + " | "*/ + order.getTotalPrice());
            }
        
        // Assegno gli ordini al ristorante
        restaurant.setOrders(orders);
             
        } catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("Errore durante la lettura del database users (creazione lista ordini)");
        }        
    }
    
    
    /**
     * Questo metodo aggiunge la lista dei feedback all'oggetto Restaurant
     */
    public void addFeedback() {
        try{
            List<List<String>> dataCategoryFeedback = CsvUtility.importDataFromCsv(fileCategoryFeedbackPath);
            List<List<String>> dataFeedback = CsvUtility.importDataFromCsv(fileFeedbackPath);
            
            List<CategoryFeedback> categories = new ArrayList<>();
            
            for(int i=0; i<dataCategoryFeedback.size(); i++){
                
                // Effettuo il parse della categoria dei feedback
                CategoryFeedback categoryFeedback = new CategoryFeedback();
                categoryFeedback.setId(Integer.parseInt(dataCategoryFeedback.get(i).get(0)));
                categoryFeedback.setName(String.valueOf(dataCategoryFeedback.get(i).get(1)));
                
                List<Feedback> feedbackList = new ArrayList<>();
                
                // Effettuo il parse dei Feedback per ogni categoria
                for(int j=0; j<dataFeedback.size(); j++){
                    Feedback feedback = new Feedback();
                    
                    if(Integer.parseInt(dataCategoryFeedback.get(i).get(0)) == Integer.parseInt(dataFeedback.get(j).get(1))){
                        feedback.setId(Integer.parseInt(dataFeedback.get(j).get(0)));
                        feedback.setCategoryId(Integer.parseInt(dataFeedback.get(j).get(1)));
                        feedback.setOrderId(Integer.parseInt(dataFeedback.get(j).get(2)));
                        feedback.setUserId(Integer.parseInt(dataFeedback.get(j).get(3)));
                        feedback.setValue(Double.parseDouble(dataFeedback.get(j).get(4)));
                        feedbackList.add(feedback);
                    }
                 
                    categoryFeedback.setFeedback(feedbackList);
                }
                categories.add(categoryFeedback);
            }
            //System.out.println("Categories: "+categories);
            restaurant.setFeedback(categories);
            
        } catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("Errore durante la lettura dei database CategoryFeedback.csv e Feedback.csv");
        }
    }
    

    /**
     * Questo metodo restituisce l'oggetto Restaurant 
     * 
     * @return l'oggetto Restaurant
     */
    public Restaurant getRestaurant() {
        return this.restaurant;
    }

}
