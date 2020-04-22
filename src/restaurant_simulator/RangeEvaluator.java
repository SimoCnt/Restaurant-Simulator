package restaurant_simulator;

import com.toedter.calendar.MinMaxDateEvaluator;
import java.util.Date;

/**
 * Classe RangeEvaluator, si occupa di validare le date in un certo range di date (min, max)
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RangeEvaluator extends MinMaxDateEvaluator {
    
    /**
     * Questo metodo permette di verificare la validata di una data in un certo range
     * di date (min, max)
     * 
     * @param date la data da validare
     * @return true se la data non è valida, false altrimenti
     */
    @Override
    public boolean isInvalid(Date date) {
        return super.isInvalid(date);
    }
}
