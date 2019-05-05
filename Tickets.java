package javaapplication1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Tickets extends JFrame implements ActionListener {

	// class level member objects
	Dao dao = null; // for CRUD operations
	
	Boolean chkIfAdmin = null;

	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;
	JMenuItem mnuItemCloseTicket;
	

	public Tickets(Boolean isAdmin) {

		chkIfAdmin = isAdmin;
		createMenu();
		prepareGUI();

	}

	private void createMenu() {

		/* Initialize sub menu items **************************************/

		// initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// add to File main menu item
		mnuFile.add(mnuItemExit);

		// initialize first sub menu items for Admin main menu
		mnuItemUpdate = new JMenuItem("Update Ticket");
		// add to Ticket main menu item
		mnuTickets.add(mnuItemUpdate);
		
		// initialize first sub menu items for Admin main menu
		mnuItemCloseTicket = new JMenuItem("Close Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemCloseTicket);

		// initialize second sub menu items for Admin main menu
		mnuItemDelete = new JMenuItem("Delete Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemDelete);

		// initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemOpenTicket);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);

		// initialize any more desired sub menu items below
		
	
		

		/* Add action listeners for each desired menu item *************/
		mnuItemExit.addActionListener(this);
		mnuItemUpdate.addActionListener(this);
		mnuItemDelete.addActionListener(this);
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		mnuItemCloseTicket.addActionListener(this);

		// add any more listeners for any additional sub menu items if desired

	}

	private void prepareGUI() {

		// create jmenu bar
		JMenuBar bar = new JMenuBar();
		//If the user is an admin, then he/she has access to all the featues.
		if(chkIfAdmin) {
			bar.add(mnuFile); // add main menu items in order, to JMenuBar
			bar.add(mnuAdmin);
			bar.add(mnuTickets);	
		}
		else { //is the user is a regular user, then he/she can only acess the open, update, and view ticket features.
			bar.add(mnuFile); // add main menu items in order, to JMenuBar
			bar.add(mnuTickets);

			
		// add menu bar components to frame
		}
		setJMenuBar(bar);

		addWindowListener(new WindowAdapter() {
		// define a window close operation
		public void windowClosing(WindowEvent wE) {
		    System.exit(0);
		}
		});
		// set frame options
		setSize(600, 400);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			dao = new Dao();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		//If exit is click, then the program closes.
		if (e.getSource()==mnuItemExit )
        {
			System.exit(0);
        }
		else if(e.getSource()==mnuItemCloseTicket) {
			//Will prompt the user for the ticket id and make sure again that the user wants to close it. Then print a message saying that the ticket is closed.
			
	    	String m = JOptionPane.showInputDialog("Enter the ticket id that you want to close?");
	    	String n = JOptionPane.showInputDialog("Are yor sure you want to close ticket " + m + "?\n Enter 1 for yes or 0 for no.");
	    	if(Integer.parseInt(n)==0) {
	        	JOptionPane.showMessageDialog(mnuItemCloseTicket, "Ticket " + m + " will not be Closed.", "Closed", NORMAL);
	    	}
	    	else {
	        	dao.closeTicket(Integer.parseInt(m));
	        	JOptionPane.showMessageDialog(mnuItemCloseTicket, "Ticket " + m + " has been closed.", "Closed", NORMAL);
	    	}   
		
			
		}
		else if (e.getSource()==mnuItemUpdate)
        {
			//Will prompt the user for the ticket id. Then prompt for a updated description. Then print a message saying the ticket is updated.
        	   String m = JOptionPane.showInputDialog("Enter the ticket id that you want to update.");
        	   String updates = JOptionPane.showInputDialog("Enter what you want to update.");
        	   dao.updateTicket(Integer.parseInt(m),updates);
        	   
        	   JOptionPane.showMessageDialog(mnuItemUpdate, "Ticket " + m + " has been Updated.", "Updated", NORMAL);

        }
		else if (e.getSource()==mnuItemDelete)
        {
			//Will prompt the user for the ticket id and make sure again that the user wants to delete it. Then print a message saying that the ticket is deleted.
			String m = JOptionPane.showInputDialog("Enter the ticket id that you want to delete?");
	    	String n = JOptionPane.showInputDialog("Are yor sure you want to close ticket " + m + "?\n Enter 1 for yes or 0 for no.");
			if(Integer.parseInt(n)==0) {
		    	JOptionPane.showMessageDialog(mnuItemDelete, "Ticket " + m + " will not be Deleted.", "Deleted", NORMAL);
		    }
			else {
		    	dao.deleteTicket(Integer.parseInt(m));
		        JOptionPane.showMessageDialog(mnuItemDelete, "Ticket " + m + " has been deleted.", "Deleted", NORMAL);
		    }
		       
	         
        }
		else if (e.getSource()==mnuItemOpenTicket)
        {
			//Created some fields for the user to type his/her name and his/her issue.
			setSize(800, 500);
			setLayout(new GridLayout(8, 2));
			setLocationRelativeTo(null); 
			JLabel lblTickIs = new JLabel("Ticket Issuer", JLabel.LEFT);
			JLabel lblTickTitle = new JLabel("Title", JLabel.LEFT);
			JLabel lblTickDesc = new JLabel("Description", JLabel.LEFT);
			JLabel lblDate = new JLabel("Date", JLabel.LEFT);
			JLabel lblStatus = new JLabel(" ", JLabel.CENTER);
			
			java.sql.Timestamp date1 = new java.sql.Timestamp(new java.util.Date().getTime());
			JTextField txtTickIs = new JTextField(50);
			JTextField txtTickTitle = new JTextField(10);
			JTextField txtTickDesc = new JTextField(1000);
			
			
			JTextField txtTickDate = new JTextField("Today: " + date1,10);
			JButton btn = new JButton("Submit");
			
			add(lblTickIs);
			add(txtTickIs);
			
			add(lblTickTitle);
			add(txtTickTitle);
			add(lblTickDesc); 
			add(txtTickDesc);
			add(lblDate); 
			add(txtTickDate);
			add(lblStatus);
			add(btn);
			add(lblStatus);
			
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int id = dao.insertTicketRec(txtTickIs.getText(),txtTickTitle.getText(),txtTickDesc.getText());
					
					//Create a ticket according to the text the user enetered. 
					JOptionPane.showMessageDialog(mnuItemOpenTicket, "Ticket number " + id + " has been open.", "Opened", JOptionPane.PLAIN_MESSAGE);
					//After the ticket is open, remove the fields and return to the original screen. 
					
					remove(lblTickIs);
					remove(txtTickIs);
					remove(lblTickTitle);// 1st row filler
					remove(txtTickTitle);
					remove(lblTickDesc); // 2nd row
					remove(txtTickDesc);
					remove(lblDate); 
					remove(txtTickDate);
					remove(lblStatus);
					remove(btn);
					remove(lblStatus);
					revalidate();
					repaint();
				}
			});
			
			setVisible(true); // SHOW THE FRAME
			
			
			
        }
		else if (e.getSource()==mnuItemViewTicket)
        {
			JFrame frame1;
			JTable table;
			String[] columnNames = {"Ticket ID","Ticket Issuer", "Ticket Title", "Ticket Description", "Status","Start Date","End Date"};

			//Create a table that presents all the ticekts. Another box will show up with each ticket taking one row.
			
			frame1 = new JFrame("Tickets");
		
			frame1.setLayout(new BorderLayout()); 
		
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(columnNames);
			
			table = new JTable();
			
			table.setModel(model); 
			//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			//table.setFillsViewportHeight(true);
			
			JScrollPane scroll = new JScrollPane(table);
			scroll.setHorizontalScrollBarPolicy(
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
			int tid;
			String tIssuer;
			String tTitle;
			String tDesc;
			String stat;
			Timestamp startD;
			Timestamp endD;
			
			ResultSet rs = dao.retrieveRecords();
			
			try {
				while(rs.next())
				{
					tid = rs.getInt("tid");
					tIssuer = rs.getString("ticket_issuer");
					tTitle = rs.getString("ticket_title");
					tDesc = rs.getString("ticket_desc");
					stat = rs.getString("status"); 
					startD = rs.getTimestamp("start_date");
					endD = rs.getTimestamp("end_date");
					model.addRow(new Object[]{tid, tIssuer, tTitle, tDesc, stat,startD,endD});
				}

				
				//rs.close(); // closes result set object
			}
			catch (SQLException se) { 
				se.printStackTrace(); 
			}
			
			frame1.add(scroll);
			frame1.setVisible(true);
			frame1.setSize(600,400);
			
		
        }
	}

}
