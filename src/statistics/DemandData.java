package statistics;

import Application.SqliteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DemandData implements Initializable{

    @FXML
    private PieChart pieChart;

    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
    private ObservableList<pieItems> data = FXCollections.observableArrayList();
    private int maxItems = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Connection conn = SqliteConnection.connector();


        //  System.out.println("well that worked");

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM iterms");

            while (rs.next()){
                data.add(new pieItems(rs.getString(2),rs.getInt(3)) );
            }
        }
        catch (SQLException e) {
            System.err.println("error" + e);
        }

        maxItems = data.size();

        for(pieItems x: data){
            pieData.add(new PieChart.Data(x.getName(), getPercentage(x.getValue())));
        }

     /*   pieData.add(a);
        pieData.add(b);*/

        pieChart.setData(pieData);
    }

    public int getPercentage(int value){
        int tmp = (value/maxItems)*100;
        return tmp;
    }

}
