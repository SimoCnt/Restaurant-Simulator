package restaurant_simulator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Classe che fornisce i metodi per la funzione di scramble della password e di
 * autenticazione utilizzando l'algoritmo di hashing SHA-1
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class PasswordHasher {
       
    private static final String algorithm = "SHA-1";	
    
    /**
     * Questo metodo cripta la password data in input utilizzando l'algoritmo
     * di hashing SHA-1 sfruttando la classe java.security.MessageDigest, che 
     * fornisce l'implementazione di diverse funzioni di hash
     * 
     * @param password la password che deve essere criptata
     * @return null se si verificano delle eccezioni, altrimenti torna il numero
     * esadecimale
     */
    public static String scramble(String password) {
    	try {
            // ritorna un oggetto di tipo MessageDigest che applica l'algoritmo dato a parametro
            MessageDigest md = MessageDigest.getInstance(algorithm);
            
            // aggiorna la password di input in un message digest (stringa binaria di dimensione fissa, o valore di hash);
            // la funzione MessageDigest.digest() esegue l'aggiornamento finale e calcola il message digest dell'input
            md.update(password.getBytes("UTF-8") );
            return convertToHex(md.digest());
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) { 
            return null;
        }
    }
    
    
    /**
     * Questo metodo converte il message digest di input in un valore esadecimale
     * 
     * @param messageDigest il message digest da convertire in esadecimale
     * @return la stringa del valore esadecimale del message digest
     */
    private static String convertToHex(byte[] messageDigest)  {
    	StringBuilder sb = new StringBuilder();
    	for (byte b : messageDigest) {
            String value = Integer.toString(b & 0xFF, 16);
            if (value.length() == 1) {
                sb.append("0");
		}
            sb.append(value);
            }
	return sb.toString();
    }
    
     
    /**
     * Questo metodo confronta la stringa di una password con la corrispondente
     * password hashata
     * 
     * @param password la password
     * @param hash l'hash della password
     * @return true se la corrispondenza è verificata, false altrimenti
     */
    public static boolean verify( String password, String hash) {
    	return hash.equals(scramble(password));
    } 
}
