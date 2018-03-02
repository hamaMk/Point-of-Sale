package Application;



import java.sql.*;

public class SqliteConnection {

    public static Connection conn;

    public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
             conn = DriverManager.getConnection("jdbc:sqlite:ShopDataBase.sqlite");
            return conn;
        }

        catch (Exception e) {
            return null;
        }
    }
}