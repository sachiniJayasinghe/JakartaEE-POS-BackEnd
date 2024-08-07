package lk.ijse.gdse68.javaeeposbackend.dao.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.dao.CrudUtil;
import lk.ijse.gdse68.javaeeposbackend.dao.DBConnectionPool;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.ItemsDAO;
import lk.ijse.gdse68.javaeeposbackend.entity.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsDAOImpl implements ItemsDAO {
    @Override
    public boolean save(Connection connection, Items entity) throws SQLException {
        System.out.println("Saving Item: " + entity.getCode() + ", " + entity.getName() + ", " + entity.getQty() + ", " + entity.getPrice());
        String sql = "INSERT INTO Items (code,name,price,qty) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(connection, sql, entity.getCode(), entity.getName(), entity.getPrice(), entity.getQty());    }

    @Override
    public boolean update(Connection connection, Items entity) throws SQLException {
        System.out.println("Updating Item: " + entity.getCode() + ", " + entity.getName() + ", " + entity.getQty() + ", " + entity.getPrice());
        String sql = "UPDATE Items SET name = ?, price = ?,qty = ? WHERE code = ?";
        return CrudUtil.execute(connection, sql, entity.getName(), entity.getPrice(), entity.getQty(), entity.getCode());    }

    @Override
    public ArrayList<Items> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM Items";
        ArrayList<Items> itemArrayList = new ArrayList<Items>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while(rst.next()){
            Items Items = new Items(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4)


            );

            itemArrayList.add(Items);
        }
        return itemArrayList;    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException {
        String sql = "DELETE FROM Items WHERE code=?";
        return CrudUtil.execute(connection,sql,code);    }

    @Override
    public Items findBy(Connection connection, String code) throws SQLException {
        String sql = "SELECT * FROM Items WHERE code=?";
        Items item =new Items();
        ResultSet rst = CrudUtil.execute(connection,sql,code);

        if (rst.next()){
            item.setCode(rst.getString(1));
            item.setName(rst.getString(2));
            item.setPrice(rst.getBigDecimal(3));
            item.setQty(Integer.parseInt(rst.getString(4)));

        }
        return item;    }

    @Override
    public Boolean updateQty(String code, int qty) throws Exception {
        try(Connection connection = DBConnectionPool.getConnection()){
            System.out.println("ItemsDAOImpl: " + code + " " + qty);
            PreparedStatement pstm = connection.prepareStatement("UPDATE Items SET qty=qty-? WHERE code=?");
            pstm.setInt(1,qty);
            pstm.setString(2,code);
            return pstm.executeUpdate()>0;
        }
    }
    }
}
