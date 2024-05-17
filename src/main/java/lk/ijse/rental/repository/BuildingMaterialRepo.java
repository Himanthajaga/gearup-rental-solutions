package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.BuildingMaterial;
import lk.ijse.rental.model.SellMaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingMaterialRepo {
    public static boolean save(BuildingMaterial buildingMaterial) throws SQLException, ClassNotFoundException {
//        In here you can now save your customer
        String sql = "INSERT INTO building_material VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, buildingMaterial.getBm_id());
        pstm.setString(2, buildingMaterial.getBm_desc());
        pstm.setString(3, buildingMaterial.getBm_type());
        pstm.setString(4, buildingMaterial.getBm_price());
        pstm.setString(5, buildingMaterial.getBm_qty());
        pstm.setString(6, buildingMaterial.getS_email());




        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(BuildingMaterial buildingMaterial) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE building_material SET  bm_desc = ?,bm_Type = ?,bm_price = ?,bm_amount = ? WHERE bm_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, buildingMaterial.getBm_desc());
        pstm.setString(2, buildingMaterial.getBm_type());
        pstm.setString(3, buildingMaterial.getBm_price());
        pstm.setString(4, buildingMaterial.getBm_qty());
        pstm.setObject(5, buildingMaterial.getBm_id());
        return pstm.executeUpdate() > 0;
    }
    public static BuildingMaterial searchByEmail(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM building_material WHERE s_email = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        BuildingMaterial buildingMaterial = null;

        if (resultSet.next()) {
            String bm_id = resultSet.getString(1);
            String bm_desc = resultSet.getString(2);
            String bm_type = resultSet.getString(3);
            String bm_price = resultSet.getString(4);
            String bm_qty = resultSet.getString(5);
            String s_email = resultSet.getString(6);
            buildingMaterial = new BuildingMaterial(bm_id, bm_desc, bm_type, bm_price, bm_qty, s_email);

        }
        return buildingMaterial;
    }
    public static BuildingMaterial searchById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM building_material WHERE bm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        BuildingMaterial buildingMaterial = null;

        if (resultSet.next()) {
            String bm_id = resultSet.getString(1);
            String bm_desc = resultSet.getString(2);
            String bm_type = resultSet.getString(3);
            String bm_price = resultSet.getString(4);
            String bm_qty = resultSet.getString(5);
            String s_email = resultSet.getString(6);
           buildingMaterial = new BuildingMaterial(bm_id, bm_desc, bm_type, bm_price, bm_qty, s_email);

        }
        return buildingMaterial;
    }

    public static boolean delete(String bm_id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM building_Material WHERE bm_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, bm_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<BuildingMaterial> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM building_material";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BuildingMaterial> buildingMaterialList = new ArrayList<>();
        while (resultSet.next()) {
            String bm_id = resultSet.getString(1);
            String bm_desc = resultSet.getString(2);
            String bm_type = resultSet.getString(3);
            String bm_price = resultSet.getString(4);
            String bm_qty = resultSet.getString(5);
            String s_email = resultSet.getString(6);
           BuildingMaterial buildingMaterial = new BuildingMaterial(bm_id, bm_desc, bm_type, bm_price, bm_qty,s_email);


            buildingMaterialList.add(buildingMaterial);
        }
        return buildingMaterialList;
    }

    public static List<String> getIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT bm_id FROM building_material";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static boolean updateQty(List<SellMaterial> odList) {
        for (SellMaterial od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    public static boolean updateQty(SellMaterial od) {
        String sql = "UPDATE building_material SET bm_amount = bm_amount - ? WHERE bm_id = ?";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection()
                    .prepareStatement(sql);
            pstm.setString(1, od.getBm_qty());
            pstm.setString(2, od.getBmId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static String getLastBuildingMaterialId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT bm_id FROM building_material ORDER BY CAST(SUBSTRING(bm_id, 2) AS UNSIGNED) desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}


