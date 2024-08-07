package lk.ijse.gdse68.javaeeposbackend.bo.custom;

import lk.ijse.gdse68.javaeeposbackend.bo.SuperBO;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;
import lk.ijse.gdse68.javaeeposbackend.dto.OrdersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException ;
    public ItemsDto searchItem(String code) throws SQLException, ClassNotFoundException ;
    public boolean existItem(String code) throws SQLException, ClassNotFoundException;
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
    public String generateOrderID() throws SQLException, ClassNotFoundException ;
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;
    public ArrayList<ItemsDto> getAllItems() throws SQLException, ClassNotFoundException;
    public boolean purchaseOrder(OrdersDto dto)throws SQLException, ClassNotFoundException;
    public ItemsDto findItem(String code)throws SQLException, ClassNotFoundException;

}
