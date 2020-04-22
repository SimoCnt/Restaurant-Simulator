package restaurant_simulator;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Interfaccia grafica UserReservation, rappresenta l'interfaccia che permette
 * di effettuare una prenotazione presso il ristorante creato.
 * 
 * Per prenotazione si intende il completamento dei seguenti step nell'ordine elencato:
 * - Scelta di una data valida
 * - Scelta di una o più consumazioni e conferma
 * - Pagamento
 * 
 * @author Danilo Dolce e Simone Contini
 * @version 1.0
 */
public class UserReservation extends javax.swing.JFrame {
    
    /**
     * Il modello della tabella delle consumazioni scelte
     */
    private DefaultTableModel tableModelProducts;
    
    /**
     * Il cliente che effettua la prenotazione
     */
    private User user;
    
    /**
     * Il menu offerto dal ristorante
     */
    private Menu menu;
    
   /**
     * Mapping delle categorie del menu. La chiave rappresenta l'indice della
     * posizione in cui si trova la categoria nella combobox, mentre il valore rappresenta
     * l'id della categoria.
     */
    private Map<Integer, Integer> categoriesMap = new HashMap<>();
    
   /**
     * Mapping dei prodotti relativi ad una categoria. La chiave rappresenta l'indice della
     * posizione in cui si trova il prodotto nella relativa combobox, mentre il valore rappresenta
     * l'id del prodotto.
     */
    private Map<Integer, Integer> productsMap = new HashMap<>();
    
    
    /**
     * Mapping degli ingredienti degli enum. La chiave rappresenta l'indice della
     * posizione in cui si trova l'ingrediente nella relativa combobox, mentre il valore rappresenta
     * un valore dell'enum.
     */
    private Map<Integer, String> enumMap = new HashMap<>();
    
    /**
     * L'id del tavolo assegnato
     */
    private int idTable;
    
    /**
     * La data temporanea per cui di vuole prenotare
     */
    private Date dateTemp;
    
    /**
     * Il numero di clienti temporaneo per cui si vuole prenotare
     */
    private int numClientsTemp;
    
    /**
     * L'orario temporaneo per cui si vuole effettuare una prenotazione
     */
    private String timeTemp;
    
    /**
     * Attributo che indica se è stata effettuata la verifica della disponibilità
     * del tavolo
     */
    private boolean checked = false;
    
    /**
     * Attributo che indica lo stato della validazione dei dati inseriti
     */
    private boolean isValid = false;
    
    private final String fileTablesPath = "data/Table.csv";
    private final String fileOrderPath = "data/Order.csv";
    private final String fileOrderItemPath = "data/OrderItem.csv";
        

    /**
     * Costruttore senza parametri della classe UserReservation
     */
    public UserReservation() {
        super("Restaurant Simulator (MC): Prenotazione");
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    
    /**
     * Costruttore con parametri della classe UserReservation
     * 
     * @param menu il menu del ristorante
     * @param user il cliente loggato
     */
    public UserReservation(Menu menu, User user) {
        super("Restaurant Simulator (MC): Prenotazione");
        initComponents();
        this.menu = menu;
        this.user = user;
        
        // Setto le dimensioni dei JPanel
        viewComponents(jPanel3, false);
        jPanel3.setPreferredSize(new Dimension(580, 525));
        viewComponents(jPanel6, false);
        jPanel6.setPreferredSize(new Dimension(325, 525));
        jPanel1.setPreferredSize(new Dimension(315, 525));

        // Inizializzo la combobox delle categorie
        cbCategoryProduct.addItem("Nessuna categoria selezionata");
        cbCategoryProduct.setSelectedIndex(0);
        // Aggiungo una categoria solo se contiene prodotti
        for (int i=0; i<this.menu.getCategoriesWithAtLeastAProduct().size(); i++) {
            cbCategoryProduct.addItem(this.menu.getCategoriesWithAtLeastAProduct().get(i).toString());
            categoriesMap.put(i+1, this.menu.getCategoriesWithAtLeastAProduct().get(i).getId());
        }
        
        // Modello della tabella dei prodotti
        tableModelProducts = (DefaultTableModel) tblOrderProducts.getModel();
        
        // Inizializzo il calendario con le date valide
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_MONTH, 0);
        Calendar max = Calendar.getInstance();
        RangeEvaluator re = new RangeEvaluator();
        re.setMinSelectableDate(min.getTime());
        txtDateChooser.getJCalendar().getDayChooser().addDateEvaluator(re);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) txtDateChooser.getDateEditor();
        editor.setEditable(false);
        
