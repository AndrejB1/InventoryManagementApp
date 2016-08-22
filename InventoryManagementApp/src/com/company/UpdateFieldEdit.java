package com.company;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.math.BigDecimal;

/**
 * Created by Andrej Butic
 *
 * Class created after the Command pattern.
 * Each time a field is updated in the table, the Undo and Redo methods encapsulate the changes that took place
 */
public class UpdateFieldEdit extends AbstractUndoableEdit {

    private InventoryTableModel model;
    private Object oldValue;
    private Object newValue;
    private String tableName;
    private int row;
    private int column;
    private int id;

    public UpdateFieldEdit (InventoryTableModel model, Object oldValue, Object newValue, String tableName, int row, int column, int id){

        this.model = model;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.tableName = tableName;
        this.row = row;
        this.column = column;
        this.id = id;

    }

    public void undo() throws CannotUndoException {

        model.setValueAt(oldValue, row, column, false);

        if(oldValue instanceof BigDecimal){
            String statement = "UPDATE " + tableName + " SET PRICE = " + oldValue +
                                " WHERE ID = " + id;
            DBHandler.executeStatement(statement);

        }else if(oldValue instanceof Integer){
            String statement = "UPDATE " + tableName + " SET AMOUNT_IN_STORE = " + oldValue +
                    " WHERE ID = " + id;
            System.out.println("Here");
            DBHandler.executeStatement(statement);
        }
    }

    public void redo() throws CannotRedoException {

        model.setValueAt(newValue, row, column, false);

        if(oldValue instanceof BigDecimal){
            String statement = "UPDATE " + tableName + " SET PRICE = " + newValue +
                    " WHERE ID = " + id;
            DBHandler.executeStatement(statement);

        }else if(oldValue instanceof Integer){
            String statement = "UPDATE " + tableName + " SET AMOUNT_IN_STORE = " + newValue +
                    " WHERE ID = " + id;
            DBHandler.executeStatement(statement);
        }
    }

    public boolean canUndo() { return true; }
    public boolean canRedo() { return true; }
}
