package lk.ijse.gdse68.javaeeposbackend.bo.custom;

import lk.ijse.gdse68.javaeeposbackend.bo.SuperBO;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean saveItem(Connection connection, ItemsDto itemDTO) throws SQLException;

    ArrayList<ItemsDto> getAllItems(Connection connection) throws SQLException;

    ItemsDto getItemById(Connection connection, String code) throws SQLException;

    boolean updateItem(Connection connection, ItemsDto itemDTO) throws SQLException;

    boolean deleteItem(Connection connection, String code) throws SQLException;
}
