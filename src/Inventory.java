import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.Properties;


/**
 * The class reads the initial store inventory and list of discounts from separate text files.
 * The information is stored into an SQL server and can be modified later.
 */

//The inventory class should do all of the updating to the database


public class Inventory
{
    private List<Discount> discounts;
    private List<Product> products;

    private String dbms =    "mysql";
    private String serverName = "localhost"; //"127.0.0.1";
    private String portNumber = "3306";

    private String dbName = "Inventory";
    private String driver = "com.mysql.jdbc.Driver";

    private String dbUsername = "root"; // admin for sql connectToDatabase
    private String dbPassword = "";

    private Connection invConn;

    /**
     * Constructor use to build a list of products and discounts from .txt files.
     *
     * @param prices      File path to prices.txt
     * @param discounts   File path to discounts.txt
     */
    public Inventory(String prices, String discounts)
    {
        try
        {
            this.discounts = generateListOfDiscounts(discounts);
            this.products =  generateListOfProducts(prices);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Second constructor used to connect to the database
     */
    public Inventory()
    {
        try
        {
            this.invConn = getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException
    {
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Connection conn;
        Properties connectionProperties = new Properties();

        connectionProperties.put("user", dbUsername);
        connectionProperties.put("password", dbPassword);

        conn = DriverManager.getConnection("jdbc:" +
                        this.dbms + "://" +
                        this.serverName + ":" +
                        this.portNumber + "/" +
                        this.dbName,
                connectionProperties);

        System.out.println("Connection successful");
        return conn;
    }

    /**
     * Creates a list of discount objects from a text file.
     * Will only run if the SQL is empty
     *
     * @param arg                       Hardcoded file path of a txt file
     * @return                          A list of Discount objects
     * @throws FileNotFoundException    In case I get the file path wrong
     */
    private List<Discount> generateListOfDiscounts(String arg) throws FileNotFoundException
    {
        List<Discount> result = new ArrayList<>();
        try
        {
            Scanner sc = new Scanner(new File(arg));

            while (sc.hasNextLine())
            {
                //creates string array which stores discount information
                String[] discountParam;
                String currentLine = sc.nextLine();

                discountParam = currentLine.split(",");

                //validate barcode and number values and creates Discount object
                validateBarcode(discountParam[0]);
                for (int i = 1; i < discountParam.length; ++i)
                {
                    validateNumeric(discountParam[i]);
                }

                Discount currentDiscount = new Discount(
                        discountParam[0],
                        Integer.parseInt(discountParam[1]),
                        Double.parseDouble(discountParam[2]),
                        Double.parseDouble(discountParam[3]));

                //add discount to list
                result.add(currentDiscount);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Creates initial product list and populates SQL database with products
     *
     * @param arg                       Hardcoded file path which I should learn how to circumvent
     * @return                          List representation of product objects
     * @throws FileNotFoundException    In case I get the file path wrong
     */
    private List<Product> generateListOfProducts(String arg) throws FileNotFoundException
    {

        List<Product> result = new ArrayList<>();
        try
        {
            Scanner sc = new Scanner(new File(arg));

            while (sc.hasNext())
            {
                String[] productParam;
                String currentLine = sc.nextLine();

                productParam = currentLine.split(",");

                // validate barcode
                validateBarcode(productParam[0]);
                //validate numeric values
                for(int i = 3; i < productParam.length; ++i)
                {
                    validateNumeric(productParam[i]);
                }
                //ensure name and category fields are not null
                if(productParam[1] != null && productParam[2] != null)
                {
                    //create new product
                    Product currentProduct = new Product(
                            productParam[0],
                            productParam[1],
                            productParam[2],
                            //int parsing like a noob
                            Double.parseDouble(productParam[3]),
                            Double.parseDouble(productParam[4]));

                    // add product to list
                    result.add(currentProduct);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            //collapsed exception catch for SQL error and FileNotFound
            e.printStackTrace();
        }

        System.out.println(result.size() + " products created");
        return result;
    }

    //adds every product in the list to the database.
    //TO DO: think about inventory update sequences. How would you do it naturally? Then build it.
    private void constructProductDB(List<Product> prod) throws SQLException
    {
        for(Product p : prod)
        {
            addProdToDB(p);
        }
    }

    /**
     * Validates that barcode only contains numeric values & is not null.
     *
     * @param barcode                   String representation of a barcode
     * @throws IllegalFormatException   Informs user of barcode format error.
     */
    private static void validateBarcode(String barcode) throws IllegalFormatException
    {
        if(barcode.matches("[a-zA-Z]"))
        {
            throw new IllegalArgumentException("A barcode can only contain numeric values. Found: " + barcode);
        }
        if(barcode == null)
        {
            throw new NullPointerException("Barcode cannot be null");
        }
    }

    /**
     * Checks if there are non numeric characters in a string.
     *
     * A product's price and assigned discount and limit are to be stored as positive doubles. Any other format
     * is illegal.
     */
    private static void validateNumeric(String numeric) throws IllegalFormatException
    {
        //if string representation of the double contains any alphabetic character
        //then throw exception
        if(numeric.matches("[\\p{Lower}\\p{Upper}]"))
        {
            throw new IllegalArgumentException("Price or limit values can only be numeric. Found: " + numeric);
        }
        if(numeric.equals(""))
        {
            throw new IllegalArgumentException("Price or limit values cannot be empty.");
        }
    }

    private User createUser(String firstName, String lastName, String username, String password)
    {
        User newUser = new User(firstName, lastName, username, password);
        System.out.println("\nUser created");

        return newUser;
    }

    private void addProdToDB(Product product) throws SQLException
    {
        try
        {
            invConn.setAutoCommit(false);

            PreparedStatement pst = invConn.prepareStatement("INSERT INTO Products(?, ?, ?, ?, ?)");
            pst.setString(1, product.getBarcode());
            pst.setString(2, product.getCategory());
            pst.setString(3, product.getName());
            pst.setDouble(4, product.getKr());
            pst.setDouble(5, product.getOre());

            pst.execute();
            System.out.println(product.toString() + " added to database");

        } catch (SQLException e) {
            e.printStackTrace();

            invConn.rollback();
        } finally {
            invConn.setAutoCommit(true);
        }
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public List<Discount> getDiscounts()
    {
        return discounts;
    }

    public void killConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("The connection you asked to be closed is severed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
