package springbook.user.service;

import springbook.user.domain.UserVO;

public interface UserService {
    void add(UserVO user);
    void upgradeLevels();
}
