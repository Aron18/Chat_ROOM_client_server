import java.io.*;
import java.io.ObjectOutputStream; 
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.Message;

public class client{
	String username;	//user name
	JTextField usrname;
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	ObjectOutputStream oos;

	private void setUpNetworking(){		
		try{
			socket = new Socket("127.0.0.1",5000);
			InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(streamReader);
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("networking established");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void login(){
		setUpNetworking();
		JFrame frame1 = new JFrame("login in panel");
		JPanel logininPanel =  new JPanel();
		JLabel label1=new JLabel("UR NAME"); 
		usrname = new JTextField(20);
		JButton loginButtion = new JButton("login");
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
				try{
					Message user = new Message();
					user.name = usrname.getText();
					user.type = "User_name";
					oos.writeObject(user);  
            		oos.flush(); 	//send the user name
            		System.out.println(user.name);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
	}
}
