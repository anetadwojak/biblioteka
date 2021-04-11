package jdbc;



import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/biblioteka?serverTimezone=UTC";
        String user = "hbstudent";
        String pass = "hbstudent";

        try{
            System.out.println("Połączono z bazą: " + jdbcUrl);

            Connection myConn = (Connection) DriverManager.getConnection(jdbcUrl, user, pass);

            System.out.println("Połączono!!");

        }catch (Exception exc ){
            exc.printStackTrace();
        }
    }
}
