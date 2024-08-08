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
import lk.ijse.gdse68.javaeeposbackend.bo.custom.ItemBO;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse68.javaeeposbackend.dao.CrudUtil;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet(name = "item" ,urlPatterns = "/item" ,loadOnStartup = 3)
public class ItemsServletAPI extends HttpServlet {
    ItemBO itemBO =new ItemBOImpl();
    DataSource connectionPool;
    Connection connection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("code");
        resp.addHeader("Content-Type", "application/json");

        if(itemId != null){
            try {
                String sql = "SELECT * FROM Items WHERE code=?";
                ResultSet rst = CrudUtil.execute(connection, sql, itemId);

                PrintWriter writer = resp.getWriter();
                JsonArrayBuilder allItems = Json.createArrayBuilder();

                while (rst.next()) {
                    String code = rst.getString("code");
                    String name = rst.getString("name");
                    double price = rst.getDouble("price");
                    int qty = rst.getInt("qty");

                    JsonObjectBuilder item = Json.createObjectBuilder();

                    item.add("code", code);
                    item.add("name", name);
                    item.add("price", price);
                    item.add("qty", qty);
                    allItems.add(item.build());
                }
                writer.print(allItems.build());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }


        try {
            ArrayList<ItemsDto> Items = itemBO.getAllItems(connectionPool.getConnection());
            resp.setContentType("application/json");

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            PrintWriter writer = resp.getWriter();

            for (ItemsDto item : Items) {
                JsonObjectBuilder items = Json.createObjectBuilder();
                items.add("code", item.getCode());
                items.add("name", item.getName());
                items.add("price", item.getPrice());
                items.add("qty", item.getQty());

                allItems.add(items.build());
            }
            writer.print(allItems.build());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            // Debugging: Print raw JSON input
            String rawJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Received JSON: " + rawJson);

            // Attempt to deserialize raw JSON
            ItemsDto itemDTO;
            try {
                itemDTO = jsonb.fromJson(rawJson, ItemsDto.class);
            } catch (JsonbException e) {
                // Attempt to handle JSON array
                ItemsDto[] itemsArray = jsonb.fromJson(rawJson, ItemsDto[].class);
                if (itemsArray.length > 0) {
                    itemDTO = itemsArray[0]; // Example: Take the first item from the array
                } else {
                    resp.getWriter().write("Empty JSON array received!!");
                    return;
                }
            }

            System.out.println(itemDTO);

            // Validate itemDTO fields
            if (itemDTO.getCode() == null || !itemDTO.getCode().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("Item id is empty or invalid!!");
                return;
            } else if (itemDTO.getName() == null || !itemDTO.getName().matches("^[A-Za-z ]{4,}$")) {
                resp.getWriter().write("Name is empty or invalid!!");
                return;
            } else if (itemDTO.getQty() <= 0) {
                resp.getWriter().write("Quantity is empty or invalid!!");
                return;
            } else if (itemDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                resp.getWriter().write("Price is invalid!!");
                return;
            }

            // Attempt to save item
            boolean isSaved = itemBO.saveItem(connection, itemDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save item");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }}
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            // Debugging: Print raw JSON input
            String rawJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Received JSON: " + rawJson);

            // Attempt to deserialize raw JSON
            ItemsDto itemDTO;
            try {
                itemDTO = jsonb.fromJson(rawJson, ItemsDto.class);
            } catch (JsonbException e) {
                // Attempt to handle JSON array
                ItemsDto[] itemsArray = jsonb.fromJson(rawJson, ItemsDto[].class);
                if (itemsArray.length > 0) {
                    itemDTO = itemsArray[0]; // Example: Take the first item from the array
                } else {
                    resp.getWriter().write("Empty JSON array received!!");
                    return;
                }
            }

            System.out.println(itemDTO);

            // Validate itemDTO fields
            if (itemDTO.getCode() == null || !itemDTO.getCode().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("Item id is empty or invalid!!");
                return;
            } else if (itemDTO.getName() == null || !itemDTO.getName().matches("^[A-Za-z ]{4,}$")) {
                resp.getWriter().write("Name is empty or invalid!!");
                return;
            } else if (itemDTO.getQty() <= 0) {
                resp.getWriter().write("Quantity is empty or invalid!!");
                return;
            } else if (itemDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                resp.getWriter().write("Price is invalid!!");
                return;
            }

            // Attempt to save item
            boolean isUpdated = itemBO.updateItem(connection, itemDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save item");
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
        String code = req.getParameter("code");
        try (Connection connection = connectionPool.getConnection()){
            boolean isDeleted = itemBO.deleteItem(connection,code);
            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete item!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);    }

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext(); //get connection to connection pool
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/thogakade");
            this.connectionPool = dataSource;
        } catch (NamingException e) {
            throw new ServletException("Cannot find JNDI resource", e);
        }
    }
}
