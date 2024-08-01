package lk.ijse.gdse68.javaeeposbackend.dao;

import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerDAOImpl implements CustomerDAO {
    public static String SAVE_Customer = "INSERT INTO customer (cusID,cusName,cusAddress,cusSalary) VALUES(?,?,?,?)";

    @Override
    public String saveCustomer(CustomerDto customer, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_Customer);
            ps.setString(1, customer.getCusID());
            ps.setString(2, customer.getCusName());
            ps.setString(3, customer.getCusAddress());
            ps.setString(4, String.valueOf(customer.getCusSalary()));
            if(ps.executeUpdate() != 0){
                return "Customer Save Successfully";
            }else {
                return "Failed to Save customer";
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }    }

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
