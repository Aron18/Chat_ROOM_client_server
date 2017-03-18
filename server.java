import java.io.*;
import java.net.*;
import java.util.*;
import java.io.ObjectInputStream;  
import com.Message;

public class server{
	ArrayList clientOutputStreams;
	ArrayList outputname;
	ObjectOutputStream oos;
	PrintWriter helloname;
	PrintWriter writer;

	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		ObjectInputStream ois;

		public ClientHandler(Socket clientSocket){
			try{
				sock = clientSocket;
				ois = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
				//Object obj = ois.readObject();  //get the object sent bt client
				//InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				//reader = new BufferedReader(isReader);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public void run(){
			String name;
			String message;
			try{
				System.out.println("test1");
				Object obj = ois.readObject();   
                Message user = (Message)obj;
                if(user.getType() == "User_name"){
                	name = user.getName();
                	System.out.println("user: " + user.getName());
                	Hello(name);    
                } 
				/*while((message = reader.readLine()) != null){
					System.out.println("read " + message);
					Send(message);
					}*/
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}

	public void go(){
		clientOutputStreams = new ArrayList();
		outputname = new ArrayList();
		try{
			ServerSocket serverSock = new ServerSocket(5000);

			while(true){
				Socket clientSocket = serverSock.accept();
				helloname = new PrintWriter(clientSocket.getOutputStream());
				writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				System.out.println("test");

				Thread t = new Thread(new ClientHandler(clientSocket));	//wait for new user
				t.start();
				System.out.println("got a connection");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void Send(String message){
		Iterator it = clientOutputStreams.iterator();
		while(it.hasNext()){
			try{
				writer = (PrintWriter)it.next();
				writer.println(message);
				writer.flush();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public void Hello(String message){
		System.out.println("Hello " + message);
		writer.println("Hello " + message);
		writer.flush();
	}

	public static void main(String[] args){
		new server().go();
	}
}