import java.net.*;
import java.awt.Color;
import java.io.*;
public class ServerThread implements Runnable{	
	private Socket clientSocket;
	private Manager manager;
	ObjectOutputStream out;
	public ServerThread(Socket clientSocket, Manager manager){
		this.clientSocket = clientSocket;
		this.manager = manager;
		
		System.out.println("ServerThread created.");
	}
	
	public void run(){
		
		System.out.println(Thread.currentThread().getName() + ": connection opened.");
		try{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println(Thread.currentThread().getName() + ": connection closed.");
			while(true){
				String data = (String)in.readObject();
				
				//broadcast serverThreadNumber and data to manager
				data=Thread.currentThread().getName()+":"+data;
				manager.broadcast(data);
			}
		} catch (IOException ex){
			System.out.println("Error listening for a connection");
			System.out.println(ex.getMessage());
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//When receiving a client message, call broadcast from manager.
	public void send(String d){
		try {
			out.reset();
			out.writeObject(d);
			out.flush();
		} catch (IOException ex) {
			System.out.println("Error listening for a connection");
			System.out.println(ex.getMessage());
		}
	}
	
	
}
	




