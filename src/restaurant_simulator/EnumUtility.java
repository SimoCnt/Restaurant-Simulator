package restaurant_simulator;

/**
 * Classe EnumUtility, fornisce dei metodi utility per le Enumerazioni
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class EnumUtility {
    
    /**
     * Questo metodo restituisce il valore di un Enum senza caratteri underscore
     * 
     * @param enumeration l'enum da splittare
     * @return il valore dell'enum con la sostituzione del carattere underscore
     * con uno spazio
     */
    public static String splitEnum(Enum enumeration) {
        return String.valueOf(enumeration).replace("_", " ");
    }
}
