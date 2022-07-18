package springbook.connection;

import springbook.user.statement.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    
    private ConnectionManager connectionManager;
    
    public void setConnectionManager(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }
    
    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = this.connectionManager.getConnection();
            ps = stmt.makePreparedStatement(conn);
            ps.executeQuery();
        }catch (SQLException e){
            throw e;
        }finally {
            if(ps!=null){ try{ ps.close(); } catch (SQLException e){} }
            if(conn!=null){ try{ conn.close(); } catch (SQLException e){} }
        }
    }

    public void executeSql(final String query) throws SQLException{
        workWithStatementStrategy(
            new StatementStrategy(){
                @Override
                public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                    return conn.prepareStatement(query);
                }
            }
        );
    }
}
