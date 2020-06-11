
package edu.escuelaing.arsw.app;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class HttpServer {
	public static void main(String[] args) throws IOException { 
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(35000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}
		
		while(true) {
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine, element = null;
			while ((inputLine = in.readLine()) != null) {
				if(inputLine.matches("(GET)+.*")) element = inputLine.split(" ")[1];
				if (!in.ready()) break;
			}
			if(element.matches(".*(.html)")) {
				StringBuffer sb = new StringBuffer();
				System.out.println(element);
				try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+element))) {
					String infile = null;
					while ((infile = reader.readLine()) != null) {
						sb.append(infile);
					}
				}
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: text/html");
				out.println();
				out.println(sb.toString());
			}
			else if(element.matches(".*(.png)")){
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type: image/png");
				out.println();
		        BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir")+element));
		        ImageIO.write(image, "PNG", clientSocket.getOutputStream());
		        
			}

			out.close();
			in.close();
		}
	}
}