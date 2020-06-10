package edu.escuelaing.arsw.app;

import java.io.*;
import java.net.*;

/**
 *
 * @author ceseg
 */
public class BrowserPrototype {
	public static void main(String[] args) throws Exception {
		URL google = new URL("http://www.google.com/");
		try (BufferedReader reader= new BufferedReader(new InputStreamReader(google.openStream()))) {
			BufferedWriter bw = new BufferedWriter(new FileWriter("resultado.html"));
			String line;
		    while ((line = reader.readLine()) != null) {
		    	bw.write(line);
		    	bw.newLine();
		    	System.out.println(line);
		    }
		    reader.close();
		    bw.close();
		} catch (IOException x) {
			System.err.println(x);
		}
	}
}