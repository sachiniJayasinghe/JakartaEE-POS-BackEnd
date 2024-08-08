package lk.ijse.gdse68.javaeeposbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse68.javaeeposbackend.bo.custom.impl.PurchaseOrderBOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.ItemsDto;
import lk.ijse.gdse68.javaeeposbackend.dto.OrdersDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "PlaceOrderServletAPI", urlPatterns = "/order")
public class PlaceOrderServletAPI extends HttpServlet {
    PurchaseOrderBO purchaseOrderBO = new PurchaseOrderBOImpl();
    DataSource connectionPool;
    private static final Logger logger = LoggerFactory.getLogger(PlaceOrderServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PlaceOrderServletAPI doPost");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/plain");

        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            OrdersDto orderDto  = jsonb.fromJson(req.getReader(), OrdersDto.class);
            System.out.println(orderDto);

            Boolean saved = purchaseOrderBO.purchaseOrder(orderDto);

            if (saved) {
                resp.getWriter().write("Order successfully saved.");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.getWriter().write("Failed to save order.");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            logger.error("Error processing request", e); // Log the full stack trace
            resp.getWriter().write("Error processing request: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            connectionPool = (DataSource) envContext.lookup("jdbc/thogakade");

            if (connectionPool == null) {
                throw new ServletException("DataSource lookup failed. DataSource is null.");
            }
            System.out.println("Connection pool initialized successfully.");
        } catch (NamingException e) {
            throw new ServletException("Cannot find JNDI resource", e);
        }
    }
}
