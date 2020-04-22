package restaurant_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 * Interfaccia grafica UserAuthentication, si occupa dell'autenticazione del
 * cliente e della visualizzazione dei feedback del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserAuthentication extends javax.swing.JFrame {
    
    /**
     * La lista delle categorie dei feedback con relativi feedback
     */
    private List<CategoryFeedback> categoryFeedbacks;
    
    /**
     * Lista delle etichette delle categorie dei feedback
     */
    private List<JLabel> lblCFeedback;
    
    /**
     * Lista delle progressBar che indicano la media dei feedback delle varie categorie
     */
    private List<JProgressBar> pbFeedbackList;
    
    
    private final String fileUserPath = "data/User.csv";
     
    
    /**
     * Costruttore senza parametri della classe UserAuthentication
     */
    public UserAuthentication() {
        super("Restaurant Simulator: Autenticazione");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Costruttore con parametri della classe UserAuthentication
     * 
     * @param categoryFeedback la lista delle categorie dei feedback con i relativi feedback
     */
    public UserAuthentication(List<CategoryFeedback> categoryFeedback) {
        super("Restaurant Simulator: Autenticazione");
        initComponents();
        this.categoryFeedbacks = categoryFeedback;
        
        // Inizializzo la lista delle progressbar
        this.pbFeedbackList = new ArrayList<>();
        this.pbFeedbackList.add(pbCFeedback1);
        this.pbFeedbackList.add(pbCFeedback2);
        this.pbFeedbackList.add(pbCFeedback3);
        
        // Inizializzo la lista delle label delle categorie dei feedback
        this.lblCFeedback = new ArrayList<>();
        this.lblCFeedback.add(lblCFeedback1);
        this.lblCFeedback.add(lblCFeedback2);
        this.lblCFeedback.add(lblCFeedback3);
        
        // Visualizzo i feedback solo se presenti
        if (!this.categoryFeedbacks.stream().allMatch(f -> f.getFeedback().size()>0)) {
            hideFeedback();
        }
        else {
            showFeedback();
        }
        
        
        // Popola gli indicatori della media dei feedback
        for (int i=0; i<this.categoryFeedbacks.size(); i++) {
            for (int j=0; j<this.pbFeedbackList.size(); j++) {
                this.lblCFeedback.get(i).setText(this.categoryFeedbacks.get(i).getName());                
                AverageCalculator averageCalculator = new AverageCalculator();
                ArithmeticAverage arithmeticAverage = new ArithmeticAverage(this.categoryFeedbacks.get(i).getFeedback().stream().map(Feedback::getValue).collect(Collectors.toList()));
                averageCalculator.calculateAverage(arithmeticAverage);
                double averageRound = DoubleUtility.round(arithmeticAverage.getResult(), 1);
                int valueAverage = (int) (averageRound*10);
                this.pbFeedbackList.get(i).setValue(valueAverage);
                this.pbFeedbackList.get(i).setString(String.valueOf(averageRound));
            }
        }
        
        btnLogin.addActionListener(
            ev -> {
                IUserAuthenticationValidator userLoginValidator = new UserLoginValidator();

                boolean validModel = false;

                try {
                    validModel = userLoginValidator.validate(this);
                } catch (UserAuthenticationException e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(),"ErrorBox", JOptionPane.ERROR_MESSAGE);
                }

                if (validModel) {
                    String emailLogin = txtEmailLogin.getText();        
                    String passwordLogin = String.valueOf(txtPasswordLogin.getPassword());

                    ICheckUser checkUser = new CheckUser();

                    // Controllo la corrispondenza dei dati dell'utente in User.csv
                    if(checkUser.checkUserCorrispondenceLogin(emailLogin, passwordLogin)){            

                        // Creo l'utente
                        UserBuilderDirector ubd = new UserBuilderDirector();
                        UserConcreteBuilder ucb = new UserConcreteBuilder();
                        User user = ubd.buildLoggedUser(ucb, emailLogin, passwordLogin);

                        //System.out.println("Utente loggato: " + user.getUsername() + " | " + user.getEmail() + " | " + user.getPassword());
                        this.dispose();
                        UserHome userHome = new UserHome(user);
                        userHome.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Errore: credenziali non corrette", "ErrorBox: " + "Error message", 
                                JOptionPane.ERROR_MESSAGE);            
                    }     
                }
            });
        
        btnSignUp.addActionListener(
            ev -> {
                UserSignUpValidator userSignUpValidator = new UserSignUpValidator();

                boolean validModel = false;

                try {
                    validModel = userSignUpValidator.validate(this);
                } catch (UserAuthenticationException e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(),"ErrorBox", JOptionPane.ERROR_MESSAGE);
                }

                if (validModel) {
                    String username = txtUsernameSignUp.getText();
                    String email = txtEmailSignUp.getText();
                    String password = PasswordHasher.scramble(String.valueOf(txtPswSignUp.getPassword()));
                    String passwordSignUpConfirm = PasswordHasher.scramble(String.valueOf(txtConfirmPsw.getPassword()));

                    ICheckUser checkUser = new CheckUser();

                    if(checkUser.checkCorrispondenceUsername(username)){
                        if(checkUser.checkCorrispondenceEmail(email)){           
                            if(password.equals(passwordSignUpConfirm)){
                                User userTemp = new User();
                                userTemp.setId(CsvUtility.maxIntegerColumn(0, fileUserPath)+1);
                                userTemp.setUsername(username);
                                userTemp.setEmail(email);
                                userTemp.setPassword(password);
                                userTemp.addRecord();
                                // Popup di conferma registrazione
                                JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "InfoBox: " + "Success", JOptionPane.INFORMATION_MESSAGE);
                                resetFieldsRegistration();
                            }                                
                            else{
                                JOptionPane.showMessageDialog(rootPane, "Errore: Password non corrispondenti","ErrorBox", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(rootPane, "Errore: email già esistente","ErrorBox", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Errore: username già esistente","ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        
             
        lblRestaurantName.setText(RestaurantService.getTableNameByIdFromCsv(0));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Questo metodo restituisce l'email inserita in input per il login
     * 
     * @return l'email del login inserita in input
     */
    public String getInputEmailLogin() {
        return txtEmailLogin.getText();
    }
    
    /**
     * Questo metodo restituisce la password inserita in input per il login
     * 
     * @return la password del login inserita in input
     */
    public String getInputPasswordLogin() {
        return String.valueOf(txtPasswordLogin.getPassword());
    }
    
    /**
     * Questo metodo restituisce l'username inserito input nella registrazione
     * 
     * @return l'username inserito input nella registrazione
     */
    public String getInputUsernameRegistration() {
        return txtUsernameSignUp.getText();
    }
    
    
    /**
     * Questo metodo restituisce l'email inserita in input nella registrazione
     * 
     * @return l'email inserita in input nella registrazione
     */
    public String getInputEmailRegistration() {
        return txtEmailSignUp.getText();
    }
    
    /**
     * Questo metodo restituisce la password inserita in input nella registrazione
     * 
     * @return la password inserita in input nella registrazione
     */
    public String getInputPasswordRegistration() {
        return String.valueOf(txtPswSignUp.getPassword());
    }
    
    /**
     * Questo metodo resetta i campi di registrazione
     */
    public void resetFieldsRegistration() {
        txtUsernameSignUp.setText("");
        txtEmailSignUp.setText("");
        txtPswSignUp.setText("");
        txtConfirmPsw.setText("");
    }

    /**
     * Questo metodo nasconde il pannello relativo ai feedback
     */
    public void hideFeedback() {
        jPanel4.setVisible(false);
    }
    
    /**
     * Questo metodo mostra il pannellor relativo ai feedback
     */
    public void showFeedback() {
        jPanel4.setVisible(true);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtEmailLogin = new javax.swing.JTextField();
        lblEmailLogin = new javax.swing.JLabel();
        lblPasswordLogin = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtPasswordLogin = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        lblAccount = new javax.swing.JLabel();
        txtUsernameSignUp = new javax.swing.JTextField();
        txtEmailSignUp = new javax.swing.JTextField();
        btnSignUp = new javax.swing.JButton();
        lblUsernameSignUp = new javax.swing.JLabel();
        lblEmailSignUp = new javax.swing.JLabel();
        lblPasswordSignUp = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtPswSignUp = new javax.swing.JPasswordField();
        txtConfirmPsw = new javax.swing.JPasswordField();
        lblConfirmPsw = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblIcoRestaurantSimulator = new javax.swing.JLabel();
        lblRestaurantName = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblFeedback1 = new javax.swing.JLabel();
        lblCFeedback1 = new javax.swing.JLabel();
        lblCFeedback2 = new javax.swing.JLabel();
        lblCFeedback3 = new javax.swing.JLabel();
        pbCFeedback1 = new javax.swing.JProgressBar();
        pbCFeedback2 = new javax.swing.JProgressBar();
        pbCFeedback3 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Restaurant Simulator", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 0));

        lblEmailLogin.setForeground(new java.awt.Color(254, 254, 254));
        lblEmailLogin.setText("Email");

        lblPasswordLogin.setForeground(new java.awt.Color(254, 254, 254));
        lblPasswordLogin.setText("Password");

        btnLogin.setBackground(new java.awt.Color(0, 204, 102));
        btnLogin.setText("Login");

        txtPasswordLogin.setPreferredSize(new java.awt.Dimension(150, 20));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmailLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmailLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLogin))
                    .addComponent(lblPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPasswordLogin)
                    .addComponent(lblEmailLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin))
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(45, 52, 54));

        lblAccount.setFont(new java.awt.Font("Ubuntu", 1, 20)); // NOI18N
        lblAccount.setForeground(new java.awt.Color(255, 255, 255));
        lblAccount.setText("Crea un account");

        btnSignUp.setBackground(new java.awt.Color(255, 153, 0));
        btnSignUp.setText("Crea un account");

        lblUsernameSignUp.setForeground(new java.awt.Color(255, 255, 255));
        lblUsernameSignUp.setText("Username:");

        lblEmailSignUp.setForeground(new java.awt.Color(255, 255, 255));
        lblEmailSignUp.setText("Email:");

        lblPasswordSignUp.setForeground(new java.awt.Color(255, 255, 255));
        lblPasswordSignUp.setText("Password:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblConfirmPsw.setForeground(new java.awt.Color(255, 255, 255));
        lblConfirmPsw.setText("Conferma password:");

        jPanel3.setBackground(new java.awt.Color(45, 52, 54));

        lblIcoRestaurantSimulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/chef.png"))); // NOI18N
        lblIcoRestaurantSimulator.setToolTipText("");

        lblRestaurantName.setFont(new java.awt.Font("Muli", 1, 18)); // NOI18N
        lblRestaurantName.setForeground(new java.awt.Color(254, 254, 254));
        lblRestaurantName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRestaurantName.setText("Il mio ristorante");
        lblRestaurantName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblRestaurantName.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblRestaurantName.setPreferredSize(new java.awt.Dimension(260, 24));
        lblRestaurantName.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIcoRestaurantSimulator)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblRestaurantName, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblIcoRestaurantSimulator)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRestaurantName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(45, 52, 54));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblFeedback1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFeedback1.setForeground(new java.awt.Color(255, 255, 0));
        lblFeedback1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFeedback1.setText("Il giudizio dei nostri clienti");
        lblFeedback1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblCFeedback1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCFeedback1.setForeground(new java.awt.Color(255, 255, 255));
        lblCFeedback1.setText("Categoria Feedback 1");

        lblCFeedback2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCFeedback2.setForeground(new java.awt.Color(255, 255, 255));
        lblCFeedback2.setText("Categoria Feedback 2");

        lblCFeedback3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCFeedback3.setForeground(new java.awt.Color(255, 255, 255));
        lblCFeedback3.setText("Categoria Feedback 3");

        pbCFeedback1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pbCFeedback1.setStringPainted(true);

        pbCFeedback2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pbCFeedback2.setStringPainted(true);

        pbCFeedback3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pbCFeedback3.setStringPainted(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFeedback1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbCFeedback1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbCFeedback2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCFeedback1)
                            .addComponent(lblCFeedback2)
                            .addComponent(lblCFeedback3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pbCFeedback3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblFeedback1)
                .addGap(11, 11, 11)
                .addComponent(lblCFeedback1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCFeedback1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCFeedback2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCFeedback2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCFeedback3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCFeedback3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsernameSignUp)
                                    .addComponent(lblAccount))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPasswordSignUp)
                                    .addComponent(lblEmailSignUp))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 134, Short.MAX_VALUE)
                                        .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtEmailSignUp)
                                    .addComponent(txtUsernameSignUp, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPswSignUp))
                                .addGap(20, 20, 20))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtConfirmPsw)
                                .addGap(20, 20, 20))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblConfirmPsw)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblAccount)
                        .addGap(13, 13, 13)
                        .addComponent(lblUsernameSignUp)
                        .addGap(10, 10, 10)
                        .addComponent(txtUsernameSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblEmailSignUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmailSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblPasswordSignUp)
                        .addGap(10, 10, 10)
                        .addComponent(txtPswSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblConfirmPsw)
                        .addGap(10, 10, 10)
                        .addComponent(txtConfirmPsw, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(btnSignUp)
                        .addGap(0, 95, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserAuthentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAuthentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAuthentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAuthentication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAuthentication().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAccount;
    private javax.swing.JLabel lblCFeedback1;
    private javax.swing.JLabel lblCFeedback2;
    private javax.swing.JLabel lblCFeedback3;
    private javax.swing.JLabel lblConfirmPsw;
    private javax.swing.JLabel lblEmailLogin;
    private javax.swing.JLabel lblEmailSignUp;
    private javax.swing.JLabel lblFeedback1;
    private javax.swing.JLabel lblIcoRestaurantSimulator;
    private javax.swing.JLabel lblPasswordLogin;
    private javax.swing.JLabel lblPasswordSignUp;
    private javax.swing.JLabel lblRestaurantName;
    private javax.swing.JLabel lblUsernameSignUp;
    private javax.swing.JProgressBar pbCFeedback1;
    private javax.swing.JProgressBar pbCFeedback2;
    private javax.swing.JProgressBar pbCFeedback3;
    private javax.swing.JPasswordField txtConfirmPsw;
    private javax.swing.JTextField txtEmailLogin;
    private javax.swing.JTextField txtEmailSignUp;
    private javax.swing.JPasswordField txtPasswordLogin;
    private javax.swing.JPasswordField txtPswSignUp;
    private javax.swing.JTextField txtUsernameSignUp;
    // End of variables declaration//GEN-END:variables
}
