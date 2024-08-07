package lk.ijse.gdse68.javaeeposbackend.bo.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.bo.custom.ItemBO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.ItemsDAO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.impl.ItemsDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;
import lk.ijse.gdse68.javaeeposbackend.entity.Items;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemsDAO itemDAO =new ItemsDAOImpl();

    @Override
    public boolean saveItem(Connection connection, ItemsDto itemDTO) throws SQLException {
        return itemDAO.save(connection,new Items(itemDTO.getCode(),itemDTO.getName(),itemDTO.getPrice(),itemDTO.getQty()));
    }

    @Override
    public ArrayList<ItemsDto> getAllItems(Connection connection) throws SQLException {
        ArrayList<Items> itemList = itemDAO.getAll(connection);
        ArrayList<ItemsDto> itemDTOList = new ArrayList<ItemsDto>();

        for (Items item : itemList){
            ItemsDto  dto = new ItemsDto(
                    item.getCode(),
                    item.getName(),
                    item.getPrice(),
                    item.getQty()

                    );
            itemDTOList.add(dto);
        }
        return itemDTOList;    }

    @Override
    public ItemsDto getItemById(Connection connection, String code) throws SQLException {
        Items item= itemDAO.findBy(connection,code);
        return new ItemsDto(
                item.getCode(),
                item.getName(),
                item.getPrice(),
                item.getQty()

                );
    }

    @Override
    public boolean updateItem(Connection connection, ItemsDto itemDTO) throws SQLException {
        return itemDAO.update(connection,new Items(itemDTO.getCode(),itemDTO.getName(),itemDTO.getPrice(),itemDTO.getQty()));

    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException {
        return itemDAO.delete(connection,code);
    }
}
