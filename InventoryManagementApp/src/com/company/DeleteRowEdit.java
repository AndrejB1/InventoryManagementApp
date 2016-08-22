package com.company;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.util.List;

/**
 * Created by Andrej Butic
 *
 * Class created after the Command pattern.
 * Each time a row is deleted from the table, the Undo and Redo methods encapsulate the changes that took place
 */
public class DeleteRowEdit extends AbstractUndoableEdit{
    private InventoryTableModel model;
    private List<Object> row;
    private int index;
    private String tableName;

    public DeleteRowEdit(InventoryTableModel model, List<Object> row, int index, String tableName){

        this.model = model;
        this.row = row;
        this.index = index;
        this.tableName = tableName;

    }

    public void undo() throws CannotUndoException {
        model.addRow(row);
        String statement = "INSERT INTO " + tableName
                            + " VALUES("
                            + row.get(0) + ",'" + row.get(1) + "','" + row.get(2) + "', " + row.get(3) + ", " + row.get(4) + ")";
        DBHandler.executeStatement(statement);
    }

    public void redo() throws CannotRedoException {
        model.deleteRow(index+1);
        String statement = "DELETE FROM " + tableName + " WHERE ID = " + row.get(0);
        DBHandler.executeStatement(statement);
    }

    public boolean canUndo() { return true; }
    public boolean canRedo() { return true; }
}
