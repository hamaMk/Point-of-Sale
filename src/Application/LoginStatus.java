package Application;

import java.sql.*;

public class LoginStatus {

    Connection conection;

    public LoginStatus() {

        conection = SqliteConnection.connector();

        if (conection == null) {
            System.exit(1);
        }
    }

    public boolean isDisconneced() {

        try {
            return !conection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isLogin(String user, String pass) throws SQLException{

        PreparedStatement preparedStatement = null ;
        ResultSet resultSet = null ;

        String query = ("select * from employees where username = ? and password = ?");

        try {
                preparedStatement = conection.prepareStatement(query);
                preparedStatement.setString(1,user);
                preparedStatement.setString(2,pass);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) return true;

                else return false;

        }
        catch (Exception e) {
                return false;
        }
        finally{
            preparedStatement.close();
            resultSet.close();
            conection.close();
        }

    }

}



















