package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta la categoria dei prodotti. Ciascuna categoria ha la propria lista
 * di prodotti
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ProductCategory<T> implements IRepositoryOperation {
    
    private final static String fileCategoryPath = "data/Category.csv";
    
    /**
     * L'id della categoria
     */
    private int id;
    
    /**
     * La categoria dei prodotti di tipo generico
     */
    private T category;
    
    /**
     * La lista dei prodotti associati alla categoria
     */
    private List<Product> products = new ArrayList<>();
    
    /**
     * Costruttore senza parametri della classe ProductCategory
     */
    public ProductCategory() {}
    
    
    /**
     * Costrutto con parametri della classe ProductCategory
     * 
     * @param categoria la categoria dei prodotti
     */
    public ProductCategory(T categoria) {
        this.category = categoria;
    }
    
    
    /**
     * Altro costruttore con parametri della classe ProductCategory
     * 
     * @param id l'id della categoria delle portate
     * @param category la categorian delle portate
     */
    public ProductCategory(int id, T category) {
        this.id = id;
        this.category = category;
    }
    
    
    /**
     * Questo metodo restituisce l'id della categoria dei prodotti
     * 
     * @return l'id della categoria dei prodotti
     */
    public int getId() {
        return this.id;
    }
    
    
    /**
     * Questo metodo permette di settare l'id della categoria dei prodotti
     * 
     * @param id l'id della categoria dei prodotti
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Questo metodo restituisce la categoria dei prodotti
     * 
     * @return la categoria dei prodotti
     */
    public T getCategory() { 
        return this.category;
    }
    
    
    /**
     * Questo metodo permette di settare la categoria dei prodotti
     * 
     * @param category la categoria dei prodotti da settare
     */
    public void setCategory(T category) {
        this.category = category;
    }
    
    
    /**
     * Questo metodo restituisce la lista dei prodotti associati alla categoria
     * 
     * @return la lista dei prodotti associati alla categoria
     */
    public List<Product> getProducts() {
        return this.products;
    }
    
    
    /**
     * Questo metodo permette di settare la lista dei prodotti associati alla categoria
     * 
     * @param products la lista dei prodotti da associare alla categoria
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    /**
     * Questo metodo permette di aggiungere un prodotto alla categoria
     * 
     * @param product il prodotto da aggiungere alla categoria
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }
    
    
    /**
     * Questo metodo permette di rimuovere un prodotto dalla categoria
     * 
     * @param product il prodotto da rimuovere
     */
    public void removeProduct(Product product) {
        this.products.remove(product);
    }
    

    /**
     * Questo metodo permette di appendere un record nel file relativo alle categorie
     * 
     * @return true se viene aggiunto un record al CSV corrispondente, false altrimenti
     */
    public boolean addRecord() {
        try { 
            File csvFile = new File(fileCategoryPath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                List<String> line = new ArrayList<>();
                line.add(String.valueOf(this.getId()));
                line.add(String.valueOf(this.getCategory().toString()));
                CsvUtility.addLineToCsv(line, fileCategoryPath);
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+fileCategoryPath);
            }
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di aggiornare il file Csv relativo alle categorie
     * 
     * @return true se il file Csv è stato aggiornato, false altrimenti
     */
    public boolean updateRecord() {
        try { 
            List<List<String>> values = CsvUtility.importDataFromCsv(fileCategoryPath);
            File oldFile = new File(fileCategoryPath);
            File newFile = new File("newCategoryPath.csv");
            PrintWriter newCsv = new PrintWriter(new FileWriter(newFile), true);
            
            newCsv.write("Id;Name\n");
            newCsv.close();
            for(int i=0; i<values.size(); i++){
                List<String> line = new ArrayList<>();
                if (Integer.parseInt(values.get(i).get(0))==this.getId()) {
                    line.add(String.valueOf(this.getId()));
                    line.add(String.valueOf(this.getCategory().toString()));
                    CsvUtility.addLineToCsv(line, "newCategoryPath.csv");
                }
                else{
                    CsvUtility.addLineToCsv(values.get(i), "newCategoryPath.csv");
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
    
    @Override
    public String toString() {
        return getCategory().toString();
    }
}
