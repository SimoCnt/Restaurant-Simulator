package restaurant_simulator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe FileUtility, fornisce un utility per i file
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class FileUtility {
    
    /**
     * Questo metodo restituisce il contenuto del file sotto forma di stringa
     * 
     * @param filePath il percorso del file da leggere
     * @return il contenuto del file
     */
    public static String readFileToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nella lettura del file: "+filePath);
        }
        return "";
    }
}
