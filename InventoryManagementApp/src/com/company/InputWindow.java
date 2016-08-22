package com.company;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/*
 * Class for initializing the GUI and detecting user inputs.
 */
public class InputWindow extends JFrame {

    public static JComboBox typePicker;
    public static JButton undoButton;
    public static JButton redoButton;

    private JTextField name;
    private JTextField manufacturer;
    private JTextField price;
    private JTextField amount;
    private JTextField searchField;

    private JButton inputButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTable table;

    // Initialize the components for 'Undo' functionality
    public UndoManager undoManager = new UndoManager();
    public static UndoableEditSupport undoSupport = new UndoableEditSupport();

    public InputWindow() {

        super("Inventory App");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set up a listener for whenever an Undoable event happens in the GUI.
        undoSupport.addUndoableEditListener(new UndoAdapter());

        // Set up the names of the tables to populate the JComboBox.
        String[]  types = {"PHONES", "COMPUTERS", "LAPTOPS", "TABLETS"};

        /*
         * Initializations for all the GUI components.
         * GridBagLayout was chosen due to its flexibility.
         */
        typePicker = new JComboBox(types);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typePicker, gbc);

        undoButton = new JButton("Undo");
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.insets= new Insets(0, 0, 2, 70);
        gbc.fill=GridBagConstraints.NONE;
        add(undoButton, gbc);
        undoButton.setEnabled(false);

        redoButton = new JButton("Redo");
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.insets= new Insets(0, 60, 2, 2);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(redoButton, gbc);
        redoButton.setEnabled(false);

        InventoryTableModel model = new InventoryTableModel();
        table = new JTable(model);

