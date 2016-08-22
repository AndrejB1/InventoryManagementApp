package com.company;

import javax.swing.table.AbstractTableModel;
import javax.swing.undo.UndoableEdit;
import java.math.BigDecimal;
import java.util.List;

import static com.company.InputWindow.typePicker;
import static com.company.InputWindow.undoButton;
import static com.company.InputWindow.undoSupport;

/*
 * Custom AbstractTableModel, capable of editing a table's contents
 * and interacting with the SQLite Database.
 */

public class InventoryTableModel extends AbstractTableModel {

    private String[] columnNames = {"ID", "Name", "Manufacturer", "Price", "Store Count"};
    private List<List<Object>> rowData;

    /*
     * Upon initialization, select everything from the SQLite PHONES table
     * (because it is the first table being displayed to the user upon startup)
     * Place it in a List<List> variable, and fill the JTable with the data
     */
    public InventoryTableModel(){
        String statement = "SELECT * FROM PHONES";
        rowData = DBHandler.fillTable(statement);
    }

    // The following are self-explanatory, generic methods for editing the JTable contents.
    public List getRowData(){
        return rowData;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rowData.size();
    }

    public void addRow(List<Object> newData)
    {
        rowData.add(newData);
        fireTableRowsInserted(rowData.size() - 1, rowData.size() - 1);
    }

    public void deleteRow(int row){
        rowData.remove(row);
        fireTableRowsDeleted(rowData.size() , rowData.size() );
    }

    public void updateCount(int newCount, int row){
        rowData.get(row).set(4, newCount);
        fireTableCellUpdated(row, 4);
    }

    public void updatePrice(BigDecimal newPrice, int row){
        rowData.get(row).set(3, newPrice);
        fireTableCellUpdated(row, 3);
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col){

        List<Object> l = rowData.get(row);
        return l.get(col);

    }

    /*
     * SetValueAt is called whenever a cell is edited directly, and we want that to be
     * an Undoable operation. However, if we Undo the user's cell edit, the Undo command
     * calls setValueAt again and becomes an Undoable operation itself! This would put
     * us in a cycle where only the Undo button works, and for a only one cell change too.
     *
     * To avoid this, we need to make sure that the Undo command doesn't qualify as an
     * Undoable operation. We do this by passing an 'Undoable' boolean as a parameter
     * of a second setValueAt method. A cell change will always call setValueAt, but
     * the boolean will determine if the operation can be Undone.
     */
    @Override
    public void setValueAt(Object newValue, int row, int col) {
        setValueAt(newValue, row, col, true);
    }

    public void setValueAt(Object newValue, int row, int col, boolean undoable)
    {
        int id = Integer.parseInt(getValueAt(row, 0).toString());

        // If undoable is false (was called by the Undo function) change the cell value but don't notify UndoManager.
        if (!undoable)
        {
            rowData.get(row).set(col, newValue);
            fireTableCellUpdated(row, col);
            notifyDatabase(newValue, typePicker.getSelectedItem().toString(), id, col);
            return;
        }

        // If undoable is true (was called by the Model itself) change the cell value and notify UndoManager.
        Object oldValue = getValueAt(row, col);
        rowData.get(row).set(col, newValue);
        fireTableCellUpdated(row, col);

        notifyDatabase(newValue, typePicker.getSelectedItem().toString(), id, col);

        UndoableEdit cellEdit = new UpdateFieldEdit(this, oldValue, newValue, typePicker.getSelectedItem().toString(), row, col, id);
        undoSupport.postEdit(cellEdit);
        undoButton.setEnabled(true);
    }

    // Let DBHandler know that a cell has been manually edited, and instruct it to change its own values accordingly.
    private void notifyDatabase(Object newValue, String tableType, int id, int column){

        if(column == 3){
            String statement = "UPDATE " + tableType + " SET PRICE = " + newValue +
                    " WHERE ID = " + id;
            DBHandler.executeStatement(statement);
            System.out.println(statement);

        }else if(column == 4){
            String statement = "UPDATE " + tableType + " SET AMOUNT_IN_STORE = " + newValue +
                    " WHERE ID = " + id;
            DBHandler.executeStatement(statement);
        }
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    // Make sure only Price and Store Count are directly editable.
    public boolean isCellEditable(int row, int col) {
        return col > 2;
    }



}
