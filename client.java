import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client{
	String username;	//user name
	JTextField usrname;
	PrintWriter writer;

	public void login(){
		JFrame frame1 = new JFrame("login in panel");
		JPanel logininPanel =  new JPanel();
		usrname = new JTextField(20);
		JButton loginButtion = new JButton("login");
		loginButtion.addActionListener(new loginButtionListener());
		logininPanel.add(loginButtion);
		frame1.getContentPane().add(BorderLayout.CENTER,logininPanel);
		frame1.setSize(400,500);
		frame1.setVisible(true);	//show the login panel
	}
	public static void main(String[] args){
		client Client = new client();
		Client.login();
		
		try{
			Socket socket = new Socket("127.0.0.1",4700);	//ask for connection
			System.out.println("success");	
		}catch(Exception e){
			System.out.println("fail");
			System.exit(0);
		}
	}

	public class loginButtionListener implements ActionListener{
			public void actionPerformed(ActionEvent ev){
				try{
					username = usrname.getText();
					writer.println(username);
					writer.flush();	//send the username;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
	}
}
