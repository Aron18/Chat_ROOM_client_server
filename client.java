import java.net.*;
import java.io.*;
import javax.swing.*;

public class client{
	public static void main(String[] args){
		try{
			Socket socket = new Socket("127.0.0.1",4700);	//向服务器请求连接
			System.out.println("success");	
		}catch(Exception e){
			System.out.println("Fail");
			System.exit(0);
		}
	}
}
