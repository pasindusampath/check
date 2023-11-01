package lk.ijse.gdse67.jdbc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class MainFormController {

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableView<?> tblItems;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemQty;

    @FXML
    private TextField txtPrice;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String text = txtItemCode.getText();

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure To Delete Item?", ButtonType.YES, ButtonType.NO).showAndWait();

        if (buttonType.isPresent()){
            ButtonType buttonType1 = buttonType.get();
            if (buttonType1.equals(ButtonType.NO)){
                return;
            }
        }else {
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test", "root", "IJSE@1234");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        //Collect Data From UI
        String itemCode = txtItemCode.getText();
        String itemName = txtItemName.getText();
        String itemQty = txtItemQty.getText();
        String price = txtPrice.getText();

        int qtyIntValue = Integer.parseInt(itemQty);
        double priceDoubleValue = Double.parseDouble(price);


        try {
            //Load Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Establish Connection with specific Database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test", "root", "IJSE@1234");

            //Prepare SQL
            PreparedStatement ps = con.prepareStatement("insert into item(itemCode,itemName,itemQty,itemPrice) values (?,?,?,?)");

            //Injecting Values
            ps.setString(1,itemCode);
            ps.setString(2,itemName);
            ps.setInt(3,qtyIntValue);
            ps.setDouble(4,priceDoubleValue);

            //Executing SQL Query
            int affectedRows = ps.executeUpdate();

            System.out.println(affectedRows);

            if (affectedRows>0){
                new Alert(Alert.AlertType.INFORMATION,"Data Added Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Wrong :(").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }


    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String itemCode= txtItemCode.getText();
        String name = txtItemName.getText();
        String qty = txtItemQty.getText();
        String price = txtPrice.getText();

        int qtyIntValue = Integer.parseInt(qty);
        double priceDoubleValue = Double.parseDouble(price);



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test", "root", "IJSE@1234");

            PreparedStatement ps = con.prepareStatement("update item set itemName=?,itemQty=?,itemPrice=? where itemCode = ?");

            ps.setString(1,name);
            ps.setInt(2,qtyIntValue);
            ps.setDouble(3,priceDoubleValue);
            ps.setString(4,itemCode);

            int affectedRows = ps.executeUpdate();

            if (affectedRows>0){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something Wrong :(").show();
            }


        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            e.printStackTrace();
        }

    }

}
