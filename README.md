#  Networking
## Manual de usuario

Si se deseea hacer uso del programa lo primero que debe realizarse el clonar el repositorio almacenado en Github a través del siguiente comando:

```
git clone https://github.com/csarssj/ARSW-3-TallerNetworking

```
O si desea puede descargarlo como archivo zip y luego descomprimirlo en la carpeta que desee.

Una vez hecho alguno de los dos pasos anteriores, nos dirigimos a la ruta de instalación y por medio de la consola digitamos el siguiente comando:

```
mvn package

```

![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/compilado.png)


### Prerequisitos

Este proyecto necesita tener los siguientes progamas instalados en la máquina donde se deseea ejecutar:

```
  java version "1.8.0_251"
  Apache Maven 3.6.3
  git version 2.25.0.windows.1
  jdk1.8.0_251
```

El sistema, mas alla de facilitar el registro de las iniciativas e ideas de proyectos, es una valiosa base de conocimiento donde los diferentes actores pueden revisar si hay iniciativas, ideas o intereses similares y aunar esfuerzos para la materializacion.

## Ejercicios 


## Ejercicio 1

Escriba un programa en el cual usted cree un objeto URL e imprima en pantalla cada uno de los datos que retornan los 8 métodos de la sección anterior.
```
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
```
![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/URL.png)
## Ejercicio 2

Escriba una aplicación browser que pregunte una dirección URL al usuario y que lea datos de esa dirección y que los almacene en un archivo con el nombre resultado.html. Luego intente ver este archivo en el navegador.

```
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
```
![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/resultado.png)

## Ejercicio 4.3.1 

Escriba un servidor que reciba un número y responda el cuadrado de este número.


```
import java.io.*;
import java.net.*;

/**
 *
 * @author ceseg
 */
public class EchoClient {

		public static void main(String[] args) throws IOException {
	
			Socket echoSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
	
			try {
				echoSocket = new Socket("127.0.0.1", 3000);
				out = new PrintWriter(echoSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						echoSocket.getInputStream()));
				} catch (UnknownHostException e) {
					System.err.println("Don’t know about host!.");
					System.exit(1);
				} catch (IOException e) {
					 System.err.println("Couldn’t get I/O for "
							+ "the connection to: localhost.");
					 System.exit(1);
				}
			BufferedReader stdIn = new BufferedReader(
			new InputStreamReader(System.in));
			String userInput;
			System.out.println("Digite su numero");
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println(in.readLine());
			}
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		 }
}
```

```
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

```
![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/EchoServer.png)
## Ejercicio 4.3.2

Escriba un servidor que pueda recibir un número y responda con un operación sobre este número. Este servidor puede recibir un mensaje que empiece por “fun:”, si recibe este mensaje cambia la 
operación a las especiﬁcada. El servidor debe responder las funciones seno, coseno y tangente. Por defecto debe empezar calculando el coseno. Por ejemplo, si el primer número que recibe es 0, 
debe responder 1, si después recibe π/2 debe responder 0, si luego recibe “fun:sin” debe cambiar la operación actual a seno, es decir a a partir de ese momento debe calcular senos. Si enseguida recibe 0 debe responder 0.

```
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
```

![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/Function.png)
## Ejercicio 4.5

Escriba un servidor web que soporte múltiples solicitudes seguidas (no concurrentes). El servidor debe retornar todos los archivos solicitados, incluyendo páginas html e imágenes.
```
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
```

Se ejecuta el siguiente comando 

```
java -cp target\TallerNetworking-1.0-SNAPSHOT.jar edu.escuelaing.arsw.app.HttpServer
```
![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/HttpServer.png)


y se confirman en el localhost los recursos que han de ser cargados:

Con HTML:

![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/dr.png)

Con HTML2:

![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/example.png)

Con Foto:

![image](https://github.com/csarssj/ARSW-3-TallerNetworking/blob/master/img/ca.png)

## Construido en

* [Maven](https://maven.apache.org/) - Dependency Management


## Control de versiones 

[Github](https://github.com/) para el versionamiento.

## Authors

[César González](https://github.com/csarssj) 

_Fecha : 10 de junio del 2020_ 


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) 
