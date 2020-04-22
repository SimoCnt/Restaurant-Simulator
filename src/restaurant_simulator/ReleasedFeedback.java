package restaurant_simulator;

import javax.swing.ButtonGroup;


/**
 * Interfaccia grafica ReleasedFeedback, permette di effettuare il rilascio
 * di un feedback per un determinato ordine effettuato da un cliente
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ReleasedFeedback extends javax.swing.JFrame {
    
    private final String fileFeedbackPath = "data/Feedback.csv";    
    private final String fileOrderPath = "data/Order.csv";
    
    /**
     * Il cliente
     */
    private User user;
    
    /**
     * L'ordine per cui rilasciare il feedback
     */
    private Order order;
    
    /**
     * Gruppo di JRadioButton relativo alla categoria dei feedback Servizio
     */
    private ButtonGroup bg1;
    
    /**
     * Gruppo di JRadioButton relativo alla categoria dei feedback Rapporto qualità\prezzo
     */
    private ButtonGroup bg2;
    
    /**
     * Gruppo di JRadioButton relativo alla categoria dei feedback Ambiente
     */
    private ButtonGroup bg3;
    
    
    /**
     * Costruttore senza parametri della classe ReleasedFeedback
     */
    public ReleasedFeedback() {
        super("Rilascia feedback");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Costruttore con parametri della classe ReleasedFeedback
     * 
     * @param user il cliente che rilascia il feedback
     * @param order l'ordine per cui si sta rilasciando il feedback
     */
    public ReleasedFeedback(User user, Order order) {
        super("Rilascia feedback");
        initComponents();
        this.user = user;
        this.order = order;
        
        rbtnBad1.setActionCommand("2");
        rbtnBad2.setActionCommand("2");
        rbtnBad3.setActionCommand("2");
        
        rbtnInsufficient1.setActionCommand("4");
        rbtnInsufficient2.setActionCommand("4");
        rbtnInsufficient3.setActionCommand("4");
        
        rbtnSufficient1.setActionCommand("6");
        rbtnSufficient2.setActionCommand("6");
        rbtnSufficient3.setActionCommand("6");
        
        rbtnGood1.setActionCommand("8");
        rbtnGood2.setActionCommand("8");
        rbtnGood3.setActionCommand("8");
        
        rbtnExcellent1.setActionCommand("10");
        rbtnExcellent2.setActionCommand("10");
        rbtnExcellent3.setActionCommand("10");
        
        bg1 = generateButtonGroup1();
        bg2 = generateButtonGroup2(); 
        bg3 = generateButtonGroup3();
        
        btnBack.addActionListener(
            ev -> {
                UserOrderList uol = new UserOrderList(this.user);
                uol.setVisible(true);
                this.setVisible(false);
            });
        
        btnRelease.addActionListener(
            ev -> {
                
                // Update di Feedback.csv (categoria: Servizio)
                Feedback fbService = new Feedback();
                fbService.setId(CsvUtility.maxIntegerColumn(0, fileFeedbackPath)+1);
                fbService.setCategoryId(0);
                fbService.setOrderId(order.getId());
                fbService.setUserId(user.getId());
                fbService.setValue(Integer.parseInt(bg1.getSelection().getActionCommand()));
                fbService.addRecord();

                // Update di Feedback.csv (categoria: Rapporto qualità\prezzo)
                Feedback fbValueForMoney = new Feedback();
                fbValueForMoney.setId(CsvUtility.maxIntegerColumn(0, fileFeedbackPath)+1);
                fbValueForMoney.setCategoryId(1);
                fbValueForMoney.setOrderId(order.getId());
                fbValueForMoney.setUserId(user.getId());
                fbValueForMoney.setValue(Integer.parseInt(bg2.getSelection().getActionCommand()));
                fbValueForMoney.addRecord();

                // Update di Feedback.csv (categoria: Ambiente)
                Feedback fbEnvironment = new Feedback();
                fbEnvironment.setId(CsvUtility.maxIntegerColumn(0, fileFeedbackPath)+1);
                fbEnvironment.setCategoryId(2);
                fbEnvironment.setOrderId(order.getId());
                fbEnvironment.setUserId(user.getId());
                fbEnvironment.setValue(Integer.parseInt(bg3.getSelection().getActionCommand()));
                fbEnvironment.addRecord();

                this.order.setFeedbackReleased(true);
                this.order.updateRecord();

                // Setto a true la voce FeedbackRilasciato dell'ordine specifico
                for (int i=0; i<user.getOrders().size(); i++) {
                    if (user.getOrders().get(i).getId() == order.getId()) {
                       user.getOrders().get(i).setFeedbackReleased(true);
                    }
                }

                UserOrderList uol = new UserOrderList(this.user);
                uol.setVisible(true);
                this.setVisible(false);
            });
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        rbtnBad1 = new javax.swing.JRadioButton();
        rbtnInsufficient1 = new javax.swing.JRadioButton();
        rbtnSufficient1 = new javax.swing.JRadioButton();
        rbtnGood1 = new javax.swing.JRadioButton();
        rbtnExcellent1 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        rbtnBad2 = new javax.swing.JRadioButton();
        rbtnInsufficient2 = new javax.swing.JRadioButton();
        rbtnSufficient2 = new javax.swing.JRadioButton();
        rbtnGood2 = new javax.swing.JRadioButton();
        rbtnExcellent2 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        rbtnBad3 = new javax.swing.JRadioButton();
        rbtnInsufficient3 = new javax.swing.JRadioButton();
        rbtnSufficient3 = new javax.swing.JRadioButton();
        rbtnGood3 = new javax.swing.JRadioButton();
        rbtnExcellent3 = new javax.swing.JRadioButton();
        lblServiceFeedback = new javax.swing.JLabel();
        btnRelease = new javax.swing.JButton();
        lblValueForMoneyFeedback = new javax.swing.JLabel();
        lblEnvironmentFeedback = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        jPanel2.setBackground(new java.awt.Color(45, 52, 54));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        rbtnBad1.setForeground(new java.awt.Color(255, 255, 255));
        rbtnBad1.setText("Pessimo");
        rbtnBad1.setToolTipText("Pessimo");
        rbtnBad1.setContentAreaFilled(false);
        jPanel2.add(rbtnBad1);

        rbtnInsufficient1.setForeground(new java.awt.Color(255, 255, 255));
        rbtnInsufficient1.setText("Insufficiente");
        rbtnInsufficient1.setContentAreaFilled(false);
        jPanel2.add(rbtnInsufficient1);

        rbtnSufficient1.setForeground(new java.awt.Color(255, 255, 255));
        rbtnSufficient1.setText("Sufficiente");
        rbtnSufficient1.setContentAreaFilled(false);
        jPanel2.add(rbtnSufficient1);

        rbtnGood1.setForeground(new java.awt.Color(255, 255, 255));
        rbtnGood1.setSelected(true);
        rbtnGood1.setText("Buono");
        rbtnGood1.setContentAreaFilled(false);
        jPanel2.add(rbtnGood1);

        rbtnExcellent1.setForeground(new java.awt.Color(255, 255, 255));
        rbtnExcellent1.setText("Eccellente");
        rbtnExcellent1.setContentAreaFilled(false);
        jPanel2.add(rbtnExcellent1);

        jPanel3.setBackground(new java.awt.Color(45, 52, 54));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        rbtnBad2.setForeground(new java.awt.Color(255, 255, 255));
        rbtnBad2.setText("Pessimo");
        rbtnBad2.setToolTipText("Pessimo");
        rbtnBad2.setContentAreaFilled(false);
        jPanel3.add(rbtnBad2);

        rbtnInsufficient2.setForeground(new java.awt.Color(255, 255, 255));
        rbtnInsufficient2.setText("Insufficiente");
        rbtnInsufficient2.setContentAreaFilled(false);
        jPanel3.add(rbtnInsufficient2);

        rbtnSufficient2.setForeground(new java.awt.Color(255, 255, 255));
        rbtnSufficient2.setText("Sufficiente");
        rbtnSufficient2.setContentAreaFilled(false);
        jPanel3.add(rbtnSufficient2);

        rbtnGood2.setForeground(new java.awt.Color(255, 255, 255));
        rbtnGood2.setSelected(true);
        rbtnGood2.setText("Buono");
        rbtnGood2.setContentAreaFilled(false);
        jPanel3.add(rbtnGood2);

        rbtnExcellent2.setForeground(new java.awt.Color(255, 255, 255));
        rbtnExcellent2.setText("Eccellente");
        rbtnExcellent2.setContentAreaFilled(false);
        jPanel3.add(rbtnExcellent2);

        jPanel4.setBackground(new java.awt.Color(45, 52, 54));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        rbtnBad3.setForeground(new java.awt.Color(255, 255, 255));
        rbtnBad3.setText("Pessimo");
        rbtnBad3.setToolTipText("Pessimo");
        rbtnBad3.setContentAreaFilled(false);
        jPanel4.add(rbtnBad3);

        rbtnInsufficient3.setForeground(new java.awt.Color(255, 255, 255));
        rbtnInsufficient3.setText("Insufficiente");
        rbtnInsufficient3.setContentAreaFilled(false);
        jPanel4.add(rbtnInsufficient3);

        rbtnSufficient3.setForeground(new java.awt.Color(255, 255, 255));
        rbtnSufficient3.setText("Sufficiente");
        rbtnSufficient3.setContentAreaFilled(false);
        jPanel4.add(rbtnSufficient3);

        rbtnGood3.setForeground(new java.awt.Color(255, 255, 255));
        rbtnGood3.setSelected(true);
        rbtnGood3.setText("Buono");
        rbtnGood3.setContentAreaFilled(false);
        jPanel4.add(rbtnGood3);

        rbtnExcellent3.setForeground(new java.awt.Color(255, 255, 255));
        rbtnExcellent3.setText("Eccellente");
        rbtnExcellent3.setContentAreaFilled(false);
        jPanel4.add(rbtnExcellent3);

        lblServiceFeedback.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblServiceFeedback.setForeground(new java.awt.Color(255, 255, 0));
        lblServiceFeedback.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblServiceFeedback.setText("Servizio");

        btnRelease.setText("Rilascia");

        lblValueForMoneyFeedback.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblValueForMoneyFeedback.setForeground(new java.awt.Color(255, 255, 0));
        lblValueForMoneyFeedback.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblValueForMoneyFeedback.setText("Rapporto Qualità\\Prezzo");

        lblEnvironmentFeedback.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblEnvironmentFeedback.setForeground(new java.awt.Color(255, 255, 0));
        lblEnvironmentFeedback.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEnvironmentFeedback.setText("Ambiente");

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setToolTipText("Torna indietro");
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setMargin(new java.awt.Insets(2, 0, 2, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnRelease)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEnvironmentFeedback, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblValueForMoneyFeedback, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblServiceFeedback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addGap(20, 20, 20)
                .addComponent(lblServiceFeedback)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblValueForMoneyFeedback)
                .addGap(11, 11, 11)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblEnvironmentFeedback)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRelease)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Questo metodo permette di restituire il ButtonGroup relativo alla
     * categoria dei feedback Servizio
     * 
     * @return il ButtonGroup della categoria dei feedback Servizio
     */
    public ButtonGroup generateButtonGroup1() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbtnBad1);
        bg.add(rbtnInsufficient1);
        bg.add(rbtnSufficient1);
        bg.add(rbtnGood1);        
        bg.add(rbtnExcellent1);
        return bg;
    }
    
    
    /**
     * Questo metodo permette di restituire il ButtonGroup relativo alla
     * categoria dei feedback Rapporto qualità\prezzo
     * 
     * @return il ButtonGroup della categoria dei feedback Rapporto qualità\prezzo
     */
    public ButtonGroup generateButtonGroup2() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbtnBad2);
        bg.add(rbtnInsufficient2);
        bg.add(rbtnSufficient2);
        bg.add(rbtnGood2);        
        bg.add(rbtnExcellent2);
        return bg;
    }
    
    /**
     * Questo metodo permette di restituire il ButtonGroup relativo alla
     * categoria dei feedback Ambiente
     * 
     * @return il ButtonGroup della categoria dei feedback Ambiente
     */
    public ButtonGroup generateButtonGroup3() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbtnBad3);
        bg.add(rbtnInsufficient3);
        bg.add(rbtnSufficient3);
        bg.add(rbtnGood3);        
        bg.add(rbtnExcellent3);
        return bg;
    }

    
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
            java.util.logging.Logger.getLogger(ReleasedFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReleasedFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReleasedFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReleasedFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReleasedFeedback().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRelease;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblEnvironmentFeedback;
    private javax.swing.JLabel lblServiceFeedback;
    private javax.swing.JLabel lblValueForMoneyFeedback;
    private javax.swing.JRadioButton rbtnBad1;
    private javax.swing.JRadioButton rbtnBad2;
    private javax.swing.JRadioButton rbtnBad3;
    private javax.swing.JRadioButton rbtnExcellent1;
    private javax.swing.JRadioButton rbtnExcellent2;
    private javax.swing.JRadioButton rbtnExcellent3;
    private javax.swing.JRadioButton rbtnGood1;
    private javax.swing.JRadioButton rbtnGood2;
    private javax.swing.JRadioButton rbtnGood3;
    private javax.swing.JRadioButton rbtnInsufficient1;
    private javax.swing.JRadioButton rbtnInsufficient2;
    private javax.swing.JRadioButton rbtnInsufficient3;
    private javax.swing.JRadioButton rbtnSufficient1;
    private javax.swing.JRadioButton rbtnSufficient2;
    private javax.swing.JRadioButton rbtnSufficient3;
    // End of variables declaration//GEN-END:variables

}
