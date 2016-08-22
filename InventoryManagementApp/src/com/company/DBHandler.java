package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej Butic
 *
 * Class for creating and maintaining a SQLite database inside the app.
 */
public class DBHandler {

    private static Connection c = null;

    // If the database does not exist, create it, along with all the necessary tables.
    public static void createTables(){

        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");

            statement = c.createStatement();

            String s = "CREATE TABLE IF NOT EXISTS PHONES " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           VARCHAR    NOT NULL, " +
                    " MANUFACTURER   VARCHAR    NOT NULL, " +
                    " PRICE          INT, " +
                    " AMOUNT_IN_STORE         INT)";
            statement.executeUpdate(s);

            s = "CREATE TABLE IF NOT EXISTS COMPUTERS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           VARCHAR    NOT NULL, " +
                    " MANUFACTURER   VARCHAR    NOT NULL, " +
                    " PRICE          INT, " +
                    " AMOUNT_IN_STORE         INT)";
            statement.executeUpdate(s);

            s = "CREATE TABLE IF NOT EXISTS LAPTOPS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           VARCHAR    NOT NULL, " +
                    " MANUFACTURER   VARCHAR    NOT NULL, " +
                    " PRICE          INT, " +
                    " AMOUNT_IN_STORE         INT)";
            statement.executeUpdate(s);

            s = "CREATE TABLE IF NOT EXISTS TABLETS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           VARCHAR    NOT NULL, " +
                    " MANUFACTURER   VARCHAR    NOT NULL, " +
                    " PRICE          INT, " +
                    " AMOUNT_IN_STORE         INT)";
            statement.executeUpdate(s);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }finally{
            try{
                statement.close();
                c.close();
            }catch (Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    // Execute whatever statement is passed by a caller method.
    public static boolean executeStatement(String s){

        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = c.createStatement();
            System.out.println(s);

            statement.executeUpdate(s);
            return true;

        }catch (Exception e) {

            e.printStackTrace();
            return false;

        }finally{
            try{
                if(statement != null)
                    statement.close();
                    c.close();
            }catch (SQLException e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }

    }

    // Called by a different method to return a List of table values, used to populate JTable.
    public static List<List<Object>> fillTable(String s){

        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery(s);


            List<List<Object>> rows = new ArrayList();
            while (rs.next()) {
                List<Object> columnData = new ArrayList();
                int i = 1;
                while (i <= 5) {
                    columnData.add(rs.getString(i++));
                }
                rows.add(columnData);
            }
            return rows;

        }catch (Exception e) {

            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return null;

        }finally{
            try{
                if(statement != null)
                    statement.close();
                c.close();
            }catch (SQLException e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
        }
    }

    // Check if whatever name the user entered in the JTextField (from InputWindow.class)
    // matches one that is already in the table.
    public static boolean isInTable(String name, String type){

        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = c.createStatement();

            String s = "SELECT COUNT(NAME) FROM " + type + " WHERE NAME = '"+ name +"'";
            ResultSet rs = statement.executeQuery(s);

            //If the count is greater than 0, then the product is already inside the table. Making 'isInTable = true'
            return Integer.parseInt(rs.getString(1))> 0;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
            try{
                if(statement != null)
                    statement.close();
                    c.close();
            }catch (SQLException e){
                e.printStackTrace();
                System.exit(0);

            }
        }
    }

    // Method for printing out all the names in the current table.
    public static void TableToString(String type){

        Statement statement = null;

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:inventory.db");
            statement = c.createStatement();
            String s = "SELECT * FROM " + type;

            ResultSet rs = statement.executeQuery(s);

            while(rs.next()){
                String result = rs.getString(2);
                System.out.println(result + " \n");
            }

        }catch (Exception e) {

            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }finally{
            try{
                if(statement != null)
                    statement.close();
                    c.close();
            }catch (SQLException e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);

            }
        }
    }

}
