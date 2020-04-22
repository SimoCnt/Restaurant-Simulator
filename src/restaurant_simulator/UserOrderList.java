package restaurant_simulator;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyleContext;


/**
 * Interfaccia grafica UserOrderList, si occupa della visualizzazione degli ordini
 * effettuati dal cliente, della visualizzazione della ricevuta per ogni ordine
 * selezionato e del rilascio dei feedback per il relativo ordine.
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserOrderList extends javax.swing.JFrame {
    
    /**
     * Il cliente loggato
     */
    User user;
    
   /**
     * Mapping degli ordini del cliente. La chiave rappresenta l'indice della
     * posizione in cui si trova l'ordine nella relativa combobox, mentre il valore rappresenta
     * l'id dell'ordine.
     */
    Map<Integer, Integer> orderMap = new HashMap<>();
    
    
    /**
     * Costruttore senza parametri della classe UserOrderList
     */
    public UserOrderList() {
        super("Restaurant Simulator (MC): I miei ordini");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    
    /**
     * Costruttore con parametri della classe UserOrderList
     * @param user il cliente loggato
     */
    public UserOrderList(User user) {
        super("Restaurant Simulator (MC): I miei ordini");
        initComponents();
        this.user = user;
        
        // Inizializzazione della combobox degli ordini
        for (int i=0; i<user.getOrders().size(); i++) {
            orderMap.put(i, user.getOrders().get(i).getId());
            cbOrderList.addItem(user.getOrders().get(i).getId() + "    | " + DateUtility.formatDateWithTime(user.getOrders().get(i).getData()));
        }
        
        // Listener pulsante back
        btnBack.addActionListener(
            ev -> {
                UserHome uh = new UserHome(this.user);
                this.setVisible(false);
                uh.setVisible(true);
            });
        
        // Listener rilascia feedback
        btnFeedbackRelease.addActionListener(
            ev -> {
                // Ricavo l'indice dell'elemento selezionato nella ComboBox
                int index = cbOrderList.getSelectedIndex();
                // Ricavo l'id dell'ordine corrispondente all'elemento selezionato
                int orderId = orderMap.get(index);
                // Ricavo l'ordine in base all'id
                Order order = this.user.getOrderByOrderId(orderId).get();
                Date dataOrderCompare = DateUtility.addHour(DateUtility.removeSecondsAndMilliseconds(order.getData()), 1);
                if(order.getState() instanceof OrderCompletedState){
                    if (!order.isFeedbackReleased()) {
                        ReleasedFeedback releasedFeedback = new ReleasedFeedback(user, order);
                        this.setVisible(false);
                        releasedFeedback.setVisible(true);
                        releasedFeedback.toFront();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Errore: è già stato rilasciato un feedback per l'ordine selezionato", "ErrorBox: " + "Error message", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Errore: l'ordine selezionato non è stato ancora completato", "ErrorBox: " + "Error message", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        // Listener combobox lista ordini
        cbOrderList.addActionListener(
            ev -> {
                // Ricavo l'indice dell'elemento selezionato nella ComboBox
                int index = cbOrderList.getSelectedIndex();
                // Ricavo l'id dell'ordine corrispondente all'elemento selezionato
                int orderId = orderMap.get(index);

                try {
                    Order order = this.user.getOrderByOrderId(orderId).get();
                    StyledDocument document = (StyledDocument) txtPaneOrderDetail.getDocument();
                    StyleContext context = new StyleContext();
                    txtPaneOrderDetail.setText(""); // Resetto il contenuto della TextPane
                    String separator = "------------------------------------------------------------------------------------------------";
                    document.insertString(document.getLength(), separator, null);
                    document.insertString(document.getLength(), "\n Id ordine: "+order.getId()+"\n", null);

                    document.insertString(document.getLength(), "\n Data: "+DateUtility.formatDateWithTime(order.getData())+"\n", null);
                    Style style = context.addStyle("", null);
                    if (order.getState() instanceof OrderPaidState) {
                        StyleConstants.setForeground(style, Color.BLUE);
                    }
                    else if (order.getState() instanceof OrderCompletedState) {
                        StyleConstants.setForeground(style, Color.GREEN);
                    }
                    document.insertString(document.getLength(), "\n Stato: ", null);
                    document.insertString(document.getLength()-1, " "+order.getState().toString(), style);
                    document.insertString(document.getLength(), separator, null);
                    for (OrderItem item: order.getOrderItems()) {
                        document.insertString(document.getLength(), "\n " +item.getProductName()+ " € "+item.getPrice()+"\n", null);
                    }
                    document.insertString(document.getLength(), separator, null);
                    document.insertString(document.getLength(), "\n Totale: "+" € "+order.getTotalPrice(), null);
                } catch(NullPointerException | BadLocationException e) {
                    JOptionPane.showMessageDialog(rootPane, "Errore: Impossibile elaborare l'ordine selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        // Listener pulsante visualizza ricevuta
        btnViewReceipt.addActionListener(
            ev -> {
                // Ricavo l'indice dell'elemento selezionato nella ComboBox
                int index = cbOrderList.getSelectedIndex();
                // Ricavo l'id dell'ordine corrispondente all'elemento selezionato
                int orderId = orderMap.get(index);

                // Ricavo l'ordine in base all'id
                Optional<Order> order = this.user.getOrderByOrderId(orderId);

                UserReceipt receipt = new UserReceipt(order.get());
                receipt.setVisible(true);
            });
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        lblSelectOrder = new javax.swing.JLabel();
        cbOrderList = new javax.swing.JComboBox<>();
        btnViewReceipt = new javax.swing.JButton();
        btnFeedbackRelease = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPaneOrderDetail = new javax.swing.JTextPane();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        lblSelectOrder.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSelectOrder.setForeground(new java.awt.Color(255, 255, 255));
        lblSelectOrder.setText("Seleziona ordine:");

        cbOrderList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOrderListItemStateChanged(evt);
            }
        });

        btnViewReceipt.setText("Visualizza ricevuta");
        btnViewReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewReceiptActionPerformed(evt);
            }
        });

        btnFeedbackRelease.setText("Rilascia feedback");
        btnFeedbackRelease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFeedbackReleaseActionPerformed(evt);
            }
        });

        txtPaneOrderDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Dettaglio"));
        jScrollPane2.setViewportView(txtPaneOrderDetail);

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setToolTipText("Torna indietro");
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setMargin(new java.awt.Insets(2, 0, 2, 0));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addComponent(cbOrderList, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSelectOrder)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnViewReceipt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                                .addComponent(btnFeedbackRelease)))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addGap(20, 20, 20)
                .addComponent(lblSelectOrder)
                .addGap(11, 11, 11)
                .addComponent(cbOrderList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewReceipt)
                    .addComponent(btnFeedbackRelease))
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

    private void btnFeedbackReleaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFeedbackReleaseActionPerformed

    }//GEN-LAST:event_btnFeedbackReleaseActionPerformed

    private void cbOrderListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOrderListItemStateChanged
        
    }//GEN-LAST:event_cbOrderListItemStateChanged

    private void btnViewReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReceiptActionPerformed

        
        
    }//GEN-LAST:event_btnViewReceiptActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(UserOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserOrderList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnFeedbackRelease;
    private javax.swing.JButton btnViewReceipt;
    private javax.swing.JComboBox<String> cbOrderList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSelectOrder;
    private javax.swing.JTextPane txtPaneOrderDetail;
    // End of variables declaration//GEN-END:variables
}
