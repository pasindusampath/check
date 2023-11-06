package lk.ijse.gdse67.jdbc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.gdse67.jdbc.model.ItemModel;
import lk.ijse.gdse67.jdbc.dto.ItemDTO;

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
        String id = txtItemCode.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want to delete " + id + "?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent()){
            ButtonType pressedButton = buttonType.get();
            if (pressedButton.equals(ButtonType.YES)){
                //Delete Item
                try {
                    boolean isDeleted = ItemModel.deleteItem(id);

                    if (isDeleted){
                        new Alert(Alert.AlertType.INFORMATION,"Item deleted").show();
                    }else {
                        new Alert(Alert.AlertType.INFORMATION,"Operation Fail").show();
                    }

                } catch (ClassNotFoundException |SQLException e) {
                    new Alert(Alert.AlertType.INFORMATION,"Operation Fail :( Contact Customer Care").show();
                    e.printStackTrace();
                }

            }

        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        //Collect Data From UI
        ItemDTO itemDTO = collectItemData();
        try {
            boolean isSuccess = ItemModel.saveItemData(itemDTO);
            if (isSuccess){
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
        String code = txtItemCode.getText();

        try {
            Optional<ItemDTO> item = ItemModel.getItem(code);
            if (item.isPresent()){
                ItemDTO itemDTO = item.get();
                setData(itemDTO);
            }else {
                new Alert(Alert.AlertType.ERROR,"No Item Found").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something Wrong :(").show();
            e.printStackTrace();
        }


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

    private ItemDTO collectItemData(){
        String itemCode= txtItemCode.getText();
        String name = txtItemName.getText();
        String qty = txtItemQty.getText();
        String price = txtPrice.getText();

        double price_d = Double.parseDouble(price);
        int qty_i = Integer.parseInt(qty);

        ItemDTO item = new ItemDTO();
        item.setItemCode(itemCode);
        item.setItemName(name);
        item.setPrice(price_d);
        item.setQty(qty_i);

        return item;

    }

    public void setData(ItemDTO itemDTO){
        txtItemName.setText(itemDTO.getItemName());
        txtPrice.setText(String.valueOf(itemDTO.getPrice()));
        txtItemQty.setText(String.valueOf(itemDTO.getQty()));
    }

}
