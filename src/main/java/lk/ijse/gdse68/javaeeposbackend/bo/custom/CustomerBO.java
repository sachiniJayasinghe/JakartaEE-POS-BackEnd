package lk.ijse.gdse68.javaeeposbackend.bo.custom;

import lk.ijse.gdse68.javaeeposbackend.bo.SuperBO;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(Connection connection, CustomerDto customerDTO) throws SQLException;

    ArrayList<CustomerDto> getAllCustomers(Connection connection) throws SQLException;

    CustomerDto getCustomerById(Connection connection, String cusID) throws SQLException;

    boolean updateCustomer(Connection connection, CustomerDto customerDTO) throws SQLException;

    boolean deleteCustomer(Connection connection, String cusID) throws SQLException;
}
