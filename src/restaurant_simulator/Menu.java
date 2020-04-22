package restaurant_simulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Rappresenta il menu del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class Menu {
    
    /**
     * La lista delle categorie con i relativi prodotti
     */
    private List<ProductCategory> productCategory = new ArrayList<>();
    
    
    /**
     * Questo metodo restituisce la lista delle categorie con i
     * relativi prodotti
     * 
     * @return la lista delle categorie con i relativi prodotti
     */
    public List<ProductCategory> getProductCategory() {
        return this.productCategory;
    }
    
    
    /**
     * Questo metodo permette di settare la lista delle categorie con
     * i relativi prodotti
     * 
     * @param productCategory la lista delle categorie con i relativi
     * prodotti
     */
    public void setProductCategory(List<ProductCategory> productCategory) {
        this.productCategory = productCategory;
    }
    
    
    /**
     * Questo metodo permette di aggiungere un ProductCategory al Menu
     * 
     * @param productCategory la categoria da aggiungere alla lista delle categorie
     */
    public void addProductCategory(ProductCategory productCategory) {
        this.productCategory.add(productCategory);
    }
    
    
    /**
     * Questo metodo restituisce l'id di una categoria dato il suo nome
     * 
     * @param category il nome della categoria da cui ricavare l'id
     * @return l'id della categoria corrispondente al nome passato a parametro, -1
     * altrimenti
     */
    public int getIdByCategoryName(String category) {
        for (Iterator<ProductCategory> it=this.getProductCategory().iterator(); it.hasNext();) {
            ProductCategory elemento = it.next();
            if(elemento.toString().equals(category))
                return elemento.getId();
        }
        return -1;
    }
    
    /**
     * Questo metodo permette di ricavare l'indice nella lista delle categorie
     * in cui si trova la categoria dato l'id
     * 
     * @param categoryId l'id della categoria da cui ricavare l'indice nella lista delle categorie
     * @return la posizione in cui si trova la categoria in id passato a parametro nella lista, -1
     * altrimenti
     */
    public int getIndexOfCategoryById(int categoryId) {
        int pos = 0;        
        for(ProductCategory pc : this.productCategory) {
            if(categoryId ==(pc.getId()))
                return pos;
            pos++;
        }
        // Se non esiste la categoria con id specificato a parametro
        return -1;
    }
    
    
    /**
     * Questo metodo verifica se esiste una categoria di menu dato il suo id
     * 
     * @param id l'id della categoria da controllare
     * @return true se la categoria con id passato a parametro esiste, false altrimenti
     */
    public boolean containsCategoryById(int id){
        return this.getProductCategory().stream().filter(o -> o.getId()== id).findFirst().isPresent();
    }
    
    
    /**
     * Questo metodo permette di restituisce la lista delle categorie con almeno
     * un prodotto
     * 
     * @return la lista delle categorie con almeno un prodotto
     */
    public List<ProductCategory> getCategoriesWithAtLeastAProduct() {
        return this.getProductCategory().stream().filter(pc -> pc.getProducts().size()>0).collect(Collectors.toList());
    }
    
    
    /**
     * Questo metodo permette di ottenere la categoria dato l'id
     * 
     * @param categoryId l'id della categoria da carcare e restituire
     * @return la ProductCategory con id passato a parametro
     */
    public ProductCategory<?> getProductCategoryById(int categoryId) {
       return this.productCategory.stream()
       .filter(p -> p.getId()==categoryId).findFirst().get();
    }
    
    
    /**
     * Questo metodo restituisce un prodotto data la categoria e l'id
     * 
     * @param productCategory la categoria su cui cercare il prodotto
     * @param productId l'id del prodotto da cercare e restituire
     * @return il prodotto con id passato a parametro
     */
    public Product getProductById(ProductCategory<?> productCategory, int productId) {
       return productCategory.getProducts().stream()
       .filter(p -> p.getId()==productId).findFirst().get();
    }
}
