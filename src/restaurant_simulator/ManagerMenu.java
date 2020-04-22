package restaurant_simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 * Interfaccia grafica ManagerMenu, permette di visualizzare il menu creato
 * dall'utente e di integeragire su di esso attraverso dei comandi
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class ManagerMenu extends javax.swing.JFrame {
    
    /**
     * Il menu del ristorante
     */
    private Menu menu;
        
    
    /**
     * Costruttore senza parametri della classe ManagerMenu
     */
    public ManagerMenu() {
        super("Restaurant Simulator (MM): Menu");
        initComponents();
        this.setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Costruttore con parametri della classe ManagerMenu
     * 
     * @param menu il menu da visualizzare
     */
    public ManagerMenu(Menu menu) {
        super("Restaurant Simulator (MM): Menu");
        initComponents();
        this.menu = menu;
        
        // Renderizzo il menu
        printMenuToJTextPane();
        
        btnBack.addActionListener(
            ev -> {
                ManagerHome managerMenu = new ManagerHome();
                managerMenu.setVisible(true);
                this.setVisible(false);
            });
        
        btnPrint.addActionListener(
            ev -> {
                ManagerMenuOperationExecutor managerMenuOperationExecutor = new ManagerMenuOperationExecutor();
                // Approccio funzionale
                managerMenuOperationExecutor.executeOperation(this::printMenuWithPrinter);
            });
        
        btnDownloadMenu.addActionListener(
            ev -> {
                ManagerMenuOperationExecutor managerMenuOperationExecutor = new ManagerMenuOperationExecutor();
                // Approccio funzionale
                managerMenuOperationExecutor.executeOperation(this::saveMenuToFile);
            });
        
        btnSettingsMenu.addActionListener(
            ev -> {
                this.setVisible(false);
                RestaurantMenuConfig menuConfig = new RestaurantMenuConfig(this.menu);
                menuConfig.setVisible(true);
            });

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    /**
     * Questo metodo permette di salvare il menu visualizzato nell'area di rendering
     * del menu (txtPaneMenu) su un file di testo
     */
    public void saveMenuToFile(){
        // se l'area di output non è vuota
        if (!txtPaneMenu.getText().isEmpty()) {
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser(); // permette di scegliere dove salvare il file
            fileChooser.setSelectedFile(new File("output.txt"));
            FileNameExtensionFilter txt = new FileNameExtensionFilter("File di testo", "txt");
            fileChooser.setFileFilter(txt);
            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                String path = fileToSave.getAbsolutePath();
                try {
                    File file = new File(path);
                    file.getParentFile().mkdirs();
                    FileWriter fw = new FileWriter(file);
                    fw.write(txtPaneMenu.getText());
                    JOptionPane.showMessageDialog(rootPane, "File salvato correttamente nella directory: "+path);
                    System.out.println("File salvato in: " + fileToSave.getAbsolutePath());
                    fw.flush();
                    fw.close();
                }
                catch(IOException e) {
                    JOptionPane.showMessageDialog(rootPane, "Si è verificato un errore nel salvataggio del file: "+path, "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Nessun menu da stampare", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    /**
     * Questo metodo permette di invocare la funzione stampa del proprio computer
     * per stampare il menu visualizzato nella relativa JTextPane
     */
    public void printMenuWithPrinter() {
        IPrintJTextComponent menu = new PrintJTextPaneWithPrinter(txtPaneMenu);
        menu.printJTextComponent();
    }
    
    
    /**
     * Questo metodo si occupa di effettuare il rendering del menu nella corrispettiva
     * JTextPane
     */
    private void printMenuToJTextPane() {
        if (menu.getProductCategory().size()>0) {
            try {
                StyledDocument document = (StyledDocument) txtPaneMenu.getDocument();
                txtPaneMenu.setText(""); // Resetto il contenuto della TextPane
                SimpleAttributeSet categoryAttributeSet = new SimpleAttributeSet();
                StyleConstants.setFontFamily(categoryAttributeSet, "Libre Baskerville");
                StyleConstants.setFontSize(categoryAttributeSet, 24);
                StyleConstants.setItalic(categoryAttributeSet, true);
                
                for (int i=0; i<menu.getProductCategory().size(); i++) {
                    if (menu.getProductCategory().get(i).getProducts().size()>0) {
                        document.insertString(document.getLength(), menu.getProductCategory().get(i).toString()+"\n\n", categoryAttributeSet);
                        List<Product> products = menu.getProductCategory().get(i).getProducts();

                        SimpleAttributeSet productAttributeSet = new SimpleAttributeSet();
                        StyleConstants.setFontFamily(productAttributeSet, "Libre Baskerville");
                        for (Product product : products) {
                            StyleConstants.setFontSize(productAttributeSet, 18);
                            document.insertString(document.getLength(), product.getName()+"  € "+product.getPrice()+"\n",  productAttributeSet);
                            StyleConstants.setFontSize(productAttributeSet, 14);
                            document.insertString(document.getLength(), String.join(", ", product.getIngredients())+"\n",  productAttributeSet);
                            document.insertString(document.getLength(), "\n",  productAttributeSet);
                        }
                    }

                }
            } catch(BadLocationException e) {
                JOptionPane.showMessageDialog(rootPane, "Errore: non è stato possibile stampare il menu", "ErrorBox", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        btnSettingsMenu = new javax.swing.JButton();
        lblMenu = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPaneMenu = new javax.swing.JTextPane();
        btnDownloadMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        btnSettingsMenu.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        btnSettingsMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnSettingsMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/settings.png"))); // NOI18N
        btnSettingsMenu.setText("Impostazioni");
        btnSettingsMenu.setBorder(null);
        btnSettingsMenu.setContentAreaFilled(false);
        btnSettingsMenu.setFocusPainted(false);
        btnSettingsMenu.setFocusable(false);
        btnSettingsMenu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnSettingsMenu.setMargin(new java.awt.Insets(2, 0, 2, 0));
        btnSettingsMenu.setPreferredSize(new java.awt.Dimension(140, 32));

        lblMenu.setFont(new java.awt.Font("Muli", 1, 24)); // NOI18N
        lblMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu.setText("Menu");

        btnPrint.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/printer.png"))); // NOI18N
        btnPrint.setText("Stampa");
        btnPrint.setBorder(null);
        btnPrint.setContentAreaFilled(false);
        btnPrint.setFocusPainted(false);
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnPrint.setMargin(new java.awt.Insets(2, 0, 2, 0));
        btnPrint.setPreferredSize(new java.awt.Dimension(100, 32));

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setToolTipText("Torna indietro");
        btnBack.setBorder(null);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setFocusable(false);
        btnBack.setPreferredSize(new java.awt.Dimension(38, 32));

        jScrollPane2.setViewportView(txtPaneMenu);

        btnDownloadMenu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDownloadMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnDownloadMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/download.png"))); // NOI18N
        btnDownloadMenu.setText("Scarica");
        btnDownloadMenu.setToolTipText("Download");
        btnDownloadMenu.setBorder(null);
        btnDownloadMenu.setContentAreaFilled(false);
        btnDownloadMenu.setFocusPainted(false);
        btnDownloadMenu.setFocusable(false);
        btnDownloadMenu.setMargin(new java.awt.Insets(2, 0, 2, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMenu)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
                        .addComponent(btnDownloadMenu)
                        .addGap(0, 0, 0)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnSettingsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSettingsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDownloadMenu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addComponent(lblMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManagerMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDownloadMenu;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSettingsMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JTextPane txtPaneMenu;
    // End of variables declaration//GEN-END:variables
}
