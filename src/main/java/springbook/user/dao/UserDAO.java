package springbook.user.dao;

import springbook.connection.ConnectionManager;
import springbook.connection.JdbcContext;
import springbook.user.domain.UserVO;
import springbook.user.statement.StatementStrategy;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDAO {

    private final ConnectionManager connectionManager;
    private DataSource dataSource;
    private Connection conn;
    private UserVO user;
    private JdbcContext jdbcContext;

    public void setJdbcContext(JdbcContext jdbcContext){
        this.jdbcContext = jdbcContext;
    }

    public UserDAO(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    public void add(final UserVO user) throws SQLException {
        this.jdbcContext.workWithStatementStrategy( new StatementStrategy(){
                @Override
                public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO USERS2(ID, NAME, PASSWORD) VALUES(?,?,?)");
                    ps.setString(1,user.getId());
                    ps.setString(2,user.getName());
                    ps.setString(3,user.getPassword());
                    ps.executeUpdate();
                    return ps;
                }
            }
        );
    }

    public UserVO get(String id) throws SQLException {

        Connection conn = connectionManager.getConnection();

        this.user = new UserVO();
        PreparedStatement ps = conn.prepareStatement("select * from users2 where id = ?");
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        UserVO user = null;
        if (rs.next()) {
            user = new UserVO();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }
        rs.close();
        ps.close();
        conn.close();

        if(user==null){
            throw new SQLFeatureNotSupportedException();
        }

        return user;
    }

    public void deleteAll() throws SQLException {
        this.jdbcContext.executeSql("TRUNCATE TABLE USERS2");
    }

    public int getCount() throws SQLException {
        this.conn = connectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from users2");

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return count;
    }

    public void update(UserVO user){
        /* todo
        *  책을 다시 보던가 해야겠따..
        * */
        this.jdbcTemplate.update(
            "update users2 set name = ? , password = ? , level = ? , login = ? ,"
            + " recommend = ? , where id = ? ", user.getName(), user.getPassword(), user.getLevel().intValue()
            , user.getLogin(), user.getRecommend(), user.getId());

    }


    public List<UserVO> getAll() {

        /* todo
        *  구현
        * */

        return null;
    }
}
