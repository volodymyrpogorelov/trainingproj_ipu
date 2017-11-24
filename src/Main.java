import model.Abonent;
import model.DAO;
import model.User;

import java.sql.*;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost/trainingdb";

    //  Database credentials
    private static final String USER = "trainingdb";
    private static final String PASS = "12";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            /////////////////////// USER DAO //////////////////////////////////
            System.out.println("User DAO tests:");
            DAO<User> userDAO = new DAO<>(conn, User.class);
            System.out.println(userDAO.getObjects());
            User Tom = new User(0,"Tom", "12");
            userDAO.insertObject(Tom);
            System.out.println("///////////////// After Insertion //////////////");
            System.out.println(userDAO.getObjects());
            User tomas = new User(26,"Tomas", "13");
            userDAO.updateObject(tomas);
            System.out.println("///////////////// After Update //////////////");
            System.out.println(userDAO.getObjects());
            userDAO.deleteObject(tomas);
            System.out.println("///////////////// After Delete Object//////////////");
            System.out.println(userDAO.getObjects());
            System.out.println("///////////////// After DeleteRow //////////////");
            userDAO.deleteRow("Login", "Tom");
            System.out.println(userDAO.getObjects());

            //////////////////////////// Abonent Dao /////////////////////////////////
            System.out.println("Abonent DAO tests:");
            DAO<Abonent> abonentDAO = new DAO<>(conn, Abonent.class);
            System.out.println(abonentDAO.getObjects());
            Abonent abonent = new Abonent(0,1, "+38093412323", 45, 0);
            abonentDAO.insertObject(abonent);
            System.out.println("///////////////// After Insertion //////////////");
            System.out.println(abonentDAO.getObjects());
            System.out.println("///////////////// After DeleteRow //////////////");
            abonentDAO.deleteRow("uid", "1");
            System.out.println(abonentDAO.getObjects());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        System.out.println("It works!");

    }
}
