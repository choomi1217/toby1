package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.connection.AConnection;
import springbook.connection.ConnectionManager;
import springbook.connection.CountingConnectionManager;

@Configuration
public class DaoFactory {

    @Bean
    public ConnectionManager connectionManager(){
        return new AConnection();
    }
    @Bean
    public ConnectionManager realConnectionManager(){
        return new AConnection();
    }

    @Bean
    public UserDAO userDAO(){
        ConnectionManager conn = connectionManager();
        UserDAO userDAO = new UserDAO(conn);
        return userDAO;
    }
}