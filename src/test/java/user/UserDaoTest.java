package user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDAO;
import springbook.user.domain.Level;
import springbook.user.domain.UserVO;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    private UserDAO dao;
    private UserVO user1;
    private UserVO user2;
    private UserVO user3;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    @Test
    public void should_SuccessToAdd_When_AddUser(){
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = context.getBean("userDAO", UserDAO.class);

        this.user1 = new UserVO("omi","앵미","486",Level.BASIC,1,0);
        this.user2 = new UserVO("kureung","쿠릉","333",Level.SILVER,55,10);
        this.user3 = new UserVO("gunkim","거니","777",Level.GOLD,100,40);
    }

    @Test
    public void should_SuccessToAddAndGet_When_AddAndGetUser() throws SQLException{

        dao.deleteAll();
        Assertions.assertEquals(dao.getCount(),0);

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        Assertions.assertEquals(dao.getCount(),3);

        UserVO getUser1 = dao.get(user1.getId());
        UserVO getUser2 = dao.get(user2.getId());
        UserVO getUser3 = dao.get(user3.getId());
        checkSameUser(user1,getUser1);
        checkSameUser(user2,getUser2);
        checkSameUser(user3,getUser3);
    }

    @Test
    public void should_SuccessToCount_When_UserCount() throws SQLException {

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDAO dao = context.getBean("userDAO", UserDAO.class);

        this.user1 = new UserVO("omi","앵미","486",Level.BASIC,1,0);
        this.user2 = new UserVO("kureung","쿠릉","333",Level.SILVER,55,10);
        this.user3 = new UserVO("gunkim","거니","777",Level.GOLD,100,40);

        dao.deleteAll();
        Assertions.assertEquals(dao.getCount(),0);
        dao.add(user1);
        Assertions.assertEquals(dao.getCount(),1);
        dao.add(user2);
        Assertions.assertEquals(dao.getCount(),2);
        dao.add(user3);
        Assertions.assertEquals(dao.getCount(),3);
    }

    @Test
    public void should_FailToGetUser_When_IdNotMatched() throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDAO dao = context.getBean("userDAO", UserDAO.class);

        dao.deleteAll();
        Assertions.assertEquals(dao.getCount(),0);

        dao.get("omi");
        Exception thrown = Assertions.assertThrows(SQLFeatureNotSupportedException.class,()-> System.out.println("오~ 에러네"));

    }

    @Test
    void update() throws SQLException {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("조앵맹");
        user1.setPassword("1217");
        user1.setLevel(Level.GOLD);
        user1.setLogin(100);
        user1.setRecommend(999);

        dao.update(user1);

        UserVO user1Update = dao.get(user1.getId());
        checkSameUser(user1, user1Update);
        UserVO user2NotUpdate = dao.get(user2.getId());
        checkSameUser(user2, user2NotUpdate);
    }

    private void checkSameUser(UserVO user1, UserVO user2){
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
    }
}
