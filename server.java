import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.event.*;

public class server{
	PrintWriter out;
	String receive,worked;
	Socket socket;
	StringTokenizer	token;

	public class ServerProcess implements Runnable{
		BufferedReader in;
		PrintWriter out;

		public ServerProcess(Socket clientSocket) throws IOException{
			try{
				socket = clientSocket;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public void run(){
			try{
				while(true){
					receive = in.readLine();
					//System.out.println(receive);
					token = new StringTokenizer(receive,"|");	//process the message
					worked = token.nextToken();	//Returns the next token from this string tokenizer
					if(worked.equals("user_name")){
						hello();
					}
					else if (worked.equals("Message")){
						chat();
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
				}
		}

		private void hello(){
			String name = token.nextToken();	//get the user name
			out.println("!!!"+ " " + name + " is online now" + "!!!" );
			out.flush();
			System.out.println("!!!"+ " " + name + " is online now");
		}

		private void chat(){
			;
		}
	}

	public void go(){
		try{
			ServerSocket serverSock = new ServerSocket(5000);
			while(true){
				socket = serverSock.accept();
				Thread t = new Thread(new ServerProcess(socket));
				t.start();
				System.out.println("get a connection");
			}
		}catch(IOException E){
			System.out.println("fail to connect");
		}
	}

	public static void main(String[] args){
		new server().go();
	}
}