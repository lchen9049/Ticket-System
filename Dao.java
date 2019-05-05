package javaapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {

	// Code database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "fp411", PASS = "411";
	
	private static final ResultSet Null = null;
	
	Connection conn = null;
	Statement stmt = null;

	public Dao() throws SQLException, ClassNotFoundException { //create db object instance
		System.out.println("Connecting to a selected database...");
		conn = connect();
		System.out.println("Connected database successfully...");
		createTicketTable();
		createUsersTable();
		//insertUserRec();
	}
	
	public Connection connect() throws SQLException {

		return DriverManager.getConnection(DB_URL, USER, PASS);

	}
	
	// CREATE TABLE METHOD
		/**
		 * createTable() prints out that we are creating a table in the given database while doing so. 
		 * It will print an error message if fail to create table.
		 */
	public void createTicketTable() {
		try {
		
			
			// Execute create query
			System.out.println("Creating Ticket table in given database...");

			stmt = conn.createStatement();

			String sql = "CREATE TABLE lchen_finalTicketSystem " + "(tid INTEGER not NULL AUTO_INCREMENT, " + " ticket_issuer VARCHAR(50), "+ " ticket_title VARCHAR(100), " + " ticket_desc TEXT, "+ " status VARCHAR(10), " + " start_date DATETIME, " +  " end_date DATETIME, " + " PRIMARY KEY ( tid ))";

			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");
			conn.close(); //close db connection 
			} 
		catch (SQLException se) {
			// Handle errors for JDBC
			//se.printStackTrace();
		}
	}
	
	/**
	 * createUsersTable() creates the table for user credentials with the username and user password
	 */
	public void createUsersTable() {
		try {

			// Execute create query
			System.out.println("Creating User table in given database...");

			stmt = conn.createStatement();

			String sql = "CREATE TABLE lchen_users " + "(pid INTEGER not NULL AUTO_INCREMENT, " + " uname VARCHAR(10), "  +  " upass VARCHAR(100), " + " admin BOOLEAN, "+ " PRIMARY KEY ( pid ))";

			stmt.executeUpdate(sql);
			
			
			System.out.println("Created table in given database...");
			conn.close(); //close db connection 
			} 
		catch (SQLException se) {
			// Handle errors for JDBC
			//se.printStackTrace();
		}
	}
	

	public void insertUserRec() {
		
		try {

			stmt = conn.createStatement();
			
			String sql = "INSERT INTO lchen_users(uname,upass,admin) " + "VALUES ('admin', '411Project', '1')";
			stmt.executeUpdate(sql);

			
			sql = "INSERT INTO lchen_users(uname,upass,admin) " + "VALUES ('user1', 'userpass1', '0')";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO lchen_users(uname,upass,admin) " + "VALUES ('user2', 'userpass2', '0')";
			stmt.executeUpdate(sql);

			conn.close(); //close db connection 
			} 
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * insertTicketRec() takes in the fields for the ticket and creates a ticket and return the ticket id to the user. 
	 * @param name the name of the ticker issuer
	 * @param title the title of the ticket
	 * @param desc  the description of the ticket
	 * @return id the primary key id of the ticket
	 */
	public int insertTicketRec(String name,String title, String desc) {
		int id = 0;
		try {
			stmt = conn.createStatement();
			
			java.sql.Timestamp date1 = new java.sql.Timestamp(new java.util.Date().getTime());
					
			
			
			String sql = "INSERT INTO lchen_finalTicketSystem(ticket_issuer,ticket_title,ticket_desc,status,start_date) " + "VALUES ('"+name+"','"+title+"', '"+desc+"','open','"+date1+"')";
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }


			//conn.close(); //close db connection 
			} 
		catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}
		return id;
		
		
	}
	
	
	/**
	 * 
	 * retriveRecords() retrives all the ticket from the database and puts it into the ResultSet.
	 * @return rs the ResultSet that contain the tickets retrieved
	 */
	public ResultSet retrieveRecords() {
		try {
			System.out.println("Connecting to a selected database for Record retrievals...");
			ResultSet rs;
			
			
			
			stmt = conn.createStatement();
			String sql = "SELECT tid, ticket_issuer,ticket_title, ticket_desc, status, start_date, end_date FROM lchen_finalTicketSystem";
			rs = stmt.executeQuery(sql);
			return rs;
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		return Null;
	}
	
	/**
	 * deleteTicket() takes in the id of the ticket and deleted the ticket from the database.
	 * @param id the id of the ticket that the user wants to delete
	 */
	public void deleteTicket(int id) {

		
		
		try {
			PreparedStatement preparedStatement = null;
			String sql = "DELETE FROM lchen_finalTicketSystem " + "WHERE tid = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	/**
	 * closeTicket() takes in the id of the ticket and changes the ticket's status from open to close and adds a end date to signify the time stamp of when the ticket is closed.
	 * @param id the id of the ticket that the user wants to close
	 */
	public void closeTicket(int id) {
		
		java.sql.Timestamp date2 = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			PreparedStatement preparedStatement = null;
			String sql = "UPDATE lchen_finalTicketSystem " + "SET status = ?, end_date = ? WHERE tid = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "close");
			preparedStatement.setTimestamp(2, date2);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		
	}
	
	/**
	 * updateTicket takes the id of the ticket and a String that holds what the user wants to update. A timestamp will be added to ach update.
	 * @param id the id of the ticket that the user wants to update
	 * @param updates the things that the user wants to update to the ticket_desc
	 */
	public void updateTicket(int id, String updates) {
		
		java.sql.Timestamp date2 = new java.sql.Timestamp(new java.util.Date().getTime());
		
		try {
			stmt = conn.createStatement();
		
			
			
			String sql = "SELECT ticket_desc FROM lchen_finalTicketSystem WHERE tid = '"+id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			String oldDesc = "";
			
			if(rs.next()) {
				oldDesc = rs.getString(1);
			}
			
			String newDesc = oldDesc + " \n\n" + updates + "\n" + "Updates as of " + date2;
			
			PreparedStatement preparedStatement = null;
			sql = "UPDATE lchen_finalTicketSystem " + "SET ticket_desc = ? WHERE tid = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, newDesc);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();

		    
		}
		catch (SQLException se) {
			se.printStackTrace();
		}

		
	}
	



	
	
	
}

