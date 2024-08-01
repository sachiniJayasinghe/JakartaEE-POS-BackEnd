package lk.ijse.gdse68.javaeeposbackend.dao;

import lk.ijse.gdse68.javaeeposbackend.controller.Customer;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;

import java.sql.Connection;

public sealed interface CustomerDAO permits CustomerDAOImpl {
    String saveCustomer(CustomerDto customer, Connection connection)throws Exception;
    boolean deleteCustomer(String id, Connection connection)throws Exception;
    boolean updateCustomer(String id, CustomerDto customer, Connection connection)throws Exception;
    CustomerDto getCustomer(String id,Connection connection)throws Exception;
}