        //Make sure all the cell text is right aligned.
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer) table.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=3;
        gbc.insets= new Insets(0, 0, 0, 0);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        JScrollPane pane = new JScrollPane(table);
        pane.getViewport().setBackground(Color.WHITE);
        add(pane, gbc);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.insets= new Insets(0,100,0,0);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(nameLabel, gbc);

        //Text fields for adding new rows.
        name = new JTextField(20);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.insets= new Insets(2,20,0,0);
        gbc.ipady=5;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(name, gbc);

        JLabel manufacturerLabel = new JLabel("Manufacturer:");
        manufacturerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.gridwidth=1;
        gbc.insets= new Insets(0,100,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(manufacturerLabel, gbc);

        manufacturer = new JTextField(20);
        manufacturer.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridwidth=2;
        gbc.insets= new Insets(2,20,0,0);
        gbc.ipady=5;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(manufacturer, gbc);

        JLabel priceLabel = new JLabel("Product Price:");
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridwidth=1;
        gbc.insets= new Insets(0,100,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(priceLabel, gbc);

        price = new JTextField(20);
        price.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=1;
        gbc.gridy=5;
        gbc.gridwidth=2;
        gbc.insets= new Insets(2,20,0,0);
        gbc.ipady = 5;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(price, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx=0;
        gbc.gridy=6;
        gbc.gridwidth=1;
        gbc.insets= new Insets(0,100,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(amountLabel, gbc);

        amount = new JTextField(20);
        amount.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=1;
        gbc.gridy=6;
        gbc.gridwidth=2;
        gbc.insets= new Insets(2,20,0,0);
        gbc.ipady = 5;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(amount, gbc);

        inputButton = new JButton("Add To Inventory");
        gbc.gridx=1;
        gbc.gridy=7;
        gbc.gridwidth=2;
        gbc.insets= new Insets(5,20,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(inputButton, gbc);

        deleteButton = new JButton("Remove Selected Row");
        gbc.gridx=1;
        gbc.gridy=8;
        gbc.gridwidth=2;
        gbc.insets= new Insets(5,20,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(deleteButton, gbc);

        // Text Field and Button for the search function
        searchField = new JTextField(20);
        searchField.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=1;
        gbc.gridy=9;
        gbc.gridwidth=1;
        gbc.insets= new Insets(5,20,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.BOTH;
        add(searchField, gbc);

        searchButton = new JButton("Search By Name");
        gbc.gridx=2;
        gbc.gridy=9;
        gbc.gridwidth=1;
        gbc.insets= new Insets(5,5,0,0);
        gbc.ipady = 0;
        gbc.fill=GridBagConstraints.BOTH;
        add(searchButton, gbc);

        // Set up all the EventListeners for the buttons, ComboBox, and searchField.
        EventHandler h = new EventHandler();
        ActionListener undoAction = new UndoAction();
        ActionListener redoAction = new RedoAction();

        undoButton.addActionListener(undoAction);
        redoButton.addActionListener(redoAction);
        inputButton.addActionListener(h);
        deleteButton.addActionListener(h);
        typePicker.addActionListener(h);
        searchField.addActionListener(h);
        searchButton.addActionListener(h);

    }

    /*
     * Inner class to handle all the button clicks and user inputs,
     * except for direct cell input; The InventoryTableModel class handles this within itself.
     */
    private class EventHandler implements ActionListener{

        int id;
        String dType;
        String dName;
        String dManuf;
        BigDecimal dPrice;
        int dAmount;
        String statement;
        List<List<Object>> rowData;

        @Override
        public void actionPerformed(ActionEvent e) {

            dType   = String.valueOf(typePicker.getSelectedItem());

            // Acquire both the table's model, and a list of the values within as 'rowData'
            InventoryTableModel m = (InventoryTableModel) table.getModel();
            rowData = m.getRowData();

            /*
             * Here the application figures out what course of action to take.
             * It does so by firstly determining the source of the ActionEvent,
             * which can in this case be Add Row, Delete Row, Table ComboBox, or Search.
             *
             * Once the source is determined, it checks the value of the TextFields to
             * decide what operation to do on the model, and what statement to send to
             * the Database.
             */
            if(e.getSource()==inputButton) {

            // If the user neglected to include a product's name, notify them, otherwise proceed with the DBHandler statements.
               if(!name.getText().equals("")) {
                   makeRandomID();
                   dName = name.getText();
                   dManuf = manufacturer.getText();


                   // The 'Price' and 'Amount' fields can be empty, however throw out an error message if they are entered as a String of letters.
                   if (!price.getText().equals("")) {
                       try {
                           dPrice = new BigDecimal(price.getText());
                       } catch (NumberFormatException n) {
                           JOptionPane.showMessageDialog(null, "Please provide a valid number for the product price");
                       }
                   }
                   if (!amount.getText().equals("")) {
                       try {
                           dAmount = Integer.parseInt(amount.getText());
                       } catch (NumberFormatException n) {
                           JOptionPane.showMessageDialog(null, "Please provide a valid number for the 'Amount' field");
                       }
                   }


                // If the row the user wants to input doesn't share a 'Name' value with any of the table rows, add a new row
                   if (!DBHandler.isInTable(dName, dType)){
                           System.out.println(dAmount);
                       if (!dManuf.equals("") && dAmount >= 0 && dPrice.compareTo(BigDecimal.ZERO) >= 0) {
                           // If the randomly generated ID is already in the table, keep creating new IDs
                           // until an acceptable number is reached, then execute the statement.
                           while (!addStatement()) {
                               makeRandomID();
                           }
                       } else JOptionPane.showMessageDialog(null,
                               "Unable to add entries to table.\n" +
                                       "Please check that you have included a Manufacturer name,\n" +
                                       "and that the 'Amount' field is not less than zero.");

                    }
                   else if (amount.getText().equals("") && price.getText().equals(""))
                        // If the user has only input the product name, prompt them to enter more details for the database.
                        JOptionPane.showMessageDialog(null, "Can you please provide more information than just the product name?");

                   else if (amount.getText().equals("") && !price.getText().equals(""))
                        // If only Name and Price have a value, update the price of the product.
                         updatePriceStatement();

                   else if (!amount.getText().equals("") && price.getText().equals(""))
                       // If only Name and Amount have a value, update the store count of the object.
                       updateStoreCountStatement();

                   else if (!amount.getText().equals("") && !price.getText().equals("")) {
                       // If Name, Price, and Amount all have values, update both the price and the store count.
                       updatePriceStatement();
                       updateStoreCountStatement();
                   }
               } else JOptionPane.showMessageDialog(null, "Please enter a product name");
            }

            // If the Delete Button is the source, execute the deleteStatement method and remove the selected row.
            if(e.getSource()==deleteButton){
                deleteStatement(Integer.parseInt(rowData.get(table.getSelectedRow()).get(0).toString()));
            }

            // If the JComboBox selection is changed, refill 'rowData' and redraw the table.
            if(e.getSource()==typePicker){
                String statement;
                InventoryTableModel model = (InventoryTableModel) table.getModel();
                rowData.clear();
                switch(String.valueOf(typePicker.getSelectedItem())){
                    case "PHONES":
                        statement = "SELECT * FROM PHONES";
                        rowData = DBHandler.fillTable(statement);
                        break;
                    case "COMPUTERS":
                        statement = "SELECT * FROM COMPUTERS";
                        rowData = DBHandler.fillTable(statement);
                        break;
                    case "LAPTOPS":
                        statement = "SELECT * FROM LAPTOPS";
                        rowData = DBHandler.fillTable(statement);
                        break;
                    case "TABLETS":
                        statement = "SELECT * FROM TABLETS";
                        rowData = DBHandler.fillTable(statement);
                        break;
                }
                table.setModel(model);
                table.repaint();
            }

            // If the user has clicked the search button or pressed enter on the search field,
            // loop through rowData and select the row with the matching name field.
            if(e.getSource() == searchButton || e.getSource() == searchField){

                if(!searchField.getText().equals("")){
                    int i = 0;
                    boolean itemFound = false;
                    for(List row : rowData){
                        if(row.contains(searchField.getText())){
                            itemFound = true;
                            break;
                        }
                        i++;
                    }
                    if (itemFound)
                        table.changeSelection(i, 1, false, false);
                    else JOptionPane.showMessageDialog(null, "Object not found");
                }
            }
        }


        // Generate Random 5-digit ID for the product. Always starts with 1.
        private void makeRandomID(){ id = new Random().nextInt(10000) + 9999; }

        // Statement for adding a new row to the table.
        private boolean addStatement(){

            statement = "INSERT INTO " + dType
                        + " VALUES("
                        + id + ",'" + dName + "','" + dManuf + "', " + dPrice + ", " + dAmount + ")";

            // If the Random Number Generator created an ID which is already in  the table, re-run it.
            if(!DBHandler.executeStatement(statement)){
                System.out.println("The ID was not unique, generating new ID");
                return false;
            }else {
                // If the ID is valid, add the new object to the list, and apply the edited model to the JTable.
                JOptionPane.showMessageDialog(null, "Added "+ dName + " to the inventory!");
                InventoryTableModel model = (InventoryTableModel) table.getModel();
                List<Object> newData = new ArrayList<>();
                newData.add(id);
                newData.add(dName);
                newData.add(dManuf);
                newData.add(dPrice);
                newData.add(dAmount);
                model.addRow(newData);
                table.setModel(model);

                // Adding a new row is an Undoable operation, so pass the details along to the 'AddRowEdit' Command.
                UndoableEdit newAddRow = new AddRowEdit(model, newData, rowData.size()-1, dType);
                undoSupport.postEdit(newAddRow);
                undoButton.setEnabled(true);

                return true;
            }
        }

        // Statement for updating the store count. Is executed when a user only inputs a name and product count
        // into the JTextFields. It works by taking the current count and adding (or subtracting) the new value
        private void updateStoreCountStatement(){

            statement = "UPDATE " + dType + " SET AMOUNT_IN_STORE = AMOUNT_IN_STORE + " + dAmount +
                    " WHERE NAME = '" + dName + "'";
            DBHandler.executeStatement(statement);

            InventoryTableModel model = (InventoryTableModel) table.getModel();

            int newCount = 0;
            int index = 0;
            for(List rows : rowData){
                // Compare the 'name' in each row with the one entered by the user. Once a match is found, calculate the new count
                if (String.valueOf(rows.get(1)).equals(dName)) {
                    newCount = Integer.parseInt(rows.get(4).toString()) + dAmount;
                    model.updateCount(newCount, index);
                    break;
                }
                index++;
            }
            // Remember the old count value for UndoRedo purposes.
            // Then reset the table model and select the updated row.
            int oldCount = newCount-dAmount;
            table.setModel(model);
            table.changeSelection(index, 4, true, false);

            // Updating is an Undoable operation, so pass the details along to the 'UpdateFieldEdit' Command.
            UndoableEdit newUpdateRow = new UpdateFieldEdit(model, oldCount, newCount, dType, index, 4, id);
            undoSupport.postEdit(newUpdateRow);
            undoButton.setEnabled(true);

        }

        // Statement for updating the price of a product. Executed when a user only inputs a product name and price.
        // Changes the old value of the price to the one that was entered by the user.
        private void updatePriceStatement(){

            statement = "UPDATE " + dType + " SET PRICE = " + dPrice +
                    " WHERE NAME = '" + dName + "'";
            DBHandler.executeStatement(statement);

            InventoryTableModel model = (InventoryTableModel) table.getModel();

            // Remember the old price for UndoRedo purposes.
            BigDecimal oldPrice = null;
            int id = 0;
            int index = 0;
            for(List rows : rowData){
                // Find a name match in the table, and change the price field.
                if (String.valueOf(rows.get(1)).equals(dName)) {
                    id = Integer.parseInt(rows.get(0).toString());
                    oldPrice = new BigDecimal(rows.get(3).toString());
                    model.updatePrice(dPrice, index);
                    break;
                }
                index++;
            }
            table.setModel(model);
            table.changeSelection(index, 3, true, false);

            // Updating is an Undoable operation, so pass the details along to the 'UpdateFieldEdit' Command.
            UndoableEdit newUpdateRow = new UpdateFieldEdit(model, oldPrice, dPrice, dType, index, 3, id);
            undoSupport.postEdit(newUpdateRow);
            undoButton.setEnabled(true);

        }

        // Statement for deleting a selected row.
        private void deleteStatement(int id){

            statement = "DELETE FROM " + dType + " WHERE ID = " + id;
            DBHandler.executeStatement(statement);
            InventoryTableModel model = (InventoryTableModel) table.getModel();
            List<Object> rowToDelete = rowData.get(table.getSelectedRow());
            model.deleteRow(table.getSelectedRow());
            table.setModel(model);
            table.repaint();

            // Deleting is an Undoable operation, so pass on the row details as a list to the 'DeleteRowEdit' Command
            UndoableEdit newDeleteRow = new DeleteRowEdit(model, rowToDelete, rowData.size()-1, dType);
            undoSupport.postEdit(newDeleteRow);
            undoButton.setEnabled(true);


        }
    }

    // Undo button click listener.
    private class UndoAction extends AbstractAction {

             public void actionPerformed(ActionEvent evt ) {

                 if(undoManager.canUndo()){
                     undoManager.undo();

                     // If an Undo operation was possible, a Redo must also be possible now, therefore enable it
                     redoButton.setEnabled(true);

                     // If the UndoManager is empty, disable the undoButton, to signify end of Undoable operation stack.
                     if(!undoManager.canUndo())
                         undoButton.setEnabled(false);
                 }
             }
    }

    // Redo button click listener.
    private class RedoAction extends AbstractAction {

        public void actionPerformed(ActionEvent evt ) {
            if(undoManager.canRedo()){
                undoManager.redo();

                // If a Redo operation was available, an Undo must have come before it, so enable the undoButton.
                undoButton.setEnabled(true);

                // If there are no Redo operations in the UndoManager, disable the button.
                if(!undoManager.canRedo())
                    redoButton.setEnabled(false);
            }
        }
    }

    // Listener for undoable methods. Picks up calls from any of the statements above and sends them to the UndoManager.
    private class UndoAdapter implements UndoableEditListener {

             public void undoableEditHappened (UndoableEditEvent evt) {

                     UndoableEdit edit = evt.getEdit();

                     undoManager.addEdit(edit);

                 }
    }

}
