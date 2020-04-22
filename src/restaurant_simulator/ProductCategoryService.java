package restaurant_simulator;

import java.util.List;

/**
 * Classe ProductCategoryService, fornisce dei metodi utility per ricavare informazioni
 * sui ProductCategory dal csv corrispondente
 *
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ProductCategoryService {
    
    private static final String fileCategoryPath = "data/Category.csv";
    
    /**
     * Il dataset delle categorie
     */
    private static List<List<String>> dataCategory = CsvUtility.importDataFromCsv(fileCategoryPath);
    
    
    /**
     * Questo metodo consente di ottenere il nome della categoria corrispondente
     * all'id passato a parametro
     * 
     * @param id l'id della categoria da cercare
     * @return il nome della categoria corrispondente all'id passato a parametro se esiste, altrimenti 
     * una stringa vuota
     */
    public static String getCategoryNameByIdFromCsv(int id){
        String str = "";
        for (int i=0; i<dataCategory.size(); i++) {
            if (Integer.parseInt(dataCategory.get(i).get(0))==id)
                str = dataCategory.get(i).get(1);
        }
        return str;
    }  
    
}
