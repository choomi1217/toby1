package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDAO;
import springbook.user.domain.Level;
import springbook.user.domain.UserVO;
import springbook.user.service.UserServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    DataSource dataSource;
    @Autowired
    PlatformTransactionManager transactionManager;

    static UserDAO dao;

    static List<UserVO> users;

    @BeforeEach
    void beforeAll() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.dao = context.getBean("userDAO", UserDAO.class);

        users = Arrays.asList(
                new UserVO("omi","앵미","486", Level.BASIC,1,0),
                new UserVO("ggamji","깜지","7777",Level.BASIC,10000,4000000),
                new UserVO("kureung","쿠릉","333",Level.SILVER,55,10),
                new UserVO("gunkim","거니","777",Level.SILVER,100,40),
                new UserVO("tobi","토비","999",Level.GOLD,90,90)
        );
    }

    @Test
    void bean() {
        assertThat(this.userService).isNotNull();
    }

    @Test
    void upgradeLevels() throws Exception{
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        MockUserDao mockUserDao = new MockUserDao(users);
        userServiceImpl.setUserDao(mockUserDao);

        List<UserVO> updated = mockUserDao.getUpdate();
        assertThat(updated.size()).isEqualTo(3);
        checkUserAndLevel(updated.get(0),"oomi",Level.BASIC);
        checkUserAndLevel(updated.get(1),"kureung",Level.SILVER);
        checkUserAndLevel(updated.get(1),"gunkim",Level.GOLD);
    }

    protected void upgradeLevel(UserVO user){
        user.upgradeLevel();
        dao.update(user);
    }

    private void checkUserAndLevel(UserVO updated, String expectUserId, Level expectLevel){
        assertThat(updated.getId()).isEqualTo(expectUserId);
        assertThat(updated.getLevel()).isEqualTo(expectLevel);
    }

    private void checkLevel(UserVO user, Level expectLevel) throws SQLException {
        UserVO userUpdate = dao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectLevel);
    }

    @Test
    void add() throws SQLException {
        dao.deleteAll();

        UserVO userWithLevel = users.get(4); //GOLD
        UserVO userWithoutLevel = users.get(0); //앵미
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        UserVO userWithLevelRead = dao.get(userWithLevel.getId());
        UserVO userWithoutLevelRead = dao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.GOLD);

    }

    @Test
    void upgradeAllOrNothing() throws Exception {
        /* todo
         * TestUserService 구현
         * */
        UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.dao);
        testUserService.setTransactionManager(transactionManager);
        testUserService.setDataSource(this.dataSource);
    }

    @Test
    void mockUpgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //import static org.mockito.Mockito.mock;
        UserDAO mockUserDao = mock(UserDAO.class);
        when(mockUserDao.getAll()).thenReturn(users);
        userServiceImpl.setUserDao(mockUserDao);

        userServiceImpl.upgradeLevels();
        verify(mockUserDao, times(3)).update(any(UserVO.class));
        verify(mockUserDao, times(3)).update(any(UserVO.class));
        verify(mockUserDao).update(users.get(1));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.SILVER);
        verify(mockUserDao).update(users.get(3));
        assertThat(users.get(1).getLevel()).isEqualTo(Level.GOLD);
    }
}
