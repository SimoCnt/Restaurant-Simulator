package restaurant_simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;


/**
 * Classe CheckAvailabilityTable, verifica la disponibilità dei tavoli per una
 * data di prenotazione e seleziona il tavolo da assegnare per la suddetta
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class CheckAvailabilityTable implements ICheckAvailabilityTable {
    
    /**
     * Mapping di tutti i tavoli del ristorante che sono maggiori del numero di clienti della 
     * prenotazione e minori a numero di clienti + 3. Per ciascun tavolo, la key è l'id del
     * tavolo, mentre il value il numero di posti.
     */
    private HashMap<Integer, Integer> tablesFilter = new HashMap<Integer, Integer>();
    
    
    /**
     * Questo metodo permette la verifica e restituzione, in caso di disponibilità,
     * dell'id del tavolo da assegnare alla prenotazione
     * 
     * @param fileTablesPath il path di Table.csv
     * @param fileOrderPath il path di Order.csv
     * @param numClients il numero di clienti per la prenotazione
     * @param data la data scelta di prenotazione
     * @return l'id del tavolo assegnato alla prenotazione, -1 se non sono stati trovati
     * tavoli da assegnare alla prenotazione per quel giorno o non esistono tavoli che
     * possono contenere il numero di persone indicato per la prenotazione
     * 
     */
    public int checkAvailabilityTable(String fileTablesPath, String fileOrderPath, int numClients, Date data) {
        // Verifico se esistono tavoli in Table.csv che siano >= a numClients e <= numClients + 3 e inserisco Id e numPosti in tablesFilter
        try{            
            String lineT = "";            
            boolean disp = false;
            FileReader fileTables = new FileReader(fileTablesPath);            
            BufferedReader brt = new BufferedReader(fileTables);            
            lineT = brt.readLine();            
            
            while((lineT = brt.readLine()) != null){
                String[] dataTable = lineT.split(";");
                                
                if((Integer.parseInt(dataTable[2]) >= numClients) && (Integer.parseInt(dataTable[2]) <= numClients + 3)){
                    // Aggiorno l'hashmap con key: id  e value: numPosti
                    tablesFilter.put(Integer.parseInt(dataTable[0]), Integer.parseInt(dataTable[2]));
                }
            }
            
            // non sono stati trovati tavoli del ristorante >= a numClients e <= numClients + 3
            if(tablesFilter.isEmpty()){
                //System.out.println("Non sono stati trovati tavoli del ristorante >= a numClients e <= numClients + 3");
                return -1;
            }
            else{
                // Ordino l'hashMap per value
                Map<Integer, Integer> sortedTablesFilter = tablesFilter;
                                
                sortedTablesFilter = tablesFilter
                    .entrySet()
                    .stream()
                    .sorted(comparingByValue())
                    .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));              
                
                // Verifico che non ci sia una corrispondenza tra id : key e tra la data passata a parametro e la data scelta.              
                // Qualora esista, passo alla key successiva della HashMap. La prima key che non ha corrispondenza sarà
                // quella tornata.
                for(int i = 0; i< sortedTablesFilter.size(); i++){
                    String lineO = "";
                    FileReader fileOrder = new FileReader(fileOrderPath);
                    BufferedReader bro = new BufferedReader(fileOrder);
                    lineO = bro.readLine();
                    disp = true;                    
                    Object key = (Integer)sortedTablesFilter.keySet().toArray()[i];
                    
                    //System.out.println("\n\nCheck del Tavolo id: " + key);
                                        
                    // Controllo riga per riga il file Order.csv per il check della key attuale
                    while((lineO = bro.readLine()) != null){
                        String[] dataOrder = lineO.split(";");                 

                        // l'id del tavolo del csv è uguale all'id del tavolo filtrato e le date corrispondono?
                        if((dataOrder[2].equals(key.toString())) && (dataOrder[3].equals(DateUtility.formatDateWithTime(data)))){
                           //System.out.println("Trovata una prenotazione per Tavolo Id " + key);
                           disp = false; 
                        } 
                    }
                    
                    /* Non è stata trovata alcuna prenotazione per il tavolo con id: key per il giorno scelto, 
                        quindi ritorno l'id di quel tavolo proseguendo con la prenotazione */
                    if(disp == true){
                       //System.out.println("\n\nTavolo Id " + key + " disponibile!");
                       return Integer.parseInt(key.toString()); 
                    }               
                }
            return -1;
            }
            
        } catch(Exception e){
            System.out.println("Errore durante la lettura del database");
        }       
        
        return -1;
    }
    
}
