package QuickConnect_GUI;

import javax.swing.*;

import QuickConnect.Connector;
import QuickConnect.Function;
import java.sql.SQLException;

import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;

public class startFrame extends JFrame {

	public static void start() {
		startFrame frameTabel = new startFrame();
	}
	
	JPanel panel = new JPanel();
	String myFont = "Times New Roman";
	JLabel lwelcome = new JLabel("Velkommen til LiveChat");
	JLabel lnavn = new JLabel("Indtast brugernavn");
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);
	JButton blogin = new JButton("Login");
	JLabel lnoUser = new JLabel("Har du ikke en bruger?");
	JLabel lregister = new JLabel("Registrer dig her:");
	JButton bregister = new JButton("Registrer");
	Point myPoint = new Point(650, 280);
	ImageIcon img = new ImageIcon("C:\\Users\\Samil\\Desktop\\fav.png");

	startFrame() {
		this.setTitle("LiveChat");
		this.setSize(330, 390);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		panel.setName("LiveChat");
		
		lwelcome.setBounds(50, 10, 300, 50);
		lnavn.setBounds(125, 55, 200, 50);
		txuser.setBounds(90, 110, 150, 20);
		blogin.setBounds(90, 185, 150, 30);
		pass.setBounds(90, 145, 150, 20);
		lnoUser.setBounds(88, 235, 200, 50);
		lregister.setBounds(108, 255, 200, 50);
		bregister.setBounds(115, 300, 100, 30);

		lwelcome.setFont(new Font(myFont, 1, 20));
		lnavn.setFont(new Font(myFont, 0, 17));
		blogin.setFont(new Font(myFont, 0, 14));
		lnoUser.setFont(new Font(myFont, 0, 15));
		lregister.setFont(new Font(myFont, 0, 15));
		bregister.setFont(new Font(myFont, 0, 14));
		

		panel.add(lwelcome);
		panel.add(lnavn);
		panel.add(lnoUser);
		panel.add(lregister);
		panel.add(blogin);
		panel.add(bregister);
		panel.add(txuser);
		panel.add(pass);

		this.add(panel);
		getRootPane().setDefaultButton(blogin);
		setVisible(true);
		actionLogin();
		actionRegister();
	}

	public void actionLogin() {
		blogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String userIn = txuser.getText();
				String passIn = pass.getText();
				Connector con = Function.mysql();
				boolean bool= false;
				try {
				 bool=con.check("SELECT username FROM users WHERE username=? AND password=?",userIn,Function.md5(passIn));
					System.out.println(bool);
				}catch (SQLException | NoSuchAlgorithmException e){
					e.printStackTrace();
				}
				
				if(bool== true){
					loginFrame logFace = new loginFrame(userIn, passIn);
					logFace.setVisible(true);
					dispose();
				}
				
				/*
				if(userIn.equals("DTU") && passIn.equals("12345")) {
					loginFrame logFace = new loginFrame(userIn, passIn);
					logFace.setVisible(true);
					dispose();
				} 
				*/
				
				else{
					JOptionPane.showMessageDialog(panel,
		                    "<html>Login mislykkedes!<br><br>Forkert brugernavn eller password", panel.getName(),
		                    JOptionPane.INFORMATION_MESSAGE);
					txuser.setText("");
					pass.setText("");
					txuser.requestFocusInWindow();
				}
			
			}
		});
	}

	public void actionRegister(){
		bregister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				registerFrame regFace = new registerFrame();
				regFace.setVisible(true);
				dispose();
			}
		});
	}
}