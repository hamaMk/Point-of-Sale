package PointOfSale;

import Application.SqliteConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataSearch {

    private SqliteConnection dc;
    Connection conn;

    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> level = new ArrayList<>();
    private ArrayList<String> stock = new ArrayList<>();
    private ArrayList<String> retail = new ArrayList<>();
    private ArrayList<String> wholeSale = new ArrayList<>();

    public void setWords(){

    }

    public DataSearch(){

        dc = new SqliteConnection();
        conn = dc.connector();


        //words.add("charger");

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT Item FROM iterms");

            while (rs.next()){
               words.add(rs.getString(1));

            }


        } catch (SQLException e) {
            System.err.println("error" + e);
        }


    }



    public ArrayList setLables(String txt) throws Exception{

        String tmp = '"' + txt + '"';


        ResultSet rs = conn.createStatement().executeQuery("select * from iterms where Item = " +tmp );
        String item = rs.getString(2);
        String level = rs.getString(3);
        String stock = rs.getString(4);
        String retail = rs.getString(5);
        String wholesale = rs.getString(6);



        ArrayList<String> details = new ArrayList<>();
        details.add(item);
        details.add(level);
        details.add(stock);
        details.add(retail);
        details.add(wholesale);

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }


    public ArrayList getWords(){
        return words;
    }
}