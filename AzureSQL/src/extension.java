import java.sql.Connection;

import java.sql.Statement;

import java.util.Scanner;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

public class extension {
	// Database credentials    
	final static String HOSTNAME = "sanb4019-sql-server.database.windows.net";

	final static String DBNAME = "cs-dsa-4513-sql-db";

	final static String USERNAME = "sanb4019";

	final static String PASSWORD = "5VEXue6kJzUN7jZ";

	// Database connection string    
	final static String URL = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
			,HOSTNAME, DBNAME, USERNAME, PASSWORD);

	// Query templates
	//Template 1 executes sp_1 Transact-SQL procedure for the first insertion case
	final static String QUERY_TEMPLATE_1 = "EXEC sp_1 @pid = ?, @name = ?, @age = ?;";
	//Template 2 executes sp_2 Transact-SQL procedure for the second insertion case
    final static String QUERY_TEMPLATE_2 = "EXEC sp_2 @pid = ?, @name = ?, @age = ?, @did = ?;";
    final static String QUERY_TEMPLATE_3 = "SELECT * FROM Performer;";
    // User input prompt//    
    final static String PROMPT = "\nPlease select one of the options below: \n" + "1) Insert new Performer \n" + "2) Insert new Performer (with did) \n" + "3) Display all perfomers \n" + "4) Exit!";
    
    public static void main(String[] args) throws SQLException {        
    	System.out.println("Welcome to the Group 15 HW3 application!");
        final Scanner sc = new Scanner(System.in);
        // Scanner is used to collect the user input        
        String option = "";
        // Initialize user option selection as nothing        
        while (!option.equals("4")) { 
        	// As user for options until option 4 is selected
        	System.out.println(PROMPT);
        	// Print the available options            
        	option = sc.next();
        	// Read in the user option selection            
        		switch (option) { 
        		// Switch between different options                
        		case "1": 
        			// Insert a new performer option 1                   
        			// Collect the new performer data from the user
        			// Read in the user input of performer ID 
        			System.out.println("Please enter integer performer ID:");
                    int pid = sc.nextInt();
                    // Read in user input of performer name                  
                    System.out.println("Please enter performer name:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.                    
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.             
                    sc.nextLine();
                    String name = sc.nextLine();
                    // Read in user input of performer age
                    System.out.println("Please enter integer age:");
                    int age = sc.nextInt();                   
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {                        
                    	try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) {                            
                    		// Populate the query template with the data collected from the user                            
                    		statement.setInt(1, pid);
                            statement.setString(2, name);
                            statement.setInt(3, age);
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query                            
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }                    
                    }                    
                    break;
                case "2":                    
        			// Insert a new performer option 2                  
        			// Collect the new performer data from the user
        			// Read in the user input of performer ID 
        			System.out.println("Please enter integer performer ID:");
                    pid = sc.nextInt();
                    // Read in user input of performer name                  
                    System.out.println("Please enter performer name:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.                    
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.             
                    sc.nextLine();
                    name = sc.nextLine();
                    // Read in user input of performer age
                    System.out.println("Please enter integer age:");
                    age = sc.nextInt();      
                    // Read in user input of director ID
                    System.out.println("Please enter integer director ID:");
                    int did = sc.nextInt();                   
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {                        
                    	try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_2)) {                            
                    		// Populate the query template with the data collected from the user                            
                    		statement.setInt(1, pid);
                            statement.setString(2, name);
                            statement.setInt(3, age);
                            statement.setInt(4, did);
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query                            
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }                    
                    }                    
                    break;
                case "3":                    
                	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute itright away, as no user input need be collected                    
                	try (final Connection connection = DriverManager.getConnection(URL)) {                        
                		System.out.println("Dispatching the query...");
                        try (
                            final Statement statement = connection.createStatement();
                            final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_3)) {                                
                        	System.out.println("Contents of the Performer table:");
                            System.out.println("ID | name | years of experience | age ");
                            // Unpack the tuples returned by the database and print them out to the user                                
                            while (resultSet.next()) {                                    
                            System.out.println(String.format("%s | %s | %s | %s",resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
                            }
                        }                        
                    }                                        
        		break;
                case "4": 
                	// Do nothing, the while loop will terminate upon the next iteration                    
                	System.out.println("Exiting! Good-buy!");
                    break;
                default: // Unrecognized option, re-prompt the user for the correctone                    
                	System.out.println(String.format("Unrecognized option: %s\n" + "Please try again!",option));
                    break;
            }        
        }        
        sc.close();
 // Close the scanner before exiting the application    
    }
}