package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO payment VALUES(?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, payment.getPaymentId());
        pstm.setString(2, payment.getPaymentAmount());
        pstm.setString(3, payment.getPaymentType());
        pstm.setString(4, payment.getCid());





        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment SET p_amount = ?,p_type = ?,c_id = ? ,WHERE p_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, payment.getPaymentAmount());
        pstm.setString(2, payment.getPaymentType());
        pstm.setString(3, payment.getPaymentId());
        pstm.setString(4, payment.getCid());

        return pstm.executeUpdate() > 0;
    }
    public static Payment searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment WHERE p_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        Payment payment = null;

        if (resultSet.next()) {
            String p_id = resultSet.getString(1);
            String p_amount = resultSet.getString(2);
            String p_type = resultSet.getString(3);
            String c_id = resultSet.getString(4);

            Payment payment1 = new Payment(p_id,p_amount, p_type,c_id);

        }
        return payment;
    }

    public static boolean delete(String mec_id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM payment WHERE p_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, mec_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Payment> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String p_id = resultSet.getString(1);
            String p_amount = resultSet.getString(2);
            String p_type = resultSet.getString(3);
            String c_id = resultSet.getString(4);
            Payment payment = new Payment(p_id,p_amount, p_type,c_id);


           paymentList.add(payment);
        }
        return paymentList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT p_id FROM payment";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
