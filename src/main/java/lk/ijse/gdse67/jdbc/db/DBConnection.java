package lk.ijse.gdse67.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;

    private Connection connection;
    private DBConnection() throws ClassNotFoundException, SQLException {
        System.out.println("DB COnnection Created");
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test?createDatabaseIfNotExist=true","root","IJSE@1234");
    }


    public Connection getConnection(){
        return connection;
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }

}
