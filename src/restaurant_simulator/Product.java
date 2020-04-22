package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Rappresenta un prodotto del menu del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Product implements IConsumation, IRepositoryOperation {
    
    private final static String fileProductPath = "data/Product.csv";
    
    
    /**
     * L'id del prodotto
     */
    private int id;
    
    /**
     * Il nome del prodotto
     */
    private String name;
    
    /**
     * L'id della categoria relativa al prodotto
     */
    private int categoryId;
    
    /**
     * La lista degli ingredienti del prodotto
     */
    private List<String> ingredients;
    
    /**
     * Il prezzo del prodotto
     */
    private double price;
    
    
    /**
     * Costruttore senza parametri della classe Product
     */
    public Product() {}
    
    
    /**
     * Costruttore con parametri della classe Product
     * 
     * @param name il nome del prodotto
     * @param price il prezzo del prodotto
     */
    public Product (String name, double price){
        this.name=name;
        this.price=price;
    }
    
    
    /**
     * Restituisce l'id del prodotto
     * 
     * @return l'id del prodotto
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Restituisce il nome del prodotto
     * 
     * @return il nome del prodotto
     */
    public String getName() {
        return this.name;
    }
    
    
    /**
     * Restituisce l'id della categoria assegnata al prodotto
     * 
     * @return l'id della categoria assegnata al prodotto
     */
    public int getCategoryId() {
        return this.categoryId;
    }
    
    
    /**
     * Restituisce la lista degli ingredienti associati al prodotto
     * 
     * @return la lista degli ingredienti associati al prodotto
     */
    public List<String> getIngredients() {
        return this.ingredients;
    }
    
    
    /**
     * Restituisce il prezzo del prodotto
     * 
     * @return il prezzo del prodotto
     */
    public double getPrice() {
        return this.price;
    }
    
    
    /**
     * Consente di settare l'id del prodotto
     * 
     * @param id l'id del prodotto
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Consente di settare il nome del prodotto
     * 
     * @param name il nome del prodotto
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * Consente di settare l'id della categoria del prodotto
     * 
     * @param categoryId l'id della categoria del prodotto
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    
    /**
     * Consente di settare la lista degli ingredienti associati al prodotto
     * 
     * @param ingredients la lista degli ingredienti associati al prodotto
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    
    
    /**
     * Consente di settare il prezzo del prodotto
     * 
     * @param price il prezzo del prodotto
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    
    /**
     * Questo metodo permette di appendere un record nel file relativo ai Prodotti
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileProductPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getCategoryId()));
                line.add(String.valueOf(this.getName()));
                line.add(String.join(", ", this.getIngredients().stream().toArray(String[]::new)));
                line.add(String.valueOf(this.getPrice()));
                CsvUtility.addLineToCsv(line, fileProductPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileProductPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo ai Prodotti
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileProductPath);
            File oldFile = new File(fileProductPath);
            File newFile = new File("newProductPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;CategoryId;Name;Ingredients;Price\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getCategoryId()));
                    line.add(this.getName());
                    line.add(String.join(", ", this.getIngredients().stream().toArray(String[]::new)));
                    line.add(String.valueOf(this.getPrice()));
                    CsvUtility.addLineToCsv(line, "newProductPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newProductPath.csv");
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
    
    
    /**
     * Restituisce le informazioni relativi all'oggetto prodotto sotto forma di stringa
     * 
     * @return le informazioni relative all'oggetto prodotto sotto forma di stringa
     */
    @Override
    public String toString() {
        return "Nome: "+this.name+" "+"Prezzo: "+this.price;
    }
    
}
