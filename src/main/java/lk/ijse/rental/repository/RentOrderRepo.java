package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.BuildingMaterial;
import lk.ijse.rental.model.RentOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentOrderRepo {
    public static String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT o_id FROM rent_order ORDER BY CAST(SUBSTRING(o_id, 2) AS UNSIGNED) desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(RentOrder rentOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO rent_order VALUES(?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, rentOrder.getOrderId());
        pstm.setDate(2, rentOrder.getDate());
        pstm.setDate(3, rentOrder.getReturnDate());
        pstm.setString(4, rentOrder.getCustomerEmail());
        pstm.setString(5, String.valueOf(rentOrder.getTotal()));

        return pstm.executeUpdate() > 0;
    }

    public static List<RentOrder> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM rent_order";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<RentOrder> rentOrderList = new ArrayList<>();
        while (resultSet.next()) {
            String o_id = resultSet.getString(1);
            Date o_date = resultSet.getDate(2); // Date.valueOf(resultSet.getString(2)
            Date o_return_date = resultSet.getDate(3); // Date.valueOf(resultSet.getString(3)
            String o_customer_email = resultSet.getString(4);
            double o_total = resultSet.getDouble(5);
            RentOrder rentOrder= new RentOrder(o_id, o_date, o_return_date, o_customer_email, o_total);


          rentOrderList.add(rentOrder);
        }
        return rentOrderList;
    }
}
