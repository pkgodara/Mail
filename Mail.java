/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/*
adding mail.jar =>javamail
*/
import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;


/**
 *
 * @author pradeep
 */
public class Mail {
    
    
    
    public static void main(String[] args) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        
        String host = "pop.gmail.com" ;
        String mailStore = "pop3";
        
        System.out.println("Host : "+host);
        System.out.print("Enter username : ");
        
        String username = sc.next();
        
        System.out.print("Enter password : ");
        String password = sc.next() ;
        
        check(host,mailStore,username,password);
        
    }
    
    
    private static void check(String host,String storeType , String user , String pass) throws IOException
    {
        try
        {
            //create properties field
            Properties props = new Properties();
            
            props.put("mail.pop3.host", host);
            props.put("mail.pop3.port", "995");
            props.put("mail.pop3.starttls.enable", "true") ;
            
            //Session emailSession = Session.getDefaultInstance(props); //this gives erroe: javax.mail.AuthenticationFailedException: [AUTH] Web login required: https://support.google.com/mail/answer/78754
            
            Session emailSession = Session.getInstance(props , new javax.mail.Authenticator()
            {
                protected PasswordAuthentication getPassAuth()
                {
                    return new PasswordAuthentication(user,pass);
                }
            }) ;
            
            //create pop3 store object and connect with pop server
            Store store = emailSession.getStore("pop3s");
            
            store.connect(host,user,pass);
            
            //create folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            
            //retrieve messages from folder in array and print it
            Message[] msg = emailFolder.getMessages() ;
            
            System.out.println("Total Messages : "+ msg.length);
            
            for(int i=0 , n = msg.length ; i < n ; i++ )
            {
                Message m = msg[i] ;
                
                System.out.println("--------------------------------------------");
                
                System.out.println("Email no : "+ (i+1) );
                
                System.out.println("Subject : "+ m.getSubject());
                System.out.println("From : "+ m.getFrom());
                System.out.println("Message : "+m.getContent().toString());
            }
            
            //close folder and store objects
            emailFolder.close(false);
            store.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
