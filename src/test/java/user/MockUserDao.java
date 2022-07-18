package user;

import springbook.user.dao.UserDAO;
import springbook.user.domain.UserVO;

import java.util.ArrayList;
import java.util.List;

class MockUserDao implements UserDAO {
    private List<UserVO> users;
    private List<UserVO> update = new ArrayList<UserVO>();

    MockUserDao(List<UserVO> users){
        this.users = users;
    }

    public List<UserVO> getUpdate(){
        return this.update;
    }

    public List<UserVO> getAll(){
        return this.users;
    }

    public void update(UserVO user){
        update.add(user);
    }

    public void add(UserVO user){ throw new UnsupportedOperationException();}
    public void deleteAll(UserVO user){ throw new UnsupportedOperationException();}
    public void get(UserVO user){ throw new UnsupportedOperationException();}
    public void getCount(UserVO user){ throw new UnsupportedOperationException();}

}
