package lk.ijse.gdse68.javaeeposbackend.controller;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.CustomerBO;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/CustomerServletAPI",loadOnStartup = 2)
public class CustomerServletAPI  extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerServletAPI.class);
    CustomerBO customerBO = (CustomerBO) new CustomerBOImpl();
    DataSource connectionPool;

    @Override
    public void init() throws ServletException {
        logger.info("Init method invoked");
        try {
            InitialContext ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/thogakade");
            this.connectionPool = dataSource;
        } catch (NamingException e) {
            throw new ServletException("Cannot find JNDI resource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String customerId = req.getParameter("customerId");
        if (customerId != null) {
            getAll(customerId, resp);
            return;
        }
        try {
            List<CustomerDto> allCustomers = customerBO.getAllCustomers(connectionPool.getConnection());
            JsonArrayBuilder allCustomersArray = Json.createArrayBuilder();

            for (CustomerDto customer : allCustomers) {
                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("cusID", customer.getCusID())
                        .add("cusName", customer.getCusName())
                        .add("cusAddress", customer.getCusAddress())
                        .add("cusSalary", customer.getCusSalary());
                allCustomersArray.add(customerObject);
            }
            PrintWriter writer = resp.getWriter();
            writer.print(allCustomersArray.build());
        } catch (SQLException e) {
            throw new ServletException("Error in doGet method", e);
        }
    }

    private void getAll(String customerId, HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");

        try (Connection connection = connectionPool.getConnection()) {
            String sql = "SELECT * FROM Customer WHERE cusID=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, customerId);
            ResultSet rst = pstm.executeQuery();

            PrintWriter writer = resp.getWriter();

            JsonArrayBuilder customerArray = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString("cusID");
                String name = rst.getString("cusName");
                String address = rst.getString("cusAddress");
                double salary = rst.getDouble("cusSalary");

                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("cusID", id)
                        .add("cusName", name)
                        .add("cusAddress", address)
                        .add("cusSalary", salary);

                customerArray.add(customerObject);
            }
            writer.print(customerArray.build());
        } catch ( SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDto customerDTO = jsonb.fromJson(req.getReader(), CustomerDto.class);
            System.out.println(customerDTO);

            if (customerDTO.getCusID() == null || !customerDTO.getCusID().matches("^(C00-)[0-9]{3}$")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Id is empty or invalid!!");
                return;
            } else if (customerDTO.getCusName() == null || !customerDTO.getCusName().matches("^[A-Za-z ]{4,}$")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Name is empty or invalid!!");
                return;
            } else if (customerDTO.getCusAddress() == null || !customerDTO.getCusAddress().matches("^[A-Za-z0-9., -]{5,}$")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Address is empty or invalid!!");
                return;
            } else if (customerDTO.getCusSalary() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Salary is empty or invalid!!");
                return;
            }

            boolean isSaved = customerBO.saveCustomer(connection, customerDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Failed to save customer.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write("Duplicate values. Please check again.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            CustomerDto customerDTO = jsonb.fromJson(req.getReader(), CustomerDto.class);
            System.out.println(customerDTO);

            if (customerDTO.getCusID() == null || !customerDTO.getCusID().matches("^(C00-)[0-9]{3}$")) {
                resp.getWriter().write("id is empty or invalid!");
                return;
            } else if (customerDTO.getCusName() == null || !customerDTO.getCusName().matches("^[A-Za-z ]{4,}$")) {
                resp.getWriter().write("Name is empty or invalid! ");
                return;
            } else if (customerDTO.getCusAddress() == null || !customerDTO.getCusAddress().matches("^[A-Za-z0-9., -]{5,}$")) {
                resp.getWriter().write("Address is empty or invalid");
                return;
            } else if (customerDTO.getCusSalary() <= 0) {
                resp.getWriter().write("Salary is empty or invalid!!");
                return;

            }
            boolean isUpdated = customerBO.updateCustomer(connection, customerDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to update customer");
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        try (Connection connection = connectionPool.getConnection()){
            boolean isDeleted = customerBO.deleteCustomer(connection,cusID);
            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete customer!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean deleteCustomerById(String cusID) {
        // Implement the logic to delete a customer by ID
        // Return true if the customer was deleted successfully, false otherwise
        return true; // Placeholder
    }


    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }
}
