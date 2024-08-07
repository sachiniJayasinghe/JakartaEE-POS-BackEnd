package lk.ijse.gdse68.javaeeposbackend.bo.custom.impl;

import ch.qos.logback.core.db.ConnectionSource;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.ItemsDAO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.OrderDAO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.impl.ItemsDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;
import lk.ijse.gdse68.javaeeposbackend.dto.OrderDetailsDto;
import lk.ijse.gdse68.javaeeposbackend.dto.OrdersDto;
import lk.ijse.gdse68.javaeeposbackend.entity.OrderDetails;
import lk.ijse.gdse68.javaeeposbackend.entity.Orders;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    ItemsDAO itemDAO = new ItemsDAOImpl();
    DataSource connectionPool;

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemsDto searchItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemsDto> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean purchaseOrder(OrdersDto dto) throws SQLException, ClassNotFoundException {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);

            Boolean orderResult = orderDAO.save(connection,new Orders(dto.getOrderID(), dto.getOrderDate(), dto.getCusID()));

            if (orderResult) {
                // Save Order Details
                for (OrderDetailsDto orderDetail : dto.getOrderItems()) {
                    Boolean orderDetailResult = orderDetailsDAO.save(connection,new OrderDetails(orderDetail.getCode(), orderDetail.getOrderID(), orderDetail.getQty(), orderDetail.getPrice()));

                    if (!orderDetailResult) {
                        System.out.println("Failed to save order details");
                        connection.rollback();
                        return false;
                    }

                    // Update Item Quantity
                    Boolean updateQuantityResult = itemDAO.updateQty(orderDetail.getCode(),orderDetail.getQty());
                    if (!updateQuantityResult) {
                        connection.rollback();
                        return false;
                    }
                }
                connection.commit();
                return true; // Commit successful
            } else {
                connection.rollback();
                return false; // Rollback due to order failure
            }
        } catch (Exception e) {

            throw new RuntimeException("Error processing purchase order", e);
        }
    }
        @Override
    public ItemsDto findItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
