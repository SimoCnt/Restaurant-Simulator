package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Questa classe fornisce dei metodi utility per lavorare con la lettura e la
 * scrittura di file CSV
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class CsvUtility {
    
    /**
     * Questo metodo permette di creare un file/file csv vuoto
     * 
     * @param filePath il path del file che deve essere creato
     * @return true se il file è stato creato, false altrimenti
     */
    public static boolean createEmptyCsv(String filePath) {
        try {
            FileWriter csvOutputFile = new FileWriter(new File(filePath));
            csvOutputFile.close();
            return true;
        } catch (IOException e) {
            System.out.println("Non è stato possibile creare il file: "+filePath);
        }
        return false;
    }
    
    /**
     * Questo metodo permette di aggiungere un record in coda ad un file csv
     * 
     * @param data il record da inserire nel file
     * @param filePath il percorso del file
     * @return true se viene aggiunto il record, false altrimenti
     */
    public static boolean addLineToCsv(List<String> data, String filePath) {
        try {
            File csvFile = new File(filePath);
            if (csvFile.exists()) {
                FileWriter writerCsv = new FileWriter(csvFile, true);
                String dataHeader = data.stream().collect(Collectors.joining(";"));
                writerCsv.write(dataHeader);
                writerCsv.write("\n");
                writerCsv.close();
                return true;
            }
            else {
                System.out.println("Il seguente file non esiste: "+filePath);
            }
            return false;
        } catch (IOException e) {
            System.out.println("Si è verificato un errore nell'aggiunta dei record nel CSV");
        }
        return false;
    }
    
    
    /**
     * Questo metodo permette di importare tutti i dati del csv esclusa la prima
     * riga (che si suppone essere l'insieme delle intestazioni del file)
     * 
     * @param filePath il file csv da cui importare i dati
     * @return l'insieme dei record del file csv escluso il primo
     */
    public static List<List<String>> importDataFromCsv(String filePath) {     
        
        List<List<String>> values = new ArrayList<>();
        
        // Lettura del file CSV nello stream
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            values = stream
            .skip(1)
            .map(line -> Arrays.asList(line.split(";")))
            .collect(Collectors.toList()); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
    
    
    /**
     * Questo metodo permette di restituire il massimo intero presente in una 
     * colonna di soli numeri interi
     * 
     * @param columnIndex la colonna del file che deve essere esaminata
     * @param filePath il percorso del file da cui deve essere esaminata la colonna
     * @return 0 se non vi sono elementi nella colonna, il massimo intero presente
     * in quella colonna altrimenti
     */
    public static int maxIntegerColumn(int columnIndex, String filePath) {

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            if (Files.lines(Paths.get(filePath)).skip(1).count()>0) {
                IntSummaryStatistics statistics = stream
                    .skip(1)
                    .map(s -> s.split(";")[columnIndex])
                    .mapToInt(Integer::valueOf)
                    .summaryStatistics();
                return statistics.getMax();
            }
            else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
