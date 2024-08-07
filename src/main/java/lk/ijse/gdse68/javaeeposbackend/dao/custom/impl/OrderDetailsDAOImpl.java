package lk.ijse.gdse68.javaeeposbackend.dao.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.javaeeposbackend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean save(Connection connection, OrderDetails entity) throws SQLException {
            String sql = "INSERT INTO Order_Detail VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,entity.getCode());
            pstm.setString(2,entity.getOrderID());
            pstm.setInt(3,entity.getQty());
            pstm.setDouble(4,entity.getPrice());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
           }

    @Override
    public boolean update(Connection connection, OrderDetails entity) throws SQLException {
        String sql = "UPDATE Order_Detail SET qty=?, price=? WHERE code=? AND orderID=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1,entity.getQty());
        pstm.setDouble(2,entity.getPrice());
        pstm.setString(3,entity.getCode());
        pstm.setString(4,entity.getOrderID());
        return pstm.executeUpdate() > 0;
      }

    @Override
    public ArrayList<OrderDetails> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        return false;
    }

    @Override
    public OrderDetails findBy(Connection connection, String id) throws SQLException {
        return null;
    }
}
