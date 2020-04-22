package restaurant_simulator;

import java.util.Date;

/**
 * Interfaccia ICheckAvailabilityTable, dichiara un metodo che consente di verificare la
 * disponibilità di un tavolo e di restituirne l'id
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public interface ICheckAvailabilityTable {
    
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
    public int checkAvailabilityTable(String fileTablesPath, String fileOrderPath, int numClients, Date data);
}