        // Listener pulsante back
        btnBack.addActionListener(
            ev -> {
                int result = JOptionPane.showConfirmDialog(this, "Tornando alla Home perderai tutti i tuoi progressi, vuoi continuare?", "ConfirmBox", JOptionPane.OK_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    UserHome uh = new UserHome(this.user);
                    uh.setVisible(true);
                    this.setVisible(false);
                }
            });
        
        // Listener pulsante verifica disponibilità tavolo
        btnCheckAvailability.addActionListener(
            ev -> {
                UserReservationCheckAvailabilityValidator validator = new UserReservationCheckAvailabilityValidator();
                boolean validModel = false;

                // Effettuo la validazione dei campi per la verifica della disponibilità di un tavolo
                try{
                    validModel = validator.validate(this);
                }
                catch(UserReservationException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }

                // Posso procedere con la fase successiva solo se il "modello" (campi per l'aggiunta di un tavolo) sono validi
                if (validModel) {
                    SimpleDateFormat dataStandardFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String dataString = dataStandardFormat.format(txtDateChooser.getDate());
                    String time = cbTimeChooser.getItemAt(cbTimeChooser.getSelectedIndex());
                    timeTemp = time;
                    String dataWithTime = dataString + " " + time;
                    SimpleDateFormat dataWithTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    numClientsTemp = Integer.parseInt(txtNumClients.getText());
                    // Resetto il contenuto del JTextPane
                    txtPaneAvailabilityState.setText("");

                    ICheckAvailabilityTable checkAvailabilityTable = new CheckAvailabilityTable();

                    try {
                        dateTemp = dataWithTimeFormat.parse(dataWithTime);

                        if (DateUtility.isSameDay(dateTemp) && (DateUtility.compareDate(dateTemp)==-1)) {
                            JOptionPane.showMessageDialog(rootPane, "Non è più possibile prenotare per questa data\n");
                        }
                        else {
                            if(checkAvailabilityTable.checkAvailabilityTable(fileTablesPath, fileOrderPath, numClientsTemp, dateTemp) != -1){
                                txtPaneAvailabilityState.setText("È possibile effettuare la prenotazione "+"per "
                                        +numClientsTemp+" persone"+" in data: "+DateUtility.formatDateWithTime(dateTemp));
                                txtPaneAvailabilityState.setForeground(Color.GREEN);
                                idTable = checkAvailabilityTable.checkAvailabilityTable(fileTablesPath, fileOrderPath, numClientsTemp, dateTemp);
                                checked = true;
                                isValid = true;
                            }
                            else{
                                txtPaneAvailabilityState.setText("Nessun tavolo disponibile");
                                txtPaneAvailabilityState.setForeground(Color.RED);
                                checked = true;
                            }
                        }
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(rootPane, "Impossibile effettuare il parse della Data\n");
                    }
                }
            });
        
