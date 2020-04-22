package restaurant_simulator;

/**
 * Interfaccia funzionale IValidator, fornisce i metodi base per effettuare dei test di validazione
 * di tipi generici
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */

@FunctionalInterface
public interface IValidator<K> {
    
    /**
     * Metodo test, restituisce il risultato della validazione del parametro (di tipo generico)
     * passato.
     * 
     * @param param paramatro da convalidare
     * @return un'istanza di tipo GenericValidationResult. Questl'ultima contiene
     * il risultato della validazione.
     */
    public GenericValidationResult test(K param);
    
    
    /**
     * Metodo and, implementa l'operazione booleana and tra i risultati di due
     * test di validazione
     * 
     * @param other istanza di IValidator
     * @return un'istanza di tipo GenericValidationResult. Questl'ultima contiene
     * il risultato della validazione.
     */
    default IValidator<K> and(IValidator<K> other) {
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return !result.isValid() ? result : other.test(param);
        };
    }
    
    /**
     * Metodo or, implementa l'operazione booleana or tra i risultati di due
     * test di validazione
     * 
     * @param other istanza di IValidator
     * @return un'istanza di tipo GenericValidationResult. Questl'ultima contiene
     * il risultato della validazione
     */
    default IValidator<K> or(IValidator<K> other) {
        return (param) -> {
            GenericValidationResult result = this.test(param);
            return result.isValid() ? result : other.test(param);
        };
    }
}

