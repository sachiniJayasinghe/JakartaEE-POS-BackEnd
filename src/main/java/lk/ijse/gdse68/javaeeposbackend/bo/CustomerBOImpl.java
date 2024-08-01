package lk.ijse.gdse68.javaeeposbackend.bo;

import lk.ijse.gdse68.javaeeposbackend.dao.CustomerDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;

import java.sql.Connection;

public class CustomerBOImpl implements CustomerBO {

    @Override
    public String saveCustomer(CustomerDto customer, Connection connection) throws Exception {
        var customerDAOImpl = new CustomerDAOImpl();
        return customerDAOImpl.saveCustomer(customer, connection);    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        return false;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDto customer, Connection connection) throws Exception {
        return false;
    }

    @Override
    public CustomerDto getCustomer(String id, Connection connection) throws Exception {
        return null;
    }
}
