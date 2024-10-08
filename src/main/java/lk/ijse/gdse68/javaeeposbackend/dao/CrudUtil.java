package lk.ijse.gdse68.javaeeposbackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T execute (Connection connection, String sql, Object... args) throws SQLException {
        PreparedStatement pstm = connection.prepareStatement(sql); // DbConnection class is that not using ,using to web app web-inf is this connected to db connection

        for (int i=0; i<args.length; i++){
            pstm.setObject((i+1),args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")){
            return (T) pstm.executeQuery(); // result set ek
        }

        return (T) (Boolean) (pstm.executeUpdate() > 0);
    }
}
