package springbook.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BConnection implements ConnectionManager{

    @Override
    public Connection getConnection() throws SQLException {

        String conn_url = "jdbc:oracle:thin:@127.0.0.1:1521/xe";
        String conn_id = "cho";
        String conn_pw = "0000";

        Connection conn = DriverManager.getConnection(conn_url, conn_id, conn_pw);

        return conn;
    }

}
