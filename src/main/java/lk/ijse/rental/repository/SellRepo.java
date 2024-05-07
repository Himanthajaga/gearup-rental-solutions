package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Sell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellRepo {
    public static String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT se_id FROM sell ORDER BY se_id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Sell sell) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO sell VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, sell.getSellId());
        pstm.setDate(2, sell.getSellDate());
        pstm.setString(3, sell.getC_email());
        pstm.setString(4, String.valueOf(sell.getTotal()));

        return pstm.executeUpdate() > 0;
    }

    public static List<Sell> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM sell";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Sell> selllList = new ArrayList<>();
        while (resultSet.next()) {
            String se_id = resultSet.getString(1);
            java.sql.Date se_date = resultSet.getDate(2);
            String c_email = resultSet.getString(3);
            double se_total = resultSet.getDouble(4);
            Sell sell = new Sell(se_id, se_date, c_email, se_total);

            selllList.add(sell);


            selllList.add(sell);
        }
        return selllList;
    }
}