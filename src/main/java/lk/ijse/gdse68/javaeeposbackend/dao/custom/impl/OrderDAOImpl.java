package lk.ijse.gdse68.javaeeposbackend.dao.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.dao.custom.OrderDAO;
import lk.ijse.gdse68.javaeeposbackend.entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Connection connection, Orders entity) throws SQLException {
        String sql = "INSERT INTO Orders VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,entity.getOrderID());
        pstm.setDate(2, entity.getOrderDate());
        pstm.setString(3,entity.getCusID());
        int rowsAffected = pstm.executeUpdate();
        return rowsAffected > 0;
        }

    @Override
    public boolean update(Connection connection, Orders entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Orders> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        return false;
    }

    @Override
    public Orders findBy(Connection connection, String id) throws SQLException {
        return null;
    }
}
