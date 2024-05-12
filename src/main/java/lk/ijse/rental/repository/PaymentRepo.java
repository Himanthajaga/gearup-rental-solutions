package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Payment;
import lk.ijse.rental.model.SupplierBuildingmaterial;

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
        pstm.setString(2, payment.getPaymentType());
        pstm.setString(3, payment.getS_email());
        pstm.setString(4, String.valueOf(payment.getPaymentAmount()));






        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment SET p_amount = ?,p_type = ?,s_email =?,WHERE p_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, payment.getPaymentId());
        pstm.setString(2, payment.getPaymentType());
        pstm.setString(3, payment.getS_email());
        pstm.setString(4, String.valueOf(payment.getPaymentAmount()));

        return pstm.executeUpdate() > 0;
    }
//    public static Payment searchById(String id) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM payment WHERE p_id = ?";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        pstm.setObject(1, id);
//        ResultSet resultSet = pstm.executeQuery();
//
//        Payment payment = null;
//
//        if (resultSet.next()) {
//
//            String p_id = resultSet.getString(1);
//            Payment payment1 = new Payment(p_id, p_amount, s_type, p_email);
//
//        }
//        return payment;
//    }
//    public static Payment searchByBMEmail(String id) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM payment WHERE bm_name = ?";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        pstm.setObject(1,id);
//        ResultSet resultSet = pstm.executeQuery();
//
//        Payment payment = null;
//
//        if (resultSet.next()) {
//
//
//            String p_id = resultSet.getString(1);
//            String s_type = resultSet.getString(2);
//            String s_email = resultSet.getString(3);
//            String p_amount = resultSet.getString(4);
//            Payment payment1 = new Payment(p_id, p_amount, s_type,s_email);
//
//        }
//        return payment;
//    }
    public static SupplierBuildingmaterial searchByBMID(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier_building_material WHERE bm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        SupplierBuildingmaterial supplierBuildingmaterial     = null;

        if (resultSet.next()) {

            String bm_id = resultSet.getString(1);
            String s_email = resultSet.getString(2);
            String amount= resultSet.getString(3);
            String bm_desc = resultSet.getString(4);


            SupplierBuildingmaterial payment1 = new SupplierBuildingmaterial(bm_id, s_email, amount, bm_desc);

        }
        return supplierBuildingmaterial;
    }
    public static SupplierBuildingmaterial searchByBMDesc(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier_building_material WHERE bm_desc = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        SupplierBuildingmaterial supplierBuildingmaterial     = null;

        if (resultSet.next()) {

            String bm_id = resultSet.getString(1);
            String s_email = resultSet.getString(2);
            String amount= resultSet.getString(4);
            String bm_desc = resultSet.getString(5);


            SupplierBuildingmaterial payment1 = new SupplierBuildingmaterial(bm_id, s_email, amount, bm_desc);

        }
        return supplierBuildingmaterial;
    }
//    public static Payment searchByType(String id) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM payment WHERE bm_type = ?";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        pstm.setObject(1, id);
//        ResultSet resultSet = pstm.executeQuery();
//
//        Payment payment = null;
//
//        if (resultSet.next()) {
//
//            String p_id = resultSet.getString(1);
//            String p_amount = resultSet.getString(2);
//            String s_type = resultSet.getString(3);
//            String p_email = resultSet.getString(4);
//
//            Payment payment1 = new Payment(p_id, p_amount, s_type, p_email);
//
//        }
//        return payment;
//    }

//    public static boolean delete(String mec_id) throws SQLException, ClassNotFoundException {
//        String sql = "DELETE FROM payment WHERE p_id = ?";
//        PreparedStatement pstm = DbConnection.getInstance().getConnection()
//                .prepareStatement(sql);
//
//        pstm.setObject(1, mec_id);
//
//        return pstm.executeUpdate() > 0;
//    }

    public static List<Payment> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String p_id = resultSet.getString(1);
            String p_type = resultSet.getString(2);
            String s_email = resultSet.getString(3);
            String p_amount = resultSet.getString(4);
            Payment payment = new Payment(p_id, p_type, s_email, Double.parseDouble(p_amount));


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
    public static String currentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT p_id FROM payment ORDER BY CAST(SUBSTRING(p_id, 2) AS UNSIGNED) desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