        // Listener pulsante aggiungi prodotto
        btnAddProduct.addActionListener(
            ev -> {
                Object[] row = new Object[3];
                if (cbCategoryProduct.getSelectedIndex()>0) {
                    int categoryIndex = cbCategoryProduct.getSelectedIndex();
                    int searchIndex = categoriesMap.get(categoryIndex);

                    ProductCategory<?> category = this.menu.getProductCategoryById(searchIndex);

                    int productId = productsMap.get(cbProducts.getSelectedIndex());

                    Product product = this.menu.getProductById(category, productId);

                    IConsumation productWithExtra = null;

                    row[0] = productId;
                
                // Creo un prodotto personalizzato e lo aggiungo alla tabella
                if (cbExtra.getSelectedIndex()>0) {
                    if (category.getCategory() instanceof Starter) {
                        productWithExtra = 
                            new StarterDecorator(product, StarterIngredients.valueOf(enumMap.get(cbExtra.getSelectedIndex())));
                    }
                    else if (category.getCategory() instanceof FirstCourse) {
                        productWithExtra = 
                            new FirstCourseDecorator(product, FirstCourseIngredients.valueOf(enumMap.get(cbExtra.getSelectedIndex())));
                    }
                    else if (category.getCategory() instanceof SecondCourse) {
                        productWithExtra = 
                            new SecondCourseDecorator(product, SecondCourseIngredients.valueOf(enumMap.get(cbExtra.getSelectedIndex())));
                    }
                    else if (category.getCategory() instanceof Dessert) {
                        productWithExtra = 
                            new DessertDecorator(product, DessertIngredients.valueOf(enumMap.get(cbExtra.getSelectedIndex())));
                    }
                    if (productWithExtra != null) {
                        row[1] = productWithExtra.getName();
                        row[2] = productWithExtra.getPrice();
                    }
                }
                 else {
                        row[1] = product.getName();
                        row[2] = product.getPrice();
                    }
                    tableModelProducts.addRow(row);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Nessun prodotto selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        // Listener combobox categoria prodotto
        cbCategoryProduct.addActionListener(
            ev -> {
                if (cbCategoryProduct.getSelectedIndex()>0) {
                    int categoryIndex = cbCategoryProduct.getSelectedIndex();
                    int searchIndex = categoriesMap.get(categoryIndex);
                    ProductCategory<?> category = this.menu.getProductCategoryById(searchIndex);
                    
                    // Rimuovo tutti gli elementi della combobox delle categorie
                    cbProducts.removeAllItems();
                    // Svuoto productsMap
                    productsMap.clear();

                    //Aggiorno i prodotti di una categoria nella relativa combobox
                    for (int j=0; j<category.getProducts().size(); j++) {
                        productsMap.put(j, category.getProducts().get(j).getId());
                        cbProducts.addItem(category.getProducts().get(j).getName()+ "  €" + category.getProducts().get(j).getPrice());
                    }

                    cbExtra.removeAllItems();
                    
                    // Popolo la combobox dlle aggiunge in relazione alla categoria scelta
                    if(category.toString().equalsIgnoreCase("Antipasti")) {
                        populateComboboxExtra(Arrays.asList(StarterIngredients.values()));
                    }
                    if(category.toString().equalsIgnoreCase("Primi")) {
                        populateComboboxExtra(Arrays.asList(FirstCourseIngredients.values()));
                    }
                    if(category.toString().equalsIgnoreCase("Secondi")) {
                        populateComboboxExtra(Arrays.asList(SecondCourseIngredients.values()));
                    }
                    if(category.toString().equalsIgnoreCase("Dolci")) {
                        populateComboboxExtra(Arrays.asList(DessertIngredients.values()));
                    }
                }
                else if (cbCategoryProduct.getSelectedIndex()==0) {
                    cbProducts.removeAllItems();
                    productsMap.clear();
                    cbExtra.removeAllItems();
                }
            });
        
        btnRemoveProduct.addActionListener(
            ev -> {
                if (this.tblOrderProducts.getRowCount() > 0) {
                    switch (this.tblOrderProducts.getSelectedRows().length) {
                        case 0:
                            JOptionPane.showMessageDialog(rootPane, "Seleziona un prodotto da rimuovere", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case 1:
                            int resultRemove = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere il prodotto scelto?", "ConfirmBox", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
                            if (resultRemove==JOptionPane.YES_OPTION) {
                                int i = this.tblOrderProducts.getSelectedRow();
                                if (i >= 0) {
                                    tableModelProducts.removeRow(i);
                                }
                                else {
                                    JOptionPane.showMessageDialog(this, "Errore: non è stato possibile rimuovere il tavolo selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                                }
                            }   break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "È possibile rimuovere soltanto un tavolo alla volta", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
                else {
                     JOptionPane.showMessageDialog(rootPane, "Nessun tavolo presente", "InfoBox", JOptionPane.WARNING_MESSAGE);
                } 
            });        
                
        btnNext.addActionListener(
            ev -> {
                if (dateTemp != null && numClientsTemp !=0) {
                    // Reinserisco i dati per evitare che l'utente abbia effettuata qualche modifica
                    txtDateChooser.setDate(dateTemp);
                    txtNumClients.setText(String.valueOf(numClientsTemp));
                    cbTimeChooser.setSelectedItem(timeTemp);
                    ICheckAvailabilityTable checkAvailabilityTable = new CheckAvailabilityTable();
                    if (checkAvailabilityTable.checkAvailabilityTable(fileTablesPath, fileOrderPath, numClientsTemp, dateTemp)!=-1) {
                        // Nascondo i componenti della verifica della disponibilità del tavolo
                        viewComponents(jPanel1, false);
                        // Mostro i componenti dell'ordinazione
                        viewComponents(jPanel3, true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "La data di prenotazione non è più valida", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Effettua per prima cosa la verifica della disponibilità", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
        btnNext2.addActionListener(
            ev -> {
                // Verifico che nella tabella vi sia almeno un prodotto inserito
                if (tblOrderProducts.getRowCount() > 0) {
                    int result = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler passare al pagamento e alla conferma del tuo ordine? Clicca su"
                            + "Yes per continuare, No altrimenti", "ConfirmBox", JOptionPane.OK_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        ICheckAvailabilityTable checkAvailabilityTable = new CheckAvailabilityTable();
                        // Effettuo per prima cosa il ri-controllo della disponibilità
                        if (checkAvailabilityTable.checkAvailabilityTable(fileTablesPath, fileOrderPath, numClientsTemp, dateTemp)!=-1) {
                            viewComponents(jPanel3, false);
                            viewComponents(jPanel6, true);

                            txtAreaSummary.append("DATI DI PRENOTAZIONE\n");
                            txtAreaSummary.append("\nData: "+DateUtility.formatDateWithTime(txtDateChooser.getDate()));
                            txtAreaSummary.append("\n\nPRODOTTI\n\n");

                            int rows = tableModelProducts.getRowCount();

                            for (int i=0; i<rows; i++) {
                                // Stampo il prodotto e il relativo prezzo
                                txtAreaSummary.append("x1 "+tableModelProducts.getValueAt(i, 1)+ "   €" + tableModelProducts.getValueAt(i, 2)+"\n");
                            }

                            txtOrderTotalPayment.setText("€ "+getTotalOrder());
                        }
                        else {
                            int result2 = JOptionPane.showConfirmDialog(this, "La data di prenotazione non è più valida sarà necessario ripetere la procedura. "
                                    + "Premi su Yes per ripetere la procedura o su No per tornare alla Home", "ErrorBox", JOptionPane.OK_OPTION);
                            if (result2 == JOptionPane.YES_OPTION) {
                                UserReservation newUserReservation = new UserReservation(this.menu, this.user);
                                newUserReservation.setVisible(true);
                                this.setVisible(false);
                            }
                            else if (result2 == JOptionPane.NO_OPTION) {
                                UserHome uh = new UserHome(this.user);
                                uh.setVisible(true);
                                this.setVisible(false);
                            }
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Errore: Inserisci almeno un prodotto al tuo ordine", "InfoBox: " + "Error message", JOptionPane.ERROR_MESSAGE);
                }
            });
        
                
        btnPayAndConfirmOrder.addActionListener(
            ev -> {
                ICheckAvailabilityTable checkAvailabilityTable = new CheckAvailabilityTable();
                // Verifico per prima cosa che la data sia ancora disponibile
                if (checkAvailabilityTable.checkAvailabilityTable(fileTablesPath, fileOrderPath, numClientsTemp, dateTemp)!=-1) {
                    if (txtInsertCash.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Inserisci l'importo in contante per effettuare il pagamento", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        if (ValidatorUtility.isDouble(txtInsertCash.getText())) {
                            if(Double.parseDouble(txtInsertCash.getText()) >= getTotalOrder()){        
                                // Processor del resto
                                BanknoteProcessor processor = new BanknoteProcessor();
                                processor.addHandler(new OneCentHandler());
                                processor.addHandler(new TwoCentHandler());
                                processor.addHandler(new FiveCentHandler());
                                processor.addHandler(new TenCentHandler());
                                processor.addHandler(new TwentyCentHandler());
                                processor.addHandler(new FiftyCentHandler());
                                processor.addHandler(new OneEuroHandler());
                                processor.addHandler(new TwoEuroHandler());
                                processor.addHandler(new FiveEuroHandler());
                                processor.addHandler(new FiveEuroHandler());
                                processor.addHandler(new TenEuroHandler());  
                                processor.addHandler(new TwentyEuroHandler());
                                processor.addHandler(new FiftyEuroHandler());
                                processor.addHandler(new OneHundredEuroHandler());
                                processor.addHandler(new FiveHundredEuroHandler());

                                // Ricavo il resto
                                double rest = DoubleUtility.round(Double.parseDouble(txtInsertCash.getText())-getTotalOrder(), 2);
                                Banknote banknote = new Banknote(rest);
                                processor.process(banknote);

                                // TextArea per il popup di conferma prenotazione
                                JTextArea textAreaRest = new JTextArea();
                                textAreaRest.setRows(10);
                                textAreaRest.setColumns(70);
                                textAreaRest.setLineWrap(true);
                                textAreaRest.setWrapStyleWord(true);
                                textAreaRest.append("Grazie per aver prenotato presso la nostra struttura!\n");

                                // Creo l'ordine
                                Order newOrder = new Order();
                                newOrder.setId(CsvUtility.maxIntegerColumn(0, fileOrderPath)+1);
                                newOrder.setUserId(user.getId());
                                newOrder.setTableId(idTable);
                                newOrder.setData(dateTemp);
                                newOrder.setTotalPrice(getTotalOrder());
                                newOrder.setInsertedCash(Double.parseDouble(txtInsertCash.getText()));
                                newOrder.setRest(rest);
                                newOrder.setState(new OrderPaidState());
                                newOrder.setFeedbackReleased(false);
                                newOrder.setDateSimulated(false);
                                newOrder.addRecord();

                                // Creo gli elementi dell'ordine
                                List<OrderItem> orderItems = new ArrayList<>();
                                for (int i=0; i<tblOrderProducts.getRowCount(); i++) {
                                    OrderItem orderItem = new OrderItem();
                                    orderItem.setOrderId(newOrder.getId());
                                    orderItem.setProductId(Integer.parseInt(String.valueOf(tblOrderProducts.getValueAt(i, 0))));
                                    orderItem.setProductName(String.valueOf(tblOrderProducts.getValueAt(i, 1)));
                                    orderItem.setPrice(Double.parseDouble(String.valueOf(tblOrderProducts.getValueAt(i, 2))));
                                    orderItem.addRecord();
                                    orderItems.add(orderItem);
                                }
                                // Assegno gli item all'ordine
                                newOrder.setOrderItems(orderItems);

                                List<Order> tempOrders = this.user.getOrders();
                                tempOrders.add(newOrder);
                                this.user.setOrders(tempOrders);

                                this.setVisible(false);

                                JScrollPane scrollTextArea = new JScrollPane (textAreaRest, 
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                                JLabel confirmLabel = new JLabel("Clicca su OK per tornare alla Home");
                                confirmLabel.setBorder(new EmptyBorder(10,0,0,0));
                                confirmLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                                Font f = confirmLabel.getFont();
                                confirmLabel.setFont(f.deriveFont(18.0f));
                                confirmLabel.setFont(f.deriveFont(f.getStyle()| Font.BOLD));
                                confirmLabel.setForeground(Color.DARK_GRAY);

                                JPanel jp = new JPanel();
                                jp.add(scrollTextArea);
                                jp.add(confirmLabel);
                                jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

                                JOptionPane paneRest = new JOptionPane();

                                if (banknote.getBanknote().size() > 0) {

                                    textAreaRest.append("\nResto erogato: "+ "€ "+rest+" in banconte e\\o monete di:\n\n");

                                    for (int i=0; i<banknote.getBanknote().size(); i++) {
                                        textAreaRest.append("-> "+ banknote.getBanknote().get(i)+"\n");
                                    }
                                }

                                // Creato il file genero la finestra di dialogo che mi permette di visualizzare il resto
                                int dialogResult = paneRest.showConfirmDialog(null, jp, "Conferma di avvenuta prenotazione", JOptionPane.OK_OPTION);

                                if (dialogResult == JOptionPane.OK_OPTION) {
                                    UserHome uh = new UserHome(this.user);
                                    uh.setVisible(true);
                                    this.setVisible(false);
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Impossibile pagare: L'importo inserito è minore del totale richiesto", "InfoBox: " + "Error message", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "L'importo inserito non è valido (Il formato dell'importo deve essere: xx.xx)", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else {
                    int result2 = JOptionPane.showConfirmDialog(this, "La data di prenotazione non è più valida sarà necessario ripetere la procedura. "
                            + "Premi su Yes per ripetere la procedura o su No per tornare alla Home", "ErrorBox", JOptionPane.OK_OPTION);
                    if (result2 == JOptionPane.YES_OPTION) {
                        UserReservation newUserReservation = new UserReservation(this.menu, this.user);
                        newUserReservation.setVisible(true);
                        this.setVisible(false);
                    }
                    else if (result2 == JOptionPane.NO_OPTION) {
                        UserHome uh = new UserHome(this.user);
                        uh.setVisible(true);
                        this.setVisible(false);
                    }
                }
            });
        
        btnIngredients.addActionListener(
            ev -> {
                try {
                    if(!(cbProducts.getItemAt(cbProducts.getSelectedIndex()).isEmpty())) {
                        int categoryId = categoriesMap.get(cbCategoryProduct.getSelectedIndex());
                        int productId = productsMap.get(cbProducts.getSelectedIndex());
                        ProductCategory<?> category = this.menu.getProductCategoryById(categoryId);
                        Product p = this.menu.getProductById(category, productId);
                        if (p.getIngredients().size()>0) {
                            JTextArea textArea = new JTextArea();
                            JScrollPane scrollPane = new JScrollPane(textArea);  
                            textArea.setLineWrap(true);  
                            textArea.setWrapStyleWord(true); 
                            scrollPane.setPreferredSize(new Dimension(400, 200));
                            for (int i=0; i<p.getIngredients().size(); i++) {
                                textArea.append(p.getIngredients().get(i)+"\n");
                            }
                            JOptionPane.showMessageDialog(null, scrollPane, "Ingredienti",  
                                                                   JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Nessun ingrediente presente", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(rootPane, "Nessun prodotto selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NullPointerException e) {
                    JOptionPane.showMessageDialog(rootPane, "Nessun prodotto selezionato", "ErrorBox", JOptionPane.ERROR_MESSAGE);
                }
            });
        
                
        btnBackToCheck.addActionListener(
            ev -> {
                int result = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler tornare nella schermata di verifica disponibilità tavolo? "
                        + "(I prodotti eventualmente selezionati non verranno persi)", "ConfirmBox", JOptionPane.OK_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    viewComponents(jPanel1, true);
                    viewComponents(jPanel3, false);
                }
            });
        
                
        btnBack2.addActionListener(
            ev -> {
                int result = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler tornare nella schermata di ordinazione?"
                        + "Clicca su Yes per procedere, No altrimenti", "ConfirmBox", JOptionPane.OK_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    viewComponents(jPanel3, true);
                    viewComponents(jPanel6, false);
                }
            });
        
        btnCancelOrder.addActionListener(
            ev -> {
                int result = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler annulare l'ordine? "
                        + "Clicca su Yes per annullare il tuo ordine e ritornare alla Home, No altrimenti", "ConfirmBox", JOptionPane.OK_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    UserHome uh = new UserHome(this.user);
                    uh.setVisible(true);
                    this.setVisible(false);
                }
            });
        
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    
    /**
     * Questo metodo permette di ottenere la data inserita in input per cui si desidera
     * effettuare una prenotazione
     * 
     * @return la data in cui si vuole effettuare una prenotazione digitata in input
     */
    public String getInputDate() {
        if (txtDateChooser.getDate()==null)
            return "";
        else
            return DateUtility.formatDateWithTime(txtDateChooser.getDate());
    }
    
    
    /**
     * Questo metodo restituisce il numero di persone per cui si intende prenotare il tavolo
     * (dato inserito in input)
     * 
     * @return il numero di persone per cui si intende prenotare il tavolo (dato inserito in input)
     */
    public String getInputNumClients() {
        return this.txtNumClients.getText();
    }
    
    
    /**
     * Questo metodo permette di calcolare il totale dell'ordine dai prodotti
     * presenti nella tabella 
     * 
     * @return il totale dell'ordine calcolato dalla tabella dei prodotti
     */
    public Double getTotalOrder() {
        int rows = tableModelProducts.getRowCount();
        double totale = 0.0d;
            
        for (int i=0; i<rows; i++) {
            // Stampo il prodotto e il relativo prezzo
            totale += Double.parseDouble(String.valueOf(tableModelProducts.getValueAt(i, 2)));
        }
        return DoubleUtility.round(totale, 2);
    }
    
    
    /**
     * Questo metodo permette di visualizzare sulla ComboboxExtra la lista di valori 
     * di un enum
     * 
     * @param enumeration i valori dell'enum da visualizzare nella combobox Extra
     */
    public void populateComboboxExtra(List<Enum> enumeration) {
        cbExtra.addItem("Nessun extra selezionato");
        AtomicInteger index = new AtomicInteger(0);
        enumeration.stream()
            .forEach(ingrendient -> {cbExtra.addItem(EnumUtility.splitEnum(ingrendient)); 
                enumMap.put(index.incrementAndGet(), String.valueOf(ingrendient));});
    }
    

    /**
     * Questo metodo permette di mostrare o nascondare gli elementi di un container
     * passato a parametro
     * 
     * @param container il contenitore di cui visualizzare o meno gli elementi figli
     * @param view parametro che permette di visualizzare gli elementi figli del
     * container passato a parametro se impostato a true, di non visualizzarli nel caso
     * contrario
     */
    public void viewComponents(Container container, boolean view) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setVisible(view);
            if (component instanceof Container) {
                viewComponents((Container)component, view);
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

        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtDateChooser = new com.toedter.calendar.JDateChooser();
        lblDateChooser = new javax.swing.JLabel();
        lblTimeChooser = new javax.swing.JLabel();
        cbTimeChooser = new javax.swing.JComboBox<>();
        lblNumClients = new javax.swing.JLabel();
        txtNumClients = new javax.swing.JTextField();
        btnCheckAvailability = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPaneAvailabilityState = new javax.swing.JTextPane();
        btnNext = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblDateChooser4 = new javax.swing.JLabel();
        cbCategoryProduct = new javax.swing.JComboBox<>();
        lblProducts = new javax.swing.JLabel();
        cbProducts = new javax.swing.JComboBox<>();
        cbExtra = new javax.swing.JComboBox<>();
        lblExtra = new javax.swing.JLabel();
        btnAddProduct = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblOrderProducts = new javax.swing.JTable();
        btnRemoveProduct = new javax.swing.JButton();
        btnNext2 = new javax.swing.JButton();
        btnIngredients = new javax.swing.JButton();
        btnBackToCheck = new javax.swing.JButton();
        lblCart = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaSummary = new javax.swing.JTextArea();
        lblInsertCash = new javax.swing.JLabel();
        txtInsertCash = new javax.swing.JTextField();
        txtOrderTotalPayment = new javax.swing.JTextField();
        btnPayAndConfirmOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        btnBack2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();

        jButton2.setText("jButton2");

        jButton3.setText("jButton3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 52, 54));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Verifica disponibilità tavolo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lblDateChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDateChooser.setForeground(new java.awt.Color(255, 255, 255));
        lblDateChooser.setText("Data:");

        lblTimeChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTimeChooser.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeChooser.setText("Ora:");

        cbTimeChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12:00", "13:00", "14:00", "19:00", "20:00", "21:00" }));

        lblNumClients.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNumClients.setForeground(new java.awt.Color(255, 255, 255));
        lblNumClients.setText("Numero di persone:");

        btnCheckAvailability.setText("Verifica disponibilità");
        btnCheckAvailability.setPreferredSize(new java.awt.Dimension(125, 25));

        txtPaneAvailabilityState.setEditable(false);
        txtPaneAvailabilityState.setBorder(javax.swing.BorderFactory.createTitledBorder("Stato disponibilità"));
        txtPaneAvailabilityState.setToolTipText("Stato disponibilità prenotazione");
        jScrollPane3.setViewportView(txtPaneAvailabilityState);

        btnNext.setText("Continua");
        btnNext.setPreferredSize(new java.awt.Dimension(73, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNumClients, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNumClients, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimeChooser, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDateChooser, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCheckAvailability, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                            .addComponent(cbTimeChooser, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDateChooser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNumClients)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumClients, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTimeChooser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTimeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCheckAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel3.setBackground(new java.awt.Color(45, 52, 54));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordinazione", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lblDateChooser4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDateChooser4.setForeground(new java.awt.Color(255, 255, 255));
        lblDateChooser4.setText("Seleziona la categoria:");

        lblProducts.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblProducts.setForeground(new java.awt.Color(255, 255, 255));
        lblProducts.setText("Seleziona il prodotto:");

        lblExtra.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblExtra.setForeground(new java.awt.Color(255, 255, 255));
        lblExtra.setText("Seleziona extra:");

        btnAddProduct.setText("Aggiungi prodotto");
        btnAddProduct.setPreferredSize(new java.awt.Dimension(119, 25));

        tblOrderProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome prodotto", "Prezzo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblOrderProducts);
        if (tblOrderProducts.getColumnModel().getColumnCount() > 0) {
            tblOrderProducts.getColumnModel().getColumn(1).setMinWidth(250);
        }

        btnRemoveProduct.setText("Rimuovi");
        btnRemoveProduct.setPreferredSize(new java.awt.Dimension(69, 25));

        btnNext2.setText("Continua");
        btnNext2.setPreferredSize(new java.awt.Dimension(173, 25));

        btnIngredients.setText("Visualizza ingredienti");

        btnBackToCheck.setText("Torna indietro");
        btnBackToCheck.setPreferredSize(new java.awt.Dimension(99, 25));

        lblCart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCart.setForeground(new java.awt.Color(255, 255, 255));
        lblCart.setText("Carrello");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBackToCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(btnRemoveProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(cbCategoryProduct, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbProducts, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbExtra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCart)
                                    .addComponent(lblExtra)
                                    .addComponent(lblDateChooser4)
                                    .addComponent(lblProducts))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(222, Short.MAX_VALUE)
                        .addComponent(btnIngredients, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDateChooser4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCategoryProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(btnIngredients)
                .addGap(8, 8, 8)
                .addComponent(lblExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbExtra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lblCart)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBackToCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel6.setBackground(new java.awt.Color(45, 52, 54));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento e conferma dell'ordine", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jScrollPane2.setToolTipText("Riepilogo Ordine");
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(174, 114));

        txtAreaSummary.setColumns(20);
        txtAreaSummary.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtAreaSummary.setLineWrap(true);
        txtAreaSummary.setRows(5);
        txtAreaSummary.setWrapStyleWord(true);
        txtAreaSummary.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Riepilogo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 102, 0))); // NOI18N
        jScrollPane2.setViewportView(txtAreaSummary);

        lblInsertCash.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInsertCash.setForeground(new java.awt.Color(255, 255, 255));
        lblInsertCash.setText("Inserisci contante:");

        txtOrderTotalPayment.setEditable(false);
        txtOrderTotalPayment.setBackground(new java.awt.Color(45, 52, 54));
        txtOrderTotalPayment.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtOrderTotalPayment.setForeground(new java.awt.Color(0, 255, 255));
        txtOrderTotalPayment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOrderTotalPayment.setText("€ 0,00");
        txtOrderTotalPayment.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Totale", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        btnPayAndConfirmOrder.setText("Paga e conferma ordine");
        btnPayAndConfirmOrder.setPreferredSize(new java.awt.Dimension(147, 25));

        btnCancelOrder.setText("Annulla ordine");

        btnBack2.setText("Torna indietro");
        btnBack2.setPreferredSize(new java.awt.Dimension(99, 25));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnBack2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPayAndConfirmOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtInsertCash, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOrderTotalPayment)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(btnCancelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(lblInsertCash)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(15, 15, 15))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addGap(25, 25, 25)
                .addComponent(txtOrderTotalPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(lblInsertCash)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInsertCash, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnPayAndConfirmOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelOrder)
                .addGap(109, 109, 109)
                .addComponent(btnBack2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel5.setBackground(new java.awt.Color(45, 52, 54));

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/left-arrow.png"))); // NOI18N
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusable(false);
        btnBack.setMargin(new java.awt.Insets(2, 0, 2, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnBack)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserReservation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack2;
    private javax.swing.JButton btnBackToCheck;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnCheckAvailability;
    private javax.swing.JButton btnIngredients;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNext2;
    private javax.swing.JButton btnPayAndConfirmOrder;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JComboBox<String> cbCategoryProduct;
    private javax.swing.JComboBox<String> cbExtra;
    private javax.swing.JComboBox<String> cbProducts;
    private javax.swing.JComboBox<String> cbTimeChooser;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblCart;
    private javax.swing.JLabel lblDateChooser;
    private javax.swing.JLabel lblDateChooser4;
    private javax.swing.JLabel lblExtra;
    private javax.swing.JLabel lblInsertCash;
    private javax.swing.JLabel lblNumClients;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblTimeChooser;
    private javax.swing.JTable tblOrderProducts;
    private javax.swing.JTextArea txtAreaSummary;
    private com.toedter.calendar.JDateChooser txtDateChooser;
    private javax.swing.JTextField txtInsertCash;
    private javax.swing.JTextField txtNumClients;
    private javax.swing.JTextField txtOrderTotalPayment;
    private javax.swing.JTextPane txtPaneAvailabilityState;
    // End of variables declaration//GEN-END:variables
}
