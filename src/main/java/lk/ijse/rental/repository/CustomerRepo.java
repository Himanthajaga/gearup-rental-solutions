package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static String c_id;
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, customer.getC_mail());
        pstm.setString(2, customer.getC_name());
        pstm.setString(3, customer.getC_address());
        pstm.setString(4, customer.getC_tel());
        pstm.setString(5, customer.getC_id());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET c_name = ?, c_address = ?, c_tel = ?,c_id = ? WHERE c_email = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, customer.getC_name());
        pstm.setObject(2, customer.getC_address());
        pstm.setObject(3, customer.getC_tel());
        pstm.setObject(4, customer.getC_id());
        pstm.setObject(5, customer.getC_mail());

        return pstm.executeUpdate() > 0;
    }
    public static Customer searchbyname(String c_mail) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE c_name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, c_mail);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String email = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String cus_id = resultSet.getString(5);


            customer = new Customer(email, name, address, tel, cus_id);
        }
        return customer;
    }
    public static Customer searchByemail(String c_mail) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE c_email = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, c_mail);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String email = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String cus_id = resultSet.getString(5);


            customer = new Customer(email, name, address, tel, cus_id);
        }
        return customer;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE c_email = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> customersList = new ArrayList<>();
        while (resultSet.next()) {
            String email = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String cus_id = resultSet.getString(5);

            Customer customer = new Customer(email, name, address, tel, cus_id);
            customersList.add(customer);
        }
        return customersList;
    }

    public static List<String> getEmails() throws SQLException, ClassNotFoundException {
        String sql = "SELECT c_email FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}

