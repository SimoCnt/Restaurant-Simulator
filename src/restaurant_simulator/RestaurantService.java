package restaurant_simulator;

import java.util.List;

/**
 * Classe RestaurantService, fornisce dei metodi utility per ricavare informazioni
 * sul ristorante dal csv corrispondente
 *
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantService {
    
    private static final String fileRestaurantInfoPath = "data/RestaurantInfo.csv";
    
    /**
     * Il dataset delle categorie
     */
    private static List<List<String>> dataRestaurantInfo = CsvUtility.importDataFromCsv(fileRestaurantInfoPath);
    
    /**
     * Questo metodo consente di ottenere il nome del ristorante corrispondente
     * all'id passato a parametro
     * 
     * @param id l'id del ristorante da cercare
     * @return il nome del ristorante corrispondente all'id passato a parametro se esiste, altrimenti 
     * una stringa vuota
     */
    public static String getTableNameByIdFromCsv(int id){
        String str = "";
        for (int i=0; i<dataRestaurantInfo.size(); i++) {
            if (Integer.parseInt(dataRestaurantInfo.get(i).get(0))==id)
                str = dataRestaurantInfo.get(i).get(1);
        }
        return str;
    }  
}
