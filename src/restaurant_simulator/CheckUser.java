package restaurant_simulator;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Questa classe fornisce i metodi per i check delle corrispondenze nel file
 * User.csv
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class CheckUser implements ICheckUser{
    
    private final String fileUsersPath = "data/User.csv";
    
    
    /**
     * Questo metodo permette di effettuare un check in User.csv dell'email e della
     * password digitati dall'utente nella fase di login
     * 
     * @param emailLogin l'email digitata per il login
     * @param passwordLogin password digitata per il login
     * @return true se viene trovato un utente in User.csv con la email e password a parametro,
     * false altrimenti
     */
    public boolean checkUserCorrispondenceLogin(String emailLogin, String passwordLogin){
        
        try {           
            String line = "";
            FileReader file = new FileReader(fileUsersPath);
            BufferedReader br = new BufferedReader(file);
            line = br.readLine();
            
            while((line = br.readLine()) != null) {                
                String[] data = line.split(";");
                
                // Se la corrispondenza esiste, entra qui
                if(data[2].equals(emailLogin) && PasswordHasher.verify(passwordLogin, data[3])) {
                    return true;                                       
                }                
            }
            
            } catch(Exception e){
                System.out.println("Errore durante la lettura del database users");
        }
        return false;
        
    }
    
    
    /**
     * Questo metodo permette di verificare che l'username scelto in fase di registrazione
     * non sia già stato utilizzato da un altro utente
     * 
     * @param username username scelto per la registrazione
     * @return true se non esiste l'username scelto in User.csv, false viceversa
     */
    public boolean checkCorrispondenceUsername(String username){
        
        try {           
            String line = "";
            FileReader file = new FileReader(fileUsersPath);
            BufferedReader br = new BufferedReader(file);
            line = br.readLine();
            
            while((line = br.readLine()) != null) {                
                String[] data = line.split(";");
                
                // Se la corrispondenza esiste, entra qui
                if(data[1].equals(username)) {
                    return false;                                       
                }                
            }
            
            } catch(Exception e){
                System.out.println("Errore durante la lettura del database users");
        }
        return true;
        
    }
    
    
    /**
     * Questo metodo permette di verificare che l'email scelta in fase di registrazione
     * non sia già stata utilizzata da un altro utente
     * 
     * @param email email scelta per la registrazione
     * @return true se non esiste l'email scelta in User.csv, false altrimenti
     */
    public boolean checkCorrispondenceEmail(String email){
        
        try {           
            String line = "";
            FileReader file = new FileReader(fileUsersPath);
            BufferedReader br = new BufferedReader(file);
            line = br.readLine();
            
            while((line = br.readLine()) != null) {                
                String[] data = line.split(";");
                
                // Se la corrispondenza esiste, entra qui
                if(data[2].equals(email)) {
                    return false;                                       
                }                
            }
            
            } catch(Exception e){
                System.out.println("Errore durante la lettura del database users");
        }
        return true;
        
    }
    
}
