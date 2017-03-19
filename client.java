import java.io.*;
import java.io.ObjectOutputStream; 
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class client{
	String name,username;
	JTextField usrname;
	JFrame frame1;	//login panel
	JFrame frame2;	//chat panel
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	JButton loginButtion;
	JButton sendButton;
	JTextArea inputarea,chatpanel;

	private void setUpNetworking(){		
		try{
			socket = new Socket("127.0.0.1",5000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

			//Thread t = new Thread(new inputListener());
			//t.start();
			//System.out.println("networking established");

			}catch(IOException ex){
				System.out.println("Fail");
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
		setUpNetworking();

		frame1.getContentPane().add(BorderLayout.CENTER,logininPanel);
		frame1.setSize(400,500);
		frame1.setVisible(true);	//show the login panel
	}

	public void chatPane(){
		frame2 = new JFrame("Chat Room");
		JPanel chatP = new JPanel();
		chatpanel = new JTextArea(10,30);	//show the chat history
		inputarea = new JTextArea(5,20);	//the area to input the message
		sendButton = new JButton("Send");
		
		sendButton.addActionListener(new sendButtonListener());
		chatpanel.setLineWrap(true);	//change lines
		chatpanel.setEditable(false);	//don't allow to edit
		
		chatP.add(chatpanel);
		chatP.add(inputarea);
		chatP.add(sendButton);

		frame2.getContentPane().add(BorderLayout.CENTER,chatP);
		frame2.setSize(400,600);
		frame2.setVisible(false);
	}

	public static void main(String[] args){
		client Client = new client();
		Client.login();
		Client.chatPane();
	}

	public class loginButtionListener implements ActionListener{
			public void actionPerformed(ActionEvent ev){
				Object obj = ev.getSource();	//choose a button to react
				try{
					if(obj.equals(loginButtion))
					{
						System.out.println("test");
						if(usrname.getText().length() > 0){
							name = "user_name|" + usrname.getText();
							username = usrname.getText();
							System.out.println(name);
							out.println(name);
							out.flush();
						}
						frame1.setVisible(false);
						frame2.setVisible(true);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("U R offline");
				}
			}
	}

	public class sendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object obj = ev.getSource();
			String message;
			try{
				if(obj.equals(sendButton)){
					if(inputarea.getText().length() > 0){
						//System.out.println(inputarea.getText());
						message = "Message|" + inputarea.getText() + "|" + username;
						System.out.println(message);
						out.println(message);
						out.flush();
						inputarea.setText("");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("User is offline");
			}
		}
	}

	/*public inputListener implements Runnable{
			
	}*/
}
