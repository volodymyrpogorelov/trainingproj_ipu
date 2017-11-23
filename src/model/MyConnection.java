package model;

import java.sql.Connection;

public class MyConnection {
    private MyConnection myConnection;
    private Connection connection;

    private MyConnection() {

    }

    public MyConnection getInstance() {
        return new MyConnection();
    }


}
