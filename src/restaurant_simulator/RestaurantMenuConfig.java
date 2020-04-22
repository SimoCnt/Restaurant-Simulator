package restaurant_simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Interfaccia grafica RestaurantMenuConfig, rappresenta l'interfaccia che permette
 * di configurare il menu del ristorante
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class RestaurantMenuConfig extends javax.swing.JFrame {
    
    /**
     * Il modello della tabella degli ingredienti
     */
    private DefaultTableModel tableModelIngrediedients;
    
    /**
     * Il modello della tabella dei prodotti
     */
    private DefaultTableModel tableModelProducts;
   
    /**
     * Il menu del ristorante
     */
    private Menu menu;
    
    /**
     * La lista dei prodotti temporanei da aggiungere al menu
     */
    private List<Product> productsTemp;
    
    /**
     * Mapping dei prodotti del menu. La chiave rappresenta l'indice della
     * riga in cui si trova il prodotto nella JTable. Il valore è un booleano
     * che indica se il prodotto è editabile\modificabile nella tabella.
     */
    private Map<Integer, Boolean> productMap;
    
    /**
     * Mapping delle categorie del menu. La chiave rappresenta l'indice della
     * della categoria nella combobox. Il valore è il nome della categoria.
     */
    private Map<Integer, String> cbCategoryMap;
    
    private final String fileCategoryPath = "data/Category.csv";
    private final String fileProductPath = "data/Product.csv";
        
    
    /**
     * Costruttore senza parametri della classe RestaurantMenuConfig
     */
    public RestaurantMenuConfig() {
        super("Restaurant Simulator (MM): Configurazione del menu");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    
    /**
     * Costruttore con parametri della classe RestaurantMenuConfig
     * 
     * @param menu il menu del ristorante
     */
    public RestaurantMenuConfig(Menu menu) {
        super("Restaurant Simulator (MM): Configurazione del menu");
        initComponents();
        this.menu = menu;
        this.productsTemp = new ArrayList<>();
        this.productMap = new HashMap<>();
        this.cbCategoryMap = new HashMap<>();
        
        // Inizializzo la combobox delle categorie
        int countCb = 0;
        for (ProductCategory pc : menu.getProductCategory()) {
            cbCategoryProduct.addItem(pc.toString());
            this.cbCategoryMap.put(countCb, pc.toString());
            countCb+=1;
        }
        
        // Inizializzo i modelli delle tabelle
        tableModelIngrediedients = (DefaultTableModel) tblIngredients.getModel();
        tableModelProducts = (DefaultTableModel) tblProducts.getModel();
        
        // Inserisco i prodotti creati in precedenza nella tabella
        int j = 0;
        for (ProductCategory item : menu.getProductCategory()) {
            Object[] row = new Object[4];
            List<Product> listProducts = item.getProducts();
            for (Product product : listProducts) {
                row[0] = item.getCategory().toString();
                row[1] = product.getName();
                row[2] = String.join(", ", product.getIngredients());
                row[3] = product.getPrice();
                this.productMap.put(j, false);
                j+=1;
                tableModelProducts.addRow(row);
            }
        }
        
        btnBack.addActionListener(
            ev -> {
            this.setVisible(false); 
               ManagerMenu managerMenu = new ManagerMenu(this.menu);
               managerMenu.setVisible(true);
            });
        
        btnAddIngredient.addActionListener(
            ev -> {
                Object[] row = new Object[1];
                if (!txtIngredientName.getText().equals("")) {
                    row[0] = txtIngredientName.getText();
                    tableModelIngrediedients.addRow(row);
                    txtIngredientName.setText("");
                    JOptionPane.showMessageDialog(this, "Ingrediente aggiunto", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Errore: Nessun ingrediente inserito", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        btnRemoveIngredient.addActionListener(
            ev -> {
                if (this.tblIngredients.getRowCount() > 0) {
                    switch (this.tblIngredients.getSelectedRows().length) {
                        case 0:
                            JOptionPane.showMessageDialog(rootPane, "Seleziona un ingrediente da rimuovere", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 1:
                            int resultRemove = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere l'ingrediente selezionato?", "ConfirmBox", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (resultRemove==JOptionPane.YES_OPTION) {
                                tableModelIngrediedients.removeRow(tblIngredients.getSelectedRow());
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "È possibile rimuovere soltanto un ingrediente alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun ingrediente presente", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        
        btnAddProduct.addActionListener(
            ev -> {
                RestaurantMenuConfigValidator validator = new RestaurantMenuConfigValidator();

                boolean isValidModel = false;

                try {
                    isValidModel = validator.validate(this);
                    if (isValidModel) {
                        Product product = new Product();

                        // Setto un id temporaneo per il prodotto
                        product.setId(this.tableModelProducts.getRowCount());

                        Object[] row = new Object[4];
                        String categorySelected = cbCategoryProduct.getItemAt(cbCategoryProduct.getSelectedIndex());
                        product.setCategoryId(this.menu.getIdByCategoryName(categorySelected));
                        row[0] = categorySelected;

                        if (!txtNameProduct.getText().equals("")) {
                            row[1] = txtNameProduct.getText();
                        }

                        product.setName(txtNameProduct.getText());

                        int rows = tableModelIngrediedients.getRowCount();
                        String ingredients = "";
                        for (int i=rows-1; i>=0; i--) {
                            if (i!=0) {
                                ingredients += tableModelIngrediedients.getValueAt(i, 0)+", ";
                            }
                            else {
                                ingredients += tableModelIngrediedients.getValueAt(i, 0);
                            }
                            tableModelIngrediedients.removeRow(i);
                        }

                        product.setIngredients(Arrays.asList(ingredients.replace(" ", "").split(",")));              
                        row[2] = ingredients;
                        String text = txtProductPrice.getText().replace(",", ".");
                        double priceValue = 0.0d;
                        priceValue = DoubleUtility.round(Double.parseDouble(txtProductPrice.getText().replace(",", ".")), 2);
                        product.setPrice(priceValue);
                        row[3] = priceValue;

                        // Aggiungo il prodotto alla lista dei prodotti temporanei
                        this.productsTemp.add(product);

                        // Segno il prodotto inserito come Editabile
                        this.productMap.put(tblProducts.getRowCount(), true);

                        tableModelProducts.addRow(row);

                        txtNameProduct.setText("");
                        txtIngredientName.setText("");
                        txtProductPrice.setText("");
                    }
                } catch(RestaurantConfigException e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(), "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        btnRemoveProduct.addActionListener(
            ev -> {
                if (this.tblProducts.getRowCount() > 0) {
                    switch (this.tblProducts.getSelectedRows().length) {
                        case 0:
                            JOptionPane.showMessageDialog(rootPane, "Seleziona un prodotto da rimuovere", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 1:
                            if (!this.productMap.get(this.tblProducts.getSelectedRow())) {
                                JOptionPane.showMessageDialog(rootPane, "Non è possibile rimuovere un prodotto creato in precedenza", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                int resultRemove = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere il prodotto selezionato?", "ConfirmBox", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (resultRemove==JOptionPane.YES_OPTION) {
                                    // Rimuovo il prodotto dai prodotti temporanei
                                    for (int i=0; i<this.productsTemp.size(); i++) {
                                        if (this.productsTemp.get(i).getId()==this.tblProducts.getSelectedRow()) {
                                            this.productsTemp.remove(this.productsTemp.get(i));
                                        }
                                    }
                                    // Rimuovo il prodotto dal productMap
                                    this.productMap.remove(this.tblProducts.getSelectedRow());
 
                                    // Rimuovo la riga dalla JTable
                                    tableModelProducts.removeRow(tblProducts.getSelectedRow());
                                }
                            }   break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "È possibile rimuovere soltanto un prodotto alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun prodotto presente", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        
        btnEdit.addActionListener(
            ev -> {
                if (this.tblProducts.getRowCount() > 0) {
                    switch (this.tblProducts.getSelectedRows().length) {
                        case 0:
                            JOptionPane.showMessageDialog(rootPane, "Seleziona un prodotto da modificare", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 1:
                            if (!this.productMap.get(this.tblProducts.getSelectedRow())) {
                                JOptionPane.showMessageDialog(rootPane, "Non è possibile modificare un prodotto creato in precedenza", "InfoBox", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                int resultEdit = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler modificare il prodotto selezionato? Cliccando su Yes potrai modificare il tuo prodotto ma perderai i dati finora inseriti per l'aggiunta di un nuovo prodotto", "ConfirmBox", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (resultEdit==JOptionPane.YES_OPTION) {
                                int row = this.tblProducts.getSelectedRow();
                                txtNameProduct.setText(String.valueOf(this.tblProducts.getValueAt(row, 1)));
                                txtProductPrice.setText(String.valueOf(this.tblProducts.getValueAt(row, 3)));

                                // Ricavo la lista degli ingredienti
                                String[] ingredients = String.valueOf(this.tblProducts.getValueAt(row, 2)).split(", ");

                                // Aggiungo la lista degli ingredienti nell'apposita sezione solo se ho inserito almeno un ingrediente
                                if (ingredients.length > 0) {
                                    // Rimuovo eventuali ingredienti inseriti in precedenza
                                    for (int i=0; i<tableModelIngrediedients.getRowCount(); i++) {
                                        this.tableModelIngrediedients.removeRow(i);
                                    }
                                    Object[] rowIngredient = new Object[1];
                                    for (String ingredient : ingredients) {
                                        rowIngredient[0] = ingredient;
                                        this.tableModelIngrediedients.addRow(rowIngredient);
                                    }
                                }
                                // Trovo la categoria corrispondente alla combobox
                                int key = -1;
                                for (Entry<Integer, String> entry : cbCategoryMap.entrySet()) {
                                    if ((String.valueOf(this.tblProducts.getValueAt(row, 0)).equals(entry.getValue()))) {
                                        key = entry.getKey();
                                    }
                                }

                                // Seleziono la categoria nel combobox solo se è stata trovata
                                if (key!=-1) {
                                    this.cbCategoryProduct.setSelectedIndex(key);
                                }
                                // Rimuovo il prodotto dai prodotti temporanei
                                for (int i=0; i<this.productsTemp.size(); i++) {
                                    if (this.productsTemp.get(i).getId()==row) {
                                        this.productsTemp.remove(this.productsTemp.get(i));
                                    }
                                }
                                // Rimuovo il prodotto dal productMap
                                this.productMap.remove(row);
                                this.tableModelProducts.removeRow(row);
                                }
                            }   break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "È possibile modificare soltanto un prodotto alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Nessun prodotto presente", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        
        btnSave.addActionListener(
            ev -> {
                // Aggiungo i nuovi prodotti al Csv
                if (this.productsTemp.size()>0) {
                    for (Product product : this.productsTemp) {
                        this.productMap.replace(product.getId(), false);
                        product.setId(CsvUtility.maxIntegerColumn(0, fileProductPath)+1);
                        for (ProductCategory category : this.menu.getProductCategory()) {
                            if (category.getId() == product.getCategoryId())
                                category.addProduct(product);
                        }
                        product.addRecord();
                    }
                    JOptionPane.showMessageDialog(this, "Salvataggio effettuato", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Non è stato aggiunto alcun prodotto", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    
    
    /**
     * Questo metodo permette di restituire il nome del prodotto inserito in input
     * 
     * @return il nome del prodotto inserito in input
     */
    public String getInputProductName() {
        return this.txtNameProduct.getText();
    }
    
    
    /**
     * Questo metodo permette di restituire il prezzo del prodotto inserito in input
     * 
     * @return il prezzo del prodotto inserito in input
     */
    public String getInputProductPrice() {
        return this.txtProductPrice.getText();
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
        lblProductName = new javax.swing.JLabel();
        txtNameProduct = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        lblCateogrySelect = new javax.swing.JLabel();
        cbCategoryProduct = new javax.swing.JComboBox<>();
        lblIngredientProduct = new javax.swing.JLabel();
        txtIngredientName = new javax.swing.JTextField();
        btnAddIngredient = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIngredients = new javax.swing.JTable();
        btnRemoveIngredient = new javax.swing.JButton();
        btnAddProduct = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        btnRemoveProduct = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtProductPrice = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));

        lblProductName.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        lblProductName.setForeground(new java.awt.Color(255, 255, 255));
        lblProductName.setText("Inserisci il nome del prodotto:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblCateogrySelect.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        lblCateogrySelect.setForeground(new java.awt.Color(255, 255, 255));
        lblCateogrySelect.setText("Seleziona categoria:");

        lblIngredientProduct.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        lblIngredientProduct.setForeground(new java.awt.Color(255, 255, 255));
        lblIngredientProduct.setText("Inserisci ingrediente:");

        btnAddIngredient.setText("+");

        tblIngredients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome Ingrediente"
            }
        ));
        jScrollPane1.setViewportView(tblIngredients);

        btnRemoveIngredient.setText("Rimuovi");

        btnAddProduct.setText("Aggiungi prodotto");

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Categoria", "Nome prodotto", "Ingredienti", "Prezzo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblProducts);
        if (tblProducts.getColumnModel().getColumnCount() > 0) {
            tblProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblProducts.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProducts.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblProducts.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        btnRemoveProduct.setText("Rimuovi");

        btnSave.setText("Salva");

        jLabel1.setFont(new java.awt.Font("Muli", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Inserisci prezzo:");

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setBorder(null);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setFocusable(false);
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        btnEdit.setText("Modifica");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblProductName)
                                        .addComponent(lblCateogrySelect)
                                        .addComponent(cbCategoryProduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblIngredientProduct)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(txtNameProduct)
                                        .addComponent(btnRemoveIngredient, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(txtIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                            .addComponent(btnAddIngredient))
                                        .addComponent(jLabel1)
                                        .addComponent(txtProductPrice))
                                    .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnEdit)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRemoveProduct))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSave)))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnRemoveProduct)
                                    .addComponent(btnEdit)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblProductName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCateogrySelect)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbCategoryProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIngredientProduct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIngredientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddIngredient))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(btnRemoveIngredient)))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddProduct)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
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
            java.util.logging.Logger.getLogger(RestaurantMenuConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RestaurantMenuConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RestaurantMenuConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RestaurantMenuConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestaurantMenuConfig().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddIngredient;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRemoveIngredient;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbCategoryProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCateogrySelect;
    private javax.swing.JLabel lblIngredientProduct;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JTable tblIngredients;
    private javax.swing.JTable tblProducts;
    private javax.swing.JTextField txtIngredientName;
    private javax.swing.JTextField txtNameProduct;
    private javax.swing.JTextField txtProductPrice;
    // End of variables declaration//GEN-END:variables
}
