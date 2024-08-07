package lk.ijse.gdse68.javaeeposbackend.dao.custom.impl;

import lk.ijse.gdse68.javaeeposbackend.dao.CrudUtil;
import lk.ijse.gdse68.javaeeposbackend.dao.DBConnectionPool;
import lk.ijse.gdse68.javaeeposbackend.dao.custom.CustomerDAO;
import lk.ijse.gdse68.javaeeposbackend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql = "INSERT INTO customer (cusID,cusName,cusAddress,cusSalary) VALUES (?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getCusID(),entity.getCusName(),entity.getCusAddress(),entity.getCusSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {
        String sql = "UPDATE customer SET cusName = ?, cusAddress = ?,cusSalary = ? WHERE cusID = ?";
        return CrudUtil.execute(connection,sql,entity.getCusName(),entity.getCusAddress(),entity.getCusSalary(),entity.getCusID());    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer";
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while(rst.next()){
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            );

            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE cusID=?";
        return CrudUtil.execute(connection,sql,id);
    }

    @Override
    public Customer findBy(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cusID=?";
        Customer customer =new Customer();
        ResultSet rst = CrudUtil.execute(connection,sql,id);

        if (rst.next()){
            customer.setCusID(rst.getString(1));
            customer.setCusName(rst.getString(2));
            customer.setCusAddress(rst.getString(3));
            customer.setCusSalary(Double.valueOf(rst.getString(4)));
        }
        return customer;
    }

}