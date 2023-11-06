package lk.ijse.gdse67.jdbc.model;

import lk.ijse.gdse67.jdbc.db.DBConnection;
import lk.ijse.gdse67.jdbc.dto.ItemDTO;

import java.sql.*;
import java.util.Optional;

public class ItemModel {



    public static boolean saveItemData(ItemDTO itemDTO) throws ClassNotFoundException, SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection con = dbConnection.getConnection();

        PreparedStatement ps = con.prepareStatement("insert into item(itemCode,itemName,itemQty,itemPrice) values (?,?,?,?)");

        ps.setString(1,itemDTO.getItemCode());
        ps.setString(2,itemDTO.getItemName());
        ps.setInt(3,itemDTO.getQty());
        ps.setDouble(4,itemDTO.getPrice());

        int affectedRows = ps.executeUpdate();

        if (affectedRows>0){
            return true;
        }else {
            return false;
        }


    }

    public static boolean deleteItem(String id) throws ClassNotFoundException, SQLException {
        DBConnection db = DBConnection.getInstance();
        Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement("delete from item where itemCode = ? ");
        ps.setString(1,id);

        int affectedRows = ps.executeUpdate();

        if (affectedRows>0){
            return true;
        }else {
            return false;
        }
    }

    public static Optional<ItemDTO> getItem(String itemCode) throws ClassNotFoundException, SQLException {
        DBConnection db = DBConnection.getInstance();
        Connection con = db.getConnection();

        PreparedStatement ps = con.prepareStatement("select * from item where itemCode = ? ");
        ps.setString(1,itemCode);

        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            String code = rs.getString(1);
            String name = rs.getString(2);
            int qty = rs.getInt(3);
            double price = rs.getDouble(4);

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemCode(code);
            itemDTO.setItemName(name);
            itemDTO.setPrice(price);
            itemDTO.setQty(qty);

            return Optional.of(itemDTO);

        }else {
           return Optional.empty();
        }
    }

}
