package springbook.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.domain.UserVO;

public class UserServiceTx implements UserService {

    //여기엔 UserServiceImpl이 주입 되어야 한다.
    UserService userService;
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    public void add(UserVO user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
        }catch (RuntimeException e){
            this.transactionManager.rollback(status);
            throw e;
        }
        userService.upgradeLevels();
    }
}
