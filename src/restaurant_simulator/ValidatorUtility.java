package restaurant_simulator;


/**
 * Classe ValidatorUtility, rappresenta un utility per il validatore generico
 * (GenericValidation)
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ValidatorUtility {
    
    /**
     * Validazione con condizione che verifica se una stringa non è nulla
     */
    public static final IValidator<String> notNullString =  GenericValidation.from(s ->  s != null);
    
    /**
     * Validazione con condizione che verifica se una stringa non è vuota
     */
    public static final IValidator<String> notEmptyString = GenericValidation.from(s -> !s.isEmpty());
    
    /**
     * Validazione con condizione che verifica se una stringa è un intero
     */
    public static final IValidator<String> isInteger = GenericValidation.from(s -> isInteger(s));
    
    /**
     * Validazione con condizione che verifica se il primo carattere di una stringa non sia una cifra
     */
    public static final IValidator<String> notFirstCharIsDigit = GenericValidation.from(s -> Character.isDigit(s.charAt(0)) != true);
    
    /**
     * Validazione con condizione che verifica se una stringa è un double
     */
    public static final IValidator<String> isDouble = GenericValidation.from(s -> isDouble(s));
    
    /**
     * Validazione con condizione che verifica se un intero non è nullo
     */
    public static final IValidator<Integer> notNullInteger = GenericValidation.from(i -> i != null);
    
    
    /**
     * Validazione con condizione che verifica se il formato dell'email inserita è corretta
     */
    public static final IValidator<String> isValidEmail = GenericValidation.from(s -> testEmailFormat(s));
    
    /**
     * Questo metodo crea un validatore con condizione che verifica se la lunghezza
     * di una stringa è maggiore di quella passata a parametro
     * 
     * @param length la lunghezza minima per la quale la stringa deve essere maggiore
     * @return un validatore generico con condizione che verifica se la lunghezza di una stringa è maggiore 
     * di quella passata a parametro
     */
    public static final IValidator<String> stringMoreThan(int length) {
        return GenericValidation.from(s -> ((String) s).length() > length);
    };
    
    
    /**
     * Questo metodo crea un validatore con condizione che verifica se la lunghezza
     * di una stringa è minore di quella passata a parametro
     * 
     * @param length la lunghezza massima per la quale la stringa deve essere minore
     * @return un validatore generico con condizione che verifica se la lunghezza di una stringa è minore 
     * di quella passata a parametro
     */
    public static final IValidator<String> stringLessThan(int length) {
        return GenericValidation.from(s -> ((String) s).length() < length);
    };
    
    
    /**
     * Questo metodo crea un validatore con condizione che verifica se la lunghezza
     * di una stringa è compresa tra i valori passati a parametro
     * 
     * @param param1 la lunghezza minima dell'intervallo della lunghezza della stringa consentita
     * @param param2 la lunghezza massima dell'intervallo della lunghezza della stringa consentita
     * @return un validatore generico con condizione che verifica se la lunghezza di una stringa sia minore 
     * di quella passata a parametro
     */
    public static final IValidator<String> stringBetween(int param1, int param2) {
        return stringMoreThan(param1).and(stringLessThan(param2));
    };
    

    /**
     * Questo metodo crea un validatore con condizione che verifica se l'intero
     * è maggiore di quello passato a parametro
     * 
     * @param intBase  l'intero minimo per il quale l'intero deve essere maggiore
     * @return un validatore generico con condizione che verifica se l'intero è maggiore di quello
     * passato a parametro
     */
    public static final IValidator<Integer> intMoreThan(int intBase) {
        return GenericValidation.from(s -> s > intBase);
    };
    
    
    /**
     * Questo metodo crea un validatore con condizione che verifica se l'intero
     * è minore di quello passato a parametro
     * 
     * @param intBase l'intero massimo per il quale l'intero deve essere minore
     * @return un validatore generico con condizione che verifica se l'intero è minore di quello
     * passato a parametro
     */
    public static final IValidator<Integer> intLessThan(int intBase) {
        return GenericValidation.from(s -> s < intBase);
    };
    
    
    /**
     * Questo metodo crea un validatore con condizione che verifica se l'intero
     * è compreso tra i valori passati a parametro
     * 
     * @param value1 l'intero minimo dell'intervallo in cui deve essere compreso l'intero
     * @param value2 l'intero massimo dell'intervallo in cui deve essere compreso l'intero
     * @return un validatore generico con condizione che verifica se l'intero è minore di quello
     * passato a parametro
     */
    public static final IValidator<Integer>intBetween(int value1, int value2) {
        return intMoreThan(value1).and(intLessThan(value2));
    }; 
    
    
    /**
     * Verifica se l'email passata a parametro rispetta il formato standard
     * 
     * @param email la mail il cui formato deve essere testato
     * @return true se email rispetta la sintassi prevista, false altrimenti
     */
    public static boolean testEmailFormat(String email) {
        return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
    }
    
    /**
     * Metodo isInteger, verifica se la stringa passata a parametro è un intero
     * 
     * @param input la stringa di test
     * @return true se la stringa passata a parametro corrisponde ad un intero,
     * false altrimenti
     */
    public static boolean isInteger(String input) {
        try { 
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e) { 
            return false;
        }
    }
    
    
    /**
     * Metodo isDouble, verifica se la stringa passata a parametro è un double
     * 
     * @param input la stringa di test
     * @return true se la stringa passata a parametro corrisponde ad un double,
     * false altrimenti
     */
    public static boolean isDouble(String input) {
        try { 
            Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException e) { 
            return false;
        }
    } 
}
