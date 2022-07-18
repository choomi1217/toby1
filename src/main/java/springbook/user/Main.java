package springbook.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDAO;
import springbook.user.domain.UserVO;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        UserDAO dao = new DaoFactory().userDAO();
        UserVO user = new UserVO();

        user.setId("testID1");
        user.setName("테스트1");
        user.setPassword("testPWD1");
        dao.add(user);

        UserVO user2 = dao.get("testID1");
        System.out.println("ID : " + user2.getId());
        System.out.println("NAME : " + user2.getName());
        System.out.println("PASSWORD : " + user2.getPassword());

    }
}
