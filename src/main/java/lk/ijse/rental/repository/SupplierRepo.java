package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Admin;
import lk.ijse.rental.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
//        In here you can now save your supplier
        String sql = "INSERT INTO supplier VALUES(?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplier.getS_email());
        pstm.setObject(2, supplier.getS_name());
        pstm.setObject(3, supplier.getS_address());
        pstm.setObject(4, supplier.getS_tel());
        pstm.setObject(5, supplier.getS_id());



        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE supplier SET s_name = ?,s_address = ?,s_tel = ?,s_id = ? WHERE s_email = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, supplier.getS_name());
        pstm.setObject(2, supplier.getS_address());
        pstm.setObject(3, supplier.getS_tel());
        pstm.setObject(4, supplier.getS_id());
        pstm.setObject(5, supplier.getS_email());


        return pstm.executeUpdate() > 0;
    }
    public static Supplier searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier WHERE s_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Supplier supplier = null;

        if (resultSet.next()) {
            String s_email = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_address = resultSet.getString(3);
            String s_tel = resultSet.getString(4);
            String s_id = resultSet.getString(5);


            supplier = new Supplier(s_id, s_name, s_address, s_tel, s_email);
        }
        return supplier;
    }
    public static Supplier searchByEmail(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier WHERE s_email = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Supplier supplier = null;

        if (resultSet.next()) {
            String s_email = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_address = resultSet.getString(3);
            String s_tel = resultSet.getString(4);
            String s_id = resultSet.getString(5);


            supplier = new Supplier(s_id, s_name, s_address, s_tel, s_email);
        }
        return supplier;
    }
    public static Supplier searchByName(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier WHERE s_name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

       Supplier supplier = null;

        if (resultSet.next()) {
            String s_email = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_address = resultSet.getString(3);
            String s_tel = resultSet.getString(4);
            String s_id = resultSet.getString(5);



           supplier= new Supplier(s_id,s_name,s_address,s_tel,s_email);
        }
        return supplier;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM supplier WHERE s_name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supplierList = new ArrayList<>();
        while (resultSet.next()) {
            String s_email = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_address = resultSet.getString(3);
            String s_tel = resultSet.getString(4);
            String s_id = resultSet.getString(5);


           Supplier supplier = new Supplier(s_id,s_name,s_address,s_tel,s_email);
            supplierList.add(supplier);
        }
        return supplierList;
    }
    public static List<String> getemails() throws SQLException, ClassNotFoundException {
        String sql = "SELECT s_email FROM supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT s_id FROM supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

}
