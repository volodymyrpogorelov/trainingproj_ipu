import model.DAO;
import model.User;

import java.sql.*;

public class Main {

    static final String DB_URL = "jdbc:mysql://localhost/trainingdb";

    //  Database credentials
    static final String USER = "trainingdb";
    static final String PASS = "12";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            DAO<User> userDAO = new DAO<User>(conn, User.class);
            System.out.println(userDAO.getObjects());
            User Tom = new User(0,"Tom", "12");
            userDAO.insertObject(Tom);
            System.out.println("///////////////// After Insertion //////////////");
            System.out.println(userDAO.getObjects());
            User tomas = new User(17,"Tomas", "13");
            userDAO.updateObject(tomas);
            System.out.println("///////////////// After Update //////////////");
            System.out.println(userDAO.getObjects());
            userDAO.deleteObject(tomas);
            System.out.println("///////////////// After Delete //////////////");
            System.out.println(userDAO.getObjects());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("It works!");
    }
}
