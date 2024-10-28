
import java.sql.*;
import java.util.*;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


public class Accounts {
        // getAcc - Populates HashMap /w Username : Password Format.
        // attemptLogin - Verifies if password & username are correct.
        // sendVerificationEmail - Verifies Email; emails link to a special page to user, page reads cookies and logs user in, or prompts user to log in if no cookies; switches the verification to true. Called if attempting to login with verification false.
        // createAccount - Adds new Account to database.
        // retrieveAccount - Sends an email with username to user.
        // verifyActivity - Turns true on login.
        // forgotPassword - Sends a reset password link!
        // logOut - Logs out, turns false for online activity.
        // getID - gets ID of user.
        // checkVerificationStatus - Checks verification status, returns true or false.
        // getEmail - Retrieves email from database.

        public static void retrieveUsername(String email) throws AddressException, MessagingException, IOException, InterruptedException, URISyntaxException{
            String[] recipient = { email };
            
            Email.sendUser(recipient, "Forgot Username", "The username assigned to this account is: " + getUsername(email));
        }

        public static String getUsername(String email) {
            String url = "jdbc:mysql://localhost/users";
            String username = "root";
            String password = "";
            HashMap<String, String> map = new HashMap<>();
    
            try {
    
            Class.forName("com.mysql.cj.jdbc.Driver"); 
     
    
            Connection connection = DriverManager.getConnection(url, username, password);
    
            Statement statement = connection.createStatement();
    
        ResultSet resultSet = statement.executeQuery("select * from user");
    
        
    
        while(resultSet.next()) {
            map.put(resultSet.getString(7), resultSet.getString(2));
        }
    
    
        
    
        connection.close();
       
    
        }
            catch(Exception e) {
                System.out.println(e);
            }
    
            return map.get(email);
        }

        public static void forgotPassword(String user) throws AddressException, MessagingException, IOException,InterruptedException, URISyntaxException {
            String[] recipient = { getEmail(user) };
            Email.sendUser(recipient, "Forgot Password", "Hello, please click this link to reset your password: https://www.TennysonLove.com");
        }
   
        public static boolean checkVerificationStatus(String user) {
            String url = "jdbc:mysql://localhost/users";
            String username = "root";
            String password = "";
            HashMap<String, Boolean> map = new HashMap<>();
    
            try {
    
            Class.forName("com.mysql.cj.jdbc.Driver"); 
     
    
            Connection connection = DriverManager.getConnection(url, username, password);
    
            Statement statement = connection.createStatement();
    
        ResultSet resultSet = statement.executeQuery("select * from user");
    
        
    
        while(resultSet.next()) {
            map.put(resultSet.getString(2), resultSet.getBoolean(6));
        }
    
    
        
    
        connection.close();
       
    
        }
            catch(Exception e) {
                System.out.println(e);
            }
    
            return map.get(user);
        }

        public static void sendVerificationEmail(String email) throws AddressException, MessagingException, IOException, InterruptedException, URISyntaxException {
            String[] recipient = { email };
            Email.sendUser(recipient, "Account Verification", "Hello, this is just to verifiy your accont. Please click here: https://www.TennysonLove.com");
        }

        public static String getEmail(String user) {
            String url = "jdbc:mysql://localhost/users";
            String username = "root";
            String password = "";
            HashMap<String, String> map = new HashMap<>();
        
            try {
        
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        
        
            Connection connection = DriverManager.getConnection(url, username, password);
        
            Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("select * from user");
        
        
        
        while(resultSet.next()) {
            map.put(resultSet.getString(2), resultSet.getString(7));
        }
        
        
        
        
        
        connection.close();
        
        
        }
            catch(Exception e) {
                System.out.println(e);
            }
        
            System.out.println(map.size());
        
        
            return map.get(user);
        }

public static int getID(String user) {
    String url = "jdbc:mysql://localhost/users";
    String username = "root";
    String password = "";
    HashMap<Integer, String> map = new HashMap<>();

    try {

    Class.forName("com.mysql.cj.jdbc.Driver"); 


    Connection connection = DriverManager.getConnection(url, username, password);

    Statement statement = connection.createStatement();

ResultSet resultSet = statement.executeQuery("select * from user");



while(resultSet.next()) {
    map.put(resultSet.getInt(1), resultSet.getString(2));
}





connection.close();


}
    catch(Exception e) {
        System.out.println(e);
    }

    System.out.println(map.size());

    for(int i = 1; i <= map.size(); i++) {
        if(map.get(i).equals(user)) {


            return i;
        } else {
        

            continue;
        }
    }

    return 0;
}

    public static HashMap<String, String> getAcc() {
        String url = "jdbc:mysql://localhost/users";
        String username = "root";
        String password = "";
        HashMap<String, String> map = new HashMap<>();

        try {

        Class.forName("com.mysql.cj.jdbc.Driver"); 
 

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery("select * from user");

    

    while(resultSet.next()) {
        map.put(resultSet.getString(2), resultSet.getString(3));
    }


    

    connection.close();
   

    }
        catch(Exception e) {
            System.out.println(e);
        }

        return map;
    }

    public static void verifyActivity(int id, boolean tf) {
        String url = "jdbc:mysql://localhost/users";
        String username = "root";
        String password = "";
        
        String sql = "UPDATE user SET onlinestatus = " + tf + " WHERE id = " + id;




        try {

        Class.forName("com.mysql.cj.jdbc.Driver"); 
 

        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();
if(id != 0) {
        statement.executeUpdate(sql); } else {
            System.out.println("Something went wrong...");
        }

        


    

    connection.close();
   

    }
        catch(Exception e) {
            System.out.println(e);
        }

      
    }

    public static void logOut(String user) {
        verifyActivity(getID(user), false);
    }
   

    public static boolean attemptLogin(String user, String pass) throws AddressException, MessagingException, IOException, InterruptedException, URISyntaxException {
        HashMap<String, String> map = getAcc();

        if(checkVerificationStatus(user)) {

        if(map.containsKey(user) && map.get(user).equals(pass)) {
            verifyActivity(getID(user), true);
            System.out.println("Login Successful");
            return true;
        } else {
            System.out.println("Login Failed.");
            return false;
        } } else {
            System.out.println("Check your email! Please verify your account!");
            sendVerificationEmail(getEmail(user));
        return false; }


    }

   

    public static void createAccount(String user, String pass, String repeatPass, String email) {
       
       String url = "jdbc:mysql://localhost/users";
       String username = "root";
       String password = "";
       int index = getAcc().size() + 1;

       try {

       Class.forName("com.mysql.cj.jdbc.Driver"); 


       Connection connection = DriverManager.getConnection(url, username, password);

if(!getAcc().containsKey(user) && pass.equals(repeatPass)) {
   PreparedStatement ps = connection.prepareStatement("INSERT INTO user (id, username, password, onlinestatus, logincounter, verified, email) VALUES (?, ?, ?, ? ,? ,? ,?)");
   ps.setInt(1, index);
   ps.setString(2, user);
   ps.setString(3, pass);
   ps.setBoolean(4, false);
   ps.setInt(5, 0);
   ps.setBoolean(6, false);
   ps.setString(7, email);


   ps.executeUpdate();
   connection.close();
   // Send Confirmation Email
   sendVerificationEmail(email);
   System.out.println("Account Created.");

} else if(!pass.equals(repeatPass)) {
    System.out.println("Password does not match!");
} else {
    System.out.println("Username Already Taken!");
}




  

   }
       catch(Exception e) {
           System.out.println(e);
       }

      
    }


}
