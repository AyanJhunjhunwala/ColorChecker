import java.net.*;
import java.io.*;

public class Server {
	public static void main(String[] args) throws IOException {
		int portNumber = 1025;
		ServerSocket serverSocket = new ServerSocket(portNumber);
		Manager manager = new Manager();

		//This loop will run and wait for one connection at a time.
		while(true){
			System.out.println("Waiting for a connection");
			Socket clientSocket = serverSocket.accept();
						//Once a connection is made, run the socket in a ServerThread.
			ServerThread x = new ServerThread(clientSocket, manager);
			manager.addServerThread(x);
			Thread thread = new Thread(x);
			thread.start();
		
		}
	}
}

