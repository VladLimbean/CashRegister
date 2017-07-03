import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main class. Establishes connection with the database. Also allows to update inventory database
 */
public class CashRegister {


    public static void main(String[] args)
    {
        System.out.println("Welcome to Cash Register v2.0");

        Inventory inv = new Inventory();
        try
        {
            Connection conn = inv.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("\nConnection error");
        }
        

    }
}
