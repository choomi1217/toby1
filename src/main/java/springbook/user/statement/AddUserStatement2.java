package springbook.user.statement;

import springbook.user.domain.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserStatement2 implements StatementStrategy{

    UserVO user;

    public AddUserStatement2(UserVO user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO USER2(ID, NAME, PASSWORD) VALUES(?,?,?)");
        ps.setString(1,user.getId());
        ps.setString(2,user.getName());
        ps.setString(3,user.getPassword());

        ps.executeUpdate();

        return ps;
    }
}
