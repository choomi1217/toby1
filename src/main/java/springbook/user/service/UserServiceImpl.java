package springbook.user.service;

import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import springbook.user.dao.UserDAO;
import springbook.user.domain.Level;
import springbook.user.domain.UserVO;

import javax.sql.DataSource;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDAO userDao;

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDAO userDao){
        this.userDao = userDao;
    }


    public void upgradeLevel(UserVO user){
        if(user.getLevel() == Level.BASIC){
            user.setLevel(Level.SILVER);
        }else if(user.getLevel() == Level.SILVER){
            user.setLevel(Level.GOLD);
        }
        userDao.update(user);

    }

    public void upgradeLevels(){
        List<UserVO> users = userDao.getAll();
        for(UserVO user : users){
            if(canUpgradeLevel(user)){
                upgradeLevel(user);
            }
        }
    }

    private boolean canUpgradeLevel(UserVO user){
        Level currentLevel = user.getLevel();
        switch (currentLevel){
            case BASIC: return (user.getLogin() >= 50);
            case SILVER: return (user.getRecommend() >= 30);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level : " + currentLevel);
        }
    }

    public void add(UserVO user){
        if(user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }



}
