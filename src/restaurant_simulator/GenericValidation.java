package restaurant_simulator;

import java.util.function.Predicate;


/**
 * Classe GenericValidation, la classe generica che gestisce la validazione di
 * un dato tipo
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class GenericValidation<K> implements IValidator<K> {
    
    /**
     * Attributo predicate di tipo generico
     */
    private Predicate<K> genericPredicate;
    
    
    /**
     * Costruttore con parametri della classe GenericValidation
     * 
     * @param genericPredicate istanza di tipo Predicate
     */
    private GenericValidation(Predicate<K> genericPredicate) {
        this.genericPredicate = genericPredicate;
    }
    
    
    /**
     * Metodo from, consente di creare una validazione generica a partire da un'implementazione
     * dell'interfaccia funzionale Predicate di tipo K
     * 
     * @param <K> il tipo della validazione generica
     * @param genericPredicate istanza di tipo Predicate
     * @return un'istanza di GenericValidation di tipo K
     */
    public static <K> GenericValidation<K> from(Predicate<K> genericPredicate) {
        return new GenericValidation<K>(genericPredicate);
    }

    
    /**
     * Metodo test, effettua la validazione del parametro di tipo K passato come argomento
     * 
     * @param parameter il parametro da testare (validare)
     * @return un'istanza di tipo GenericValidationResult. Quest'ultima contiene
     * il risultato ottenuto dal test.
     */
    @Override
    public GenericValidationResult test(K parameter) {
        /* 
           Se il test è positivo restituisce un GenericValidationResult con proprietà
           valid settata a true, altrimenti un GenericValidationResult con prorietà
           valid settata a false
        */
        return genericPredicate.test(parameter) ? GenericValidationResult.approved() : GenericValidationResult.disapproved();
    } 
}
