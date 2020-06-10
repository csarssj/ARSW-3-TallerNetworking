package edu.escuelaing.arsw.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FunctionServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(3000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 3000.");
			System.exit(1);
		}
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						clientSocket.getInputStream()));
		String inputLine, outputLine;
		String function = "fun:cos";
		while ((inputLine = in.readLine()) != null) {
			try {
				if(inputLine.contains("fun:sin")|| inputLine.contains("fun:tan") || inputLine.contains("fun:cos")) {
					function = inputLine;
					outputLine = "Ha cambiado para calcular : " +inputLine;
				}
				else{
					outputLine = "Respuesta: " +function(function,inputLine);
				}
				out.println(outputLine);
			}catch(NumberFormatException ex){
				outputLine = "Error al dar la Respuesta";
			}
			if (outputLine.equals("Respuestas: Bye."))
				break;
		}
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
	public static Double function(String function,String numero) {
		int num = Integer.parseInt(numero);
		if (function.equals("fun:cos")) {
			return Math.cos(num); 
		}
		else if (function.equals("fun:sin")) {
			return Math.sin(num);
		}
		else {
			return Math.tan(num);	
		}
	}
		
}
