import java.net.*;
import java.io.*;

public class server{
	public static void main(String[] args){
		ServerSocket server = null;
		try{
			server= new ServerSocket(4700);		
		}catch(Exception e){
			System.out.println("fail");
		}
		Socket socket = null;
		try{
			socket = server.accept();
			System.out.println("success");	
		}catch(Exception e){
			System.out.println("can't establish the link");
		}
	}
}