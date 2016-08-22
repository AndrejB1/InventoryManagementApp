package com.company;

import javax.swing.*;

/*
 *  Created by Andrej Butic.
 *
 *  Simple Inventory management app
 *  for an electronics store.
 *
 *  Contains examples of MVC and Command patterns.
 */
public class Main{

    public static void main(String[] args) {

        // If database 'inventory.db' does not exist, create it, along with its tables.
        DBHandler.createTables();

        // Initialize the main window
        InputWindow window = new InputWindow();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(600, 710);
        window.setVisible(true);

    }
}
