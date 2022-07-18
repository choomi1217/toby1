package springbook.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionManager implements ConnectionManager{

    int count = 0;
    private ConnectionManager realConnectionManager;

    public CountingConnectionManager(ConnectionManager realConnectionManager){
        this.realConnectionManager = realConnectionManager;
    }

    @Override
    public Connection getConnection() throws SQLException {
        this.count++;
        return realConnectionManager.getConnection();
    }

    public int getCount(){
        return this.count;
    }

}
