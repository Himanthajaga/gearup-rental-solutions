package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AdminRepo {
    public static String adminId;

    public boolean save(Admin admin) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO admin VALUES(?, ?, ?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, admin.getA_id());
        pstm.setObject(2, admin.getA_name());
        pstm.setObject(3, admin.getA_password());
        pstm.setObject(4, admin.getA_confirmPassword());
        pstm.setObject(5, admin.getA_email());


        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public boolean update(Admin admin) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE admin SET a_name = ?,a_password = ?,a_confirmPassword =?,a_email=? WHERE a_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);


        pstm.setObject(1, admin.getA_name());
        pstm.setObject(2, admin.getA_password());
        pstm.setObject(3, admin.getA_confirmPassword());
        pstm.setObject(4, admin.getA_email());
        pstm.setObject(3, admin.getA_id());


        return pstm.executeUpdate() > 0;
    }

    public boolean searchByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM admin WHERE a_email = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, email);
        ResultSet resultSet = pstm.executeQuery();

        Admin admin = null;

        if (resultSet.next()) {
            String a_id = resultSet.getString(1);
            String a_name = resultSet.getString(2);
            String a_pasword = resultSet.getString(3);
            String a_confirmPasword = resultSet.getString(4);
            String a_email = resultSet.getString(5);


            admin = new Admin(a_id, a_name, a_pasword, a_confirmPasword, a_email);
        }
        return admin != null;
    }
    public boolean searchEmailAndUsername(String username, String email) throws SQLException, ClassNotFoundException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM admin WHERE a_id=? AND a_email=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,username);

        pstm.setString(2,email);

        return pstm.executeQuery().next();
    }
    public boolean searchByName(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM admin WHERE a_name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Admin admin = null;

        if (resultSet.next()) {
            String a_id = resultSet.getString(1);
            String a_name = resultSet.getString(2);
            String a_pasword = resultSet.getString(3);
            String a_confirmPasword = resultSet.getString(4);
            String a_email = resultSet.getString(5);

            admin = new Admin(a_id, a_name, a_pasword, a_confirmPasword, a_email);
        }
        return admin != null;
    }
    public boolean searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM admin WHERE a_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Admin admin = null;

        if (resultSet.next()) {
            String a_id = resultSet.getString(1);
            String a_name = resultSet.getString(2);
            String a_pasword = resultSet.getString(3);
            String a_confirmPasword = resultSet.getString(4);
            String a_email = resultSet.getString(5);

            admin = new Admin(a_id, a_name, a_pasword, a_confirmPasword, a_email);
        }
        return admin != null;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM admin WHERE a_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public List<Admin> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM admin";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Admin> adminList = new ArrayList<>();
        while (resultSet.next()) {
            String a_id = resultSet.getString(1);
            String a_name = resultSet.getString(2);
            String a_pasword = resultSet.getString(3);
            String a_confirmPasword = resultSet.getString(4);
            String a_email = resultSet.getString(5);

            Admin admin = new Admin(a_id, a_name, a_pasword, a_confirmPasword, a_email);
            adminList.add(admin);
        }
        return adminList;
    }

    public List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT a_id FROM admin";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}


