import java.net.*;
import java.io.*;

public class server{
	public static void main(String[] args){
		ServerSocket server = null;
		int count=0;
		try{
			server= new ServerSocket(4700);		
		}catch(Exception e){
			System.out.println("fail and exit");
		}
		while(true){
		Socket socket = null;
		System.out.println("test");
		try{
			socket = server.accept();
			System.out.println("success");
			count+=1;
			System.out.println(count);	
		}catch(Exception e){
			System.out.println("can't establish the link");
			}
		}
	}
}