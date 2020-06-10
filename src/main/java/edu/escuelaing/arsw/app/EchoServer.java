package edu.escuelaing.arsw.app;

/**
 *
 * @author ceseg
 */
import java.net.*;
import java.io.*;
public class EchoServer {
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
		while ((inputLine = in.readLine()) != null) {
			try {
				int num = Integer.parseInt(inputLine);
				outputLine = "Respuesta: " + num*num ;
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
}
