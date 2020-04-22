package restaurant_simulator;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Interfaccia Grafica ManagerOrderDetail, mostra il dettaglio dell'ordine selezionato
 * nella GUI dello storico ordini della Modalità Manager
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerOrderDetail extends javax.swing.JFrame {
    
    /**
     * L'ordine di visualizzare il dettaglio
     */
    private Order order;
    
    /**
     * Il nome del ristorante
     */
    private String restaurantName;
    
    
    /**
     * Costruttore senza parametri della classe ManagerOrderDetail
     */
    public ManagerOrderDetail() {
        super("Dettaglio ordine: ");
        initComponents();
    }
    
    /**
     * Costruttore con parametri della classe ManagerOrderDetail
     * 
     * @param restaurantName il nome del ristorante
     * @param order l'ordine di cui visualizzare il dettaglio
     */
    public ManagerOrderDetail(String restaurantName, Order order) {
        super("Dettaglio ordine: "+order.getId());
        initComponents();
        this.restaurantName = restaurantName;
        this.order = order;
        printOrderDetail();
        
        btnViewReceipt.addActionListener(
            ev -> {
                ManagerReceipt mr = new ManagerReceipt(this.restaurantName, this.order);
                mr.setVisible(true);
            });
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPaneOrderDetail = new javax.swing.JTextPane();
        btnViewReceipt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        txtPaneOrderDetail.setBorder(javax.swing.BorderFactory.createTitledBorder("Dettaglio"));
        jScrollPane1.setViewportView(txtPaneOrderDetail);

        btnViewReceipt.setText("Visualizza ricevuta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(261, Short.MAX_VALUE)
                        .addComponent(btnViewReceipt)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnViewReceipt)
                .addContainerGap(20, Short.MAX_VALUE))
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
     * Renderizza il dettaglio dell'ordine nella relativa area (txtPaneOrderDetail)
     */
    public void printOrderDetail() {
        try {
            StyledDocument document = (StyledDocument) txtPaneOrderDetail.getDocument();
            StyleContext context = new StyleContext();
            txtPaneOrderDetail.setText(""); // Resetto il contenuto della TextPane
            String separator = "------------------------------------------------------------------------------------------------";
            document.insertString(document.getLength(), separator, null);
            document.insertString(document.getLength(), "\n Id ordine: "+order.getId()+"\n", null);
            document.insertString(document.getLength(), "\n Id utente: "+this.order.getUserId()+"\n", null);
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
            java.util.logging.Logger.getLogger(ManagerOrderDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerOrderDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerOrderDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerOrderDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerOrderDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnViewReceipt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtPaneOrderDetail;
    // End of variables declaration//GEN-END:variables
}