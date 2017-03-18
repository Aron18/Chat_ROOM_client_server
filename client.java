import java.io.*;
import java.io.ObjectOutputStream; 
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client{
	String username;	//user name
	JTextField usrname;
	JFrame frame1;
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	JButton loginButtion;

	private void setUpNetworking(){		
		try{
			socket = new Socket("127.0.0.1",5000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			System.out.println("networking established");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void login(){
		setUpNetworking();
		frame1 = new JFrame("login in panel");
		JPanel logininPanel =  new JPanel();
		JLabel label1=new JLabel("UR NAME"); 
		usrname = new JTextField(20);
		loginButtion = new JButton("login");
		JScrollPane qScroller = new JScrollPane(usrname);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		loginButtion.addActionListener(new loginButtionListener());
		logininPanel.add(qScroller);
		logininPanel.add(label1);
		logininPanel.add(usrname);
		logininPanel.add(loginButtion);
		
		frame1.getContentPane().add(BorderLayout.CENTER,logininPanel);
		frame1.setSize(400,500);
		frame1.setVisible(true);	//show the login panel
	}

	public static void main(String[] args){
		client Client = new client();
		Client.login();
	}

	public class loginButtionListener implements ActionListener{
			public void actionPerformed(ActionEvent ev){
				Object obj = ev.getSource();	//choose a button to react
				String name;
				try{
					if(obj.equals(loginButtion)){
						if(usrname.getText().length() > 0){
							setUpNetworking();
							name = "user_name|" + usrname.getText();
							out.println(name);
							out.flush();
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
	}
}
