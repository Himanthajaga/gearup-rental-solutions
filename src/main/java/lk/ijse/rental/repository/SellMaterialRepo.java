package lk.ijse.rental.repository;

import lk.ijse.rental.db.DbConnection;
import lk.ijse.rental.model.SellMaterial;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SellMaterialRepo {
    public static boolean save(List<SellMaterial> odList) throws SQLException, ClassNotFoundException {
        for (SellMaterial od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(SellMaterial od) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO sell_material VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, od.getSellId());
        pstm.setString(2, od.getBmId());
        pstm.setString(3, od.getBm_unit_price());
        pstm.setString(4, od.getBm_qty());

        return pstm.executeUpdate() > 0;
    }
}
