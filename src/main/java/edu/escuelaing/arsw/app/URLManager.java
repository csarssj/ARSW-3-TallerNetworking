package edu.escuelaing.arsw.app;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ceseg
 */
public class URLManager {
     public static void main(String[] args) throws Exception {
        try { 
            URL aURL = new URL("http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING");

            System.out.println("protocol = " + aURL.getProtocol()); 
            System.out.println("authority = " + aURL.getAuthority()); 
            System.out.println("host = " + aURL.getHost()); 
            System.out.println("port = " + aURL.getPort()); 
            System.out.println("path = " + aURL.getPath()); 
            System.out.println("query = " + aURL.getQuery()); 
            System.out.println("filename = " + aURL.getFile());
            System.out.println("ref = " + aURL.getRef()); 
            
        }catch(MalformedURLException ex){
            Logger.getLogger(URLManager.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
}
