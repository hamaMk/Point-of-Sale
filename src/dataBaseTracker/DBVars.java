package dataBaseTracker;

import Application.SqliteConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBVars {


    public static int getQuantity(String item){
        Connection conn = new SqliteConnection().connector();

        String tmp = '"' + item + '"';

        String query = "select * from iterms where item = "  + tmp ;
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery( query );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String level = null;
        try {
            level = rs.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(level);
    }

}
