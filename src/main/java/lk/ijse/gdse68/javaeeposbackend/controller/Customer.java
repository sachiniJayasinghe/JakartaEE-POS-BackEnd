package lk.ijse.gdse68.javaeeposbackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.javaeeposbackend.bo.CustomerBOImpl;
import lk.ijse.gdse68.javaeeposbackend.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer",loadOnStartup = 2)
public class Customer extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(Customer.class);
    Connection connection;


    @Override
    public void init() throws ServletException {
        logger.info("Init method invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/thogakade");
            this.connection = pool.getConnection();
            logger.debug("Connection initialized", this.connection);

        } catch (SQLException | NamingException e) {
            logger.error("DB connection not init");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Request not matched with the criteria");
            }
            try (var writer = resp.getWriter()) {
                Jsonb jsonb = JsonbBuilder.create();
                var customerBOImpl = new CustomerBOImpl();
                CustomerDto customer = jsonb.fromJson(req.getReader(), CustomerDto.class);
                logger.info("Invoke idGenerate()");
//                student.setId(Util.idGenerate());
                //Save data in the DB
                writer.write(customerBOImpl.saveCustomer(customer, connection));
                logger.info("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (Exception e) {
                logger.error("Connection failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }


        }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

}
