package restaurant_simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Interfaccia grafica RestaurantTableConfig, si occupa della gestione dei tavoli
 * del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantTableConfig extends javax.swing.JFrame {
    
    private final String fileTablePath = "data/Table.csv";
    
    /**
     * La lista dei tavoli del ristorante
     */
    private List<Table> tables;
    
    /**
     * La lista dei tavoli temporanei che vogliamo aggiungere al ristorante
     */
    private List<Table> tablesTemp;
    
    /**
     * Modello della JTable relativa ai tavoli
     */
    private DefaultTableModel tableModel;
    
    /**
     * Mapping dei tavoli del ristorante. La chiave rappresenta l'indice della
     * riga in cui si trova il tavolo nella JTable. Il valore è un booleano
     * che indica se il tavolo è editabile nella JTable.
     */
    private Map<Integer, Boolean> tableMap;
    
    
    /**
     * Costruttore senza parametri della classe RestaurantTableConfig
     */
    public RestaurantTableConfig() {
        super("Restaurant Simulator (MM): Impostazioni tavoli");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    
    /**
     * Costruttore con parametri della classe RestaurantTableConfig
     * 
     * @param tables la lista dei tavoli del ristorante
     */
    public RestaurantTableConfig(List<Table> tables) {
        super("Restaurant Simulator (MM): Impostazioni tavoli");
        initComponents();
        this.tables = tables;
        this.tableMap = new HashMap<>();
        this.tablesTemp = new ArrayList<>();
        tableModel = (DefaultTableModel) tblTables.getModel();
        
        Object[] row_table = new Object[2];
        int countRow = 0;
        for(Table table: tables) {
            row_table[0] = table.getName();
            row_table[1] = table.getNumSeats();
            this.tableMap.put(countRow, false);
            countRow+=1;
            tableModel.addRow(row_table);
        }
        
        // Nascondo i componenti di modifica
        hideComponentEditTemp();
        
        btnBack.addActionListener(
            ev -> {
                ManagerHome rmh = new ManagerHome();
                rmh.setVisible(true);
                this.setVisible(false);
            });
        
        btnAdd.addActionListener(
            ev -> {
                RestaurantTableConfigValidator validator = new RestaurantTableConfigValidator();
                boolean validModel = false;

                // Effettuo la validazione dei campi per l'aggiunta di un tavolo
                try{
                    validModel = validator.validate(this, 0);
                }
                catch(RestaurantConfigException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }

                // Posso procedere con la fase successiva solo se il "modello" (campi per l'aggiunta di un tavolo) sono validi
                if (validModel) {

                    // Condizioni che permettono di stabilire se è presente un tavolo con lo stesso nome di quello che si vuole inserire
                    boolean controlTableList = tables.stream().filter(o -> o.getName().equals(this.txtTableName.getText())).findFirst().isPresent();
                    boolean controlTableTempList = tablesTemp.stream().filter(o -> o.getName().equals(this.txtTableName.getText())).findFirst().isPresent();

                    if (!controlTableList && !controlTableTempList) {
                        int columns = this.tblTables.getColumnCount();
                        Table table = new Table();
                        // Setto un id temporaneo per il tavolo (ovvero l'id della riga corrispondente alla JTable)
                        table.setId(this.tblTables.getRowCount());
                        // Setto un nome per il tavolo temporaneo
                        table.setName(txtTableName.getText());
                        // Setto il numero di posti per il tavolo temporaneo
                        table.setNumSeats(Integer.parseInt(txtNumSeatsTable.getText()));
                        // Aggiungo il tavolo alla lista temporanea
                        this.tablesTemp.add(table);

                        // Creo un array contente i valori da aggiungere alla riga successiva della tabella
                        Object[] row = new Object[columns];
                        row[0] = txtTableName.getText();
                        row[1] = txtNumSeatsTable.getText();

                        // Inserisco nella mappa tableMap che l'ultima riga che sta per essere aggiunta è editabile
                        this.tableMap.put(this.tblTables.getRowCount(), true);

                        // Aggiungo la riga alla tabella dei tavoli
                        this.tableModel.addRow(row);

                        // Comunico che il tavolo richiesto è stato inserito in tabella
                        JOptionPane.showMessageDialog(this, "Tavolo aggiunto", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Errore: il tavolo che si sta cercando di inserire è esistente", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                    }
            });
        
        btnEdit.addActionListener(
            ev -> {
                if (this.tblTables.getRowCount() > 0) {
                    if (this.tblTables.getSelectedRows().length == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Seleziona un tavolo da modificare", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (this.tblTables.getSelectedRows().length == 1) {
                        if (!this.tableMap.get(this.tblTables.getSelectedRow())) {
                            JOptionPane.showMessageDialog(rootPane, "Non è possibile modificare un tavolo creato in precedenza", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            tblTables.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                                public void valueChanged(ListSelectionEvent lse) {
                                    if (!lse.getValueIsAdjusting()) {
                                        resetComponentEditTemp();
                                        hideComponentEditTemp();
                                    }
                                }
                            });
                            showComponentEditTemp();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, "È possibile modificare soltanto un tavolo alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun tavolo presente", "InfoBox", JOptionPane.WARNING_MESSAGE);
                };
            });
        
        btnEdit2.addActionListener(
            ev -> {
                RestaurantTableConfigValidator validator = new RestaurantTableConfigValidator();
                
                boolean validModel = false;

                try{
                    validModel = validator.validate(this, 1);
                }
                catch(RestaurantConfigException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }

                if (validModel) {
                    if (!tables.stream().filter(o -> o.getName().equals(this.txtTableName)).findFirst().isPresent()) {
                        this.tblTables.getSelectedRow();
                        int columns = this.tblTables.getColumnCount();
                        Object[] row = new Object[columns];
                        row[0] = txtNewTableName.getText();
                        row[1] = txtNewNumSeatsTable.getText();
                        this.tableModel.setValueAt(txtNewTableName.getText(), this.tblTables.getSelectedRow(), 0);
                        this.tableModel.setValueAt(txtNewNumSeatsTable.getText(), this.tblTables.getSelectedRow(), 1);
                        // modifico il tavolo nella lista dei tavoli temporanei
                        for (Table table : this.tablesTemp) {
                            if (table.getId()==this.tblTables.getSelectedRow()) {
                                table.setName(txtNewTableName.getText());
                                table.setNumSeats(Integer.parseInt(txtNewNumSeatsTable.getText()));
                            }
                        }
                        resetComponentEditTemp();
                        hideComponentEditTemp();
                        JOptionPane.showMessageDialog(this, "Modifica effettuata", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Errore: il tavolo che si sta cercando di inserire è esistente", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        
        btnCancel.addActionListener(
            ev -> {
                resetComponentEditTemp();
                hideComponentEditTemp();
            });
        
        btnSave.addActionListener(
            ev -> {
                if (this.tablesTemp.size() > 0) {
                    for (Table table : this.tablesTemp) {
                        this.tableMap.replace(table.getId(), false);
                        table.setId(CsvUtility.maxIntegerColumn(0, fileTablePath)+1);
                        table.addRecord();
                    }
                    JOptionPane.showMessageDialog(rootPane, "Salvataggio effettuato", "InfoBox", JOptionPane.INFORMATION_MESSAGE);        
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun tavolo da salvare", "InfoBox", JOptionPane.INFORMATION_MESSAGE);        
                }
            });
        
        btnRemove.addActionListener(
            ev -> {
                if (this.tblTables.getRowCount() > 0) {
                    if (this.tblTables.getSelectedRows().length == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Seleziona un tavolo da rimuovere", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if (this.tblTables.getSelectedRows().length == 1) {
                        if (!this.tableMap.get(this.tblTables.getSelectedRow())) {
                            JOptionPane.showMessageDialog(rootPane, "Non è possibile rimuovere un tavolo creato in precedenza");
                        }
                        else {
                            int resultRemove = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere il tavolo selezionato?", "ConfirmBox", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (resultRemove==JOptionPane.YES_OPTION) {
                                int i = tblTables.getSelectedRow();
                                if (i >= 0) {
                                    this.tablesTemp.remove(this.tablesTemp.stream().filter(o -> o.getId() == this.tblTables.getSelectedRow()).findFirst().get());
                                    tableModel.removeRow(i);
                                }
                                else {
                                    JOptionPane.showMessageDialog(this, "Errore: non è stato possibile rimuovere il tavolo selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, "È possibile rimuovere soltanto un tavolo alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun tavolo presente", "InfoBox", JOptionPane.WARNING_MESSAGE);
                }
            });
        

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Questo metodo restituisce il nome del tavolo inserito in input
     * 
     * @return il nome del tavolo inserito in input
     */
    public String getInputTableName() {
        return this.txtTableName.getText();
    }
    
    
    /**
     * Questo metodo restituisce il numero di posti del tavolo inseriti in input
     * 
     * @return il numero di posti del tavolo inserito in input
     */
    public String getInputNumSeatsTable() {
        return this.txtNumSeatsTable.getText();
    }
    
    
    /**
     * Questo metodo restituisce il nuovo nome del tavolo da modificare inserito input
     * 
     * @return il nuovo nome del tavolo da modificare modificare inserito in input
     */
    public String getInputNewTableName() {
        return this.txtNewTableName.getText();
    }
    
    
    /**
     * Questo metodo restituisce il nuovo numero di posti che si vogliono attribuire al tavolo da modificare inserito in input
     * 
     * @return il nuovo numero di posti che si vogliono attribuire al tavolo da modificare inserito in input 
     */
    public String getInputNewNumSeatsTable() {
        return this.txtNewNumSeatsTable.getText();
    }
    
    /**
     * Questo metodo resetta i campi di modifica temporanea del tavolo
     */
    public void resetComponentEditTemp() {
        txtNewNumSeatsTable.setText("");
        txtNewTableName.setText("");
    }
    
    /**
     * Questo metodo nasconde i componenti grafici della modifica temporanea del tavolo
     */
    public final void hideComponentEditTemp() {
        this.lblNewNumSeatsTable.setVisible(false);
        this.lblNewTableName.setVisible(false);
        this.txtNewNumSeatsTable.setVisible(false);
        this.txtNewTableName.setVisible(false);
        this.btnEdit2.setVisible(false);
        this.btnCancel.setVisible(false);
    }
    
    /**
     * Questo metodo mostra i componenti grafici della modifica temporanea del tavolo
     */
    public final void showComponentEditTemp() {
        this.lblNewNumSeatsTable.setVisible(true);
        this.lblNewTableName.setVisible(true);
        this.txtNewNumSeatsTable.setVisible(true);
        this.txtNewTableName.setVisible(true);
        this.btnEdit2.setVisible(true);
        this.btnCancel.setVisible(true);
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
        txtTableName = new javax.swing.JTextField();
        lblTableName = new javax.swing.JLabel();
        txtNumSeatsTable = new javax.swing.JTextField();
        lblNumSeatsTable = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTables = new javax.swing.JTable();
        btnAdd = new javax.swing.JToggleButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblEditTemp = new javax.swing.JLabel();
        lblNewTableName = new javax.swing.JLabel();
        txtNewTableName = new javax.swing.JTextField();
        lblNewNumSeatsTable = new javax.swing.JLabel();
        txtNewNumSeatsTable = new javax.swing.JTextField();
        btnEdit2 = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        txtTableName.setToolTipText("Inserisci il nome del tavolo che vuoi aggiungere");
        txtTableName.setPreferredSize(new java.awt.Dimension(7, 25));

        lblTableName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTableName.setForeground(new java.awt.Color(255, 255, 255));
        lblTableName.setText("Inserisci il nome del tavolo:");

        txtNumSeatsTable.setToolTipText("Inserisci il nome del tavolo che vuoi aggiungere");
        txtNumSeatsTable.setPreferredSize(new java.awt.Dimension(7, 25));

        lblNumSeatsTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNumSeatsTable.setForeground(new java.awt.Color(255, 255, 255));
        lblNumSeatsTable.setText("Inserisci il numero dei posti:");

        tblTables.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblTables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome tavolo", "Numero posti"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblTables);

        btnAdd.setText("Aggiungi");
        btnAdd.setMaximumSize(new java.awt.Dimension(73, 25));
        btnAdd.setPreferredSize(new java.awt.Dimension(73, 25));

        btnEdit.setText("Modifica");

        btnRemove.setText("Rimuovi");

        btnSave.setText("Salva");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNumSeatsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTableName)
                            .addComponent(lblNumSeatsTable))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(btnRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTableName)
                .addGap(11, 11, 11)
                .addComponent(txtTableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblNumSeatsTable)
                .addGap(11, 11, 11)
                .addComponent(txtNumSeatsTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(45, 52, 54));

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setMargin(new java.awt.Insets(2, 0, 2, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(45, 52, 54));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblEditTemp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEditTemp.setForeground(new java.awt.Color(255, 255, 255));
        lblEditTemp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEditTemp.setText("Modifiche temporanee");

        lblNewTableName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNewTableName.setForeground(new java.awt.Color(255, 255, 255));
        lblNewTableName.setText("Inserisci il nome del nuovo tavolo:");

        txtNewTableName.setPreferredSize(new java.awt.Dimension(60, 25));

        lblNewNumSeatsTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNewNumSeatsTable.setForeground(new java.awt.Color(255, 255, 255));
        lblNewNumSeatsTable.setText("Inserisci il numero di posti del nuovo tavolo:");

        txtNewNumSeatsTable.setPreferredSize(new java.awt.Dimension(60, 25));

        btnEdit2.setText("Modifica");

        btnCancel.setText("Annulla");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblEditTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtNewTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblNewTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblNewNumSeatsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNewNumSeatsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEdit2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblEditTemp)
                        .addGap(20, 20, 20)
                        .addComponent(lblNewTableName)
                        .addGap(11, 11, 11)
                        .addComponent(txtNewTableName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblNewNumSeatsTable)
                        .addGap(11, 11, 11)
                        .addComponent(txtNewNumSeatsTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnEdit2)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(20, 20, 20))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(RestaurantTableConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RestaurantTableConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RestaurantTableConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RestaurantTableConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestaurantTableConfig().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit2;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblEditTemp;
    private javax.swing.JLabel lblNewNumSeatsTable;
    private javax.swing.JLabel lblNewTableName;
    private javax.swing.JLabel lblNumSeatsTable;
    private javax.swing.JLabel lblTableName;
    private javax.swing.JTable tblTables;
    private javax.swing.JTextField txtNewNumSeatsTable;
    private javax.swing.JTextField txtNewTableName;
    private javax.swing.JTextField txtNumSeatsTable;
    private javax.swing.JTextField txtTableName;
    // End of variables declaration//GEN-END:variables
}
