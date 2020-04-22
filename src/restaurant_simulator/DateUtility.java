package restaurant_simulator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Classe che fornisce un Utility per le Date in Restaurant Simulator
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class DateUtility {
    
    /**
     * Rimuove i secondi e i millisecondi dalla data passata a parametro
     * 
     * @param date la data da modificare
     * @return la data senza i secondi e i millisecondi
     */
    public static Date removeSecondsAndMilliseconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, -cal.get(Calendar.SECOND));
        cal.add(Calendar.MILLISECOND, -cal.get(Calendar.MILLISECOND));
        Date dateWithoutSecondsAndMilliseconds = cal.getTime();
        return dateWithoutSecondsAndMilliseconds;
    }
    
    /**
     * Questo metodo aggiunfe un tot. numero di ore alla data passata a parametro 
     * 
     * @param date la data a cui deve essere sommato un'ora
     * @param hours le ore da aggiungere alla data
     * @return la data con un'ora aggiuntiva
     */
    public static Date addHour(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, +hours);
        Date dateWithAnHour = cal.getTime();
        return dateWithAnHour;
    }
    
    /**
     * Confronta la data passata a parametro con la data corrente
     * (incluso l'orario)
     * 
     * @param dateTest la data (senza i secondi e i millisecondi) che deve essere testata
     * @return 1 se dateTest è maggiore della data corrente, -1 se dateTest è minore della
     * data corrente e 0 se dateTest è uguale alla data corrente
     */
    public static int compareDate(Date dateTest) {
        Date currentDate = removeSecondsAndMilliseconds(new Date());
        if (dateTest.after(currentDate))
            return 1;
        if (dateTest.before(currentDate))
            return -1;
        return 0;
    }
    
    /**
     * Converte la data passata a parametro in una stringa che rispetta il
     * seguente formato di data: dd-mm-yyyy hh:mm
     * 
     * @param date la data che deve essere formattata
     * @return la stringa rappresentate la data nel formato: dd-mm-yyy hh:mm
     */
    public static String formatDateWithTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(date);
    }
    
    /**
     * Verifica se una data corrisponde allo stesso giorno della data corrente
     * 
     * @param date la data che deve essere confrontata con il giorno corrente
     * @return true se date e la data corrente rappresentano lo stesso giorno,
     * false altrimenti
     */
    public static boolean isSameDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        if(format.format(date).equals(format.format(currentDate))) {
            return true;
        }
        return false;
    }
    
    /**
     * Converte la data passata a parametro in una data che rispetta il
     * seguente formato di data: dd-mm-yyyy hh:mm
     * 
     * @param date la data di cui deve essere effettuato il parse
     * @return la data rappresentate la data nel formato: dd-mm-yyy hh:mm
     */
    public static Date parseDateWithTime(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("FormatDateWithTime: Si è verificato un errore nel parsing della data");
            return null;
        }
        
    }
    
}
