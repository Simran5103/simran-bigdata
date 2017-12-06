
package javaapplication6;

// Libraries 
import java.sql.*;
import javax.swing.*; // * means all libraries included under swing
import java.awt.*;
import java.awt.event.*;
/// Splash screen ka lia

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
///

public class JavaApplication6 extends JWindow{
        
    
        // 
        // For splash Screen
        Image img=Toolkit.getDefaultToolkit().getImage("cu.jpg"); //  Storing image in img variable

        ImageIcon imgicon=new ImageIcon(img);   // Making new instant of image using ImageIcon class

        public JavaApplication6() // Constructor function of the class
        {
        try
        {
        setSize(633,300);   // Image size 
        setLocationRelativeTo(null); // Main window pops in center after splash screen due to this line
        Thread.sleep(5000);   // 5000 ms splash screen time
        dispose();  // Clear screen
        }
        catch(Exception exception)  
        {
                   javax.swing.JOptionPane.showMessageDialog((java.awt.Component)
                       null,"Error"+exception.getMessage(), "Error:",
                       javax.swing.JOptionPane.DEFAULT_OPTION);
        }
        }

        public void paint(Graphics g)
        {
        g.drawImage(img,0,0,this);  // Draws the bitmap /Image
        }

        public static void main(String[]args)
        {
        JavaApplication6 sp=new JavaApplication6();
        Gui gui = new Gui();
        }

  }

    

class Gui extends JFrame implements ActionListener
{   
    // JFrame is where full application is present.
    // Inside of a frame there is a panel where all elements are present  
    JTextField t1; // Search box
    JButton bs; // Search button
    JPanel p1; // Main panel
    JTextPane textPane1 = new JTextPane();  // TextPane where search results are shown 
    JScrollPane jsp = new JScrollPane(textPane1); // Providing a scroll to TextPane (adding textpane in panel)
    
    
    
    public Gui()          // Constructor function of a class(same name as that of the class)
    {
        textPane1.setText("                              Results will be displayed here                              \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        // Initial Text pane text
        textPane1.setEditable(false);  // Dont let user edit textPane  
        p1 = new JPanel();             // Defining new Panel            
        t1 = new JTextField(20);       // Defining new TextField Width of textfield is 20
        bs = new JButton("Search");    // Defining new Button
        JLabel l = new JLabel("Made by roll nos : \n\n 14BCS5090 | 14BCS5101 | 14BCS5103 ");

        ///////////////////////////////////////////////////////////
        p1.setLayout(new FlowLayout());     // Type of layout of panel
        p1.add(t1);                         // adding textfield in panel
        p1.add(bs);                         // adding button in panel
        p1.add(jsp);                        // adding scrollpane in textpane
        p1.add(l);
        add(p1, BorderLayout.CENTER);       // adding panel in frame
        bs.addActionListener(this);         // button listener event
        
                //// Frame Properties
        setVisible(true);
        setSize(450,400);            // Setting size of frame
        setLocationRelativeTo(null); // To align to the center location after the splash screen
        setResizable(false);         // Not resizable frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when click the cross 
        setTitle("Chandigarh University Search Engine"); // setting title of the frame
        ///
         }

    @Override
    public void actionPerformed(ActionEvent ae) {   // Button listener event

     try {
        //  Database credentials
           Connection c ;
        //STEP 2: Register JDBC driver
           Class.forName("org.sqlite.JDBC");  // Driver for connecting sqllite to java
        
        //STEP 3: Open a connection
           c = DriverManager.getConnection("jdbc:sqlite:test.sqlite");   // sqllite file name
           Statement stmt = (Statement) c.createStatement();
            // Picking the searched text from textbox
            String name = t1.getText();
            // SQL query statement for searching the database
            // eg: SELECT * FROM tableName WHERE columnName LIKE input
            String SQL = "SELECT * FROM Track WHERE NAME LIKE '" + name + "%'";
            // Empty string where result is being stored
            String final1 = ""; 
            // Database connection where query is stored
            ResultSet rs = stmt.executeQuery(SQL);   // Create a query list and stored in rs // Executing query stored in SQL variable
          
            int a = 1;
            // Looping through all the queries and storing in string
            while(rs.next()){
                // rs.next() checks if there is any data result from query rs
                a = 0;
                final1 += rs.getString("Name") + "\n";   // Get query of column name Name
            }
            if (name.isEmpty())  // Check if name string is empty
            {
                // If the input field of search is empty execute this
                textPane1.setText("Type something to search");
            }
            else{ // Nested loop
            if (a == 0)
            { // Entered once in the loop means result found
            textPane1.setText("Result:\n\n" + final1);
            }
            else{
                // Means no result is found as no query is there 
            textPane1.setText("No Result found.Search again!");    
            }}
            c.close();
        } 
        catch ( ClassNotFoundException | SQLException e ) {
          // Catches all database exceptions such as database not found
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
}
