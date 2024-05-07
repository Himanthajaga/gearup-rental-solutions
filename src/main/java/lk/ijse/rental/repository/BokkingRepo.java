package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.Bokking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BokkingRepo {

    public static boolean save(Bokking bokking) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO bokking VALUES(?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, bokking.getBokkingId());
        pstm.setString(2, bokking.getBokkingDate());
        pstm.setString(3, bokking.getCustomerEmail());
        pstm.setString(4, bokking.getMachineId());


        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Bokking bokking) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE bokking SET b_date = ?,c_email= ?,m_id=? WHERE b_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, bokking.getBokkingDate());
        pstm.setString(2, bokking.getCustomerEmail());
        pstm.setString(3, bokking.getMachineId());
        pstm.setString(4, bokking.getBokkingId());

        return pstm.executeUpdate() > 0;
    }

    public static Bokking searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bokking WHERE b_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Bokking bokking = null;

        if (resultSet.next()) {
            String b_id = resultSet.getString(1);
            String b_date = resultSet.getString(2);
            String c_id = resultSet.getString(3);
            String m_id = resultSet.getString(4);
            bokking = new Bokking(b_id, b_date, c_id, m_id);

        }
        return bokking;
    }

    public static boolean delete(String b_id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM bokking WHERE b_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, b_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Bokking> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bokking";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Bokking> bokkingList = new ArrayList<>();
        while (resultSet.next()) {
            String b_id = resultSet.getString(1);
            String b_date = resultSet.getString(2);
            String c_id = resultSet.getString(3);
            String m_id = resultSet.getString(4);
            Bokking bokking = new Bokking(b_id, b_date, c_id, m_id);


            bokkingList.add(bokking);
        }
        return bokkingList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT b_id FROM bokking";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}