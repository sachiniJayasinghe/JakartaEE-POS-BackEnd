package lk.ijse.gdse68.javaeeposbackend.bo.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.bo.custom.CustomerBO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.CustomerDAO;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;
import lk.ijse.gdse68.javaeeposbackend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) new CustomerDAOImpl();


    @Override
    public boolean saveCustomer(Connection connection, CustomerDto customerDTO) throws SQLException {
        return customerDAO.save(connection,new Customer(customerDTO.getCusID(), customerDTO.getCusName(), customerDTO.getCusAddress(),customerDTO.getCusSalary()));
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<Customer> customersList = customerDAO.getAll(connection);
        ArrayList<CustomerDto> customerDTOList = new ArrayList<CustomerDto>();

        for (Customer customer : customersList){
            CustomerDto dto = new CustomerDto(

                    customer.getCusID(),
                    customer.getCusName(),
                    customer.getCusAddress(),
                    customer.getCusSalary()
            );
            customerDTOList.add(dto);
        }
        return customerDTOList;    }

    @Override
    public CustomerDto getCustomerById(Connection connection, String cusID) throws SQLException {
        Customer customer = customerDAO.findBy(connection,cusID);
        return new CustomerDto(
                customer.getCusID(),
                customer.getCusName(),
                customer.getCusAddress(),
                customer.getCusSalary()
        );    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDto customerDTO) throws SQLException {
        return customerDAO.update(connection,new Customer(customerDTO.getCusID(), customerDTO.getCusName(), customerDTO.getCusAddress(),customerDTO.getCusSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String cusID) throws SQLException {
        return customerDAO.delete(connection,cusID);

    }
}
