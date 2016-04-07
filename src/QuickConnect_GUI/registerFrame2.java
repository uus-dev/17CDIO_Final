package QuickConnect_GUI;

import javax.swing.*;
import QuickConnect.Connector;
import QuickConnect.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.sql.SQLException;

public class registerFrame2 extends JFrame {

	public static void showRegisterFrame() {
		registerFrame2 frameTabel = new registerFrame2();
	}
	
	JPanel panel = new JPanel();
	String myFont = "Times New Roman";
	JLabel welcome = new JLabel("Registrering til LiveChat");
	JLabel luser = new JLabel("Indtast dit ønskede brugernavn:");
	JTextField username = new JTextField(15);
	JLabel lpass = new JLabel("Indtast din ønskede kode:");
	JPasswordField pass = new JPasswordField(15);
	JLabel lpass1 = new JLabel("Gentag din kode:");
	JPasswordField pass1 = new JPasswordField(15);
	JButton bconfirm = new JButton("Godkend");
	Point myPoint = new Point(650, 280);
	JButton bback = new JButton("Tilbage");
	JTextField email = new JTextField(15);
	JLabel lemail = new JLabel("Indtast din E-mail");

	registerFrame2() {
		this.setTitle("LiveChat - Registrering");
		this.setSize(300, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.setLayout(null);
		panel.setName("LiveChat - Registrering");

		welcome.setBounds(40, 5, 300, 50);
		luser.setBounds(45, 45, 300, 50);
		username.setBounds(45, 90, 215, 20);
		lpass.setBounds(65, 105, 300, 50);
		pass.setBounds(45, 150, 215, 20);
		lpass1.setBounds(90, 165, 300, 60);
		pass1.setBounds(45, 210, 215, 20);
		lemail.setBounds(90, 225, 250, 60);
		email.setBounds(45, 270, 215, 20);
		bconfirm.setBounds(155, 305, 100, 40);
		bback.setBounds(45,305,100,40);

		welcome.setFont(new Font(myFont, 1, 20));
		luser.setFont(new Font(myFont, 0, 15));
		lpass.setFont(new Font(myFont, 0, 15));
		lpass1.setFont(new Font(myFont, 0, 15));
		bconfirm.setFont(new Font(myFont, 0, 14));
		bback.setFont(new Font(myFont, 0, 14));
		lemail.setFont(new Font(myFont, 0, 15));

		panel.add(welcome);
		panel.add(luser);
		panel.add(lpass);
		panel.add(lpass1);
		panel.add(username);
		panel.add(pass);
		panel.add(pass1);
		panel.add(bconfirm);
		panel.add(bback);
		panel.add(email);
		panel.add(lemail);

		this.add(panel);
		getRootPane().setDefaultButton(bconfirm);
		setVisible(true);
		actionConfirm();
		actionBack();
	}

	public void actionConfirm() {
		bRegister.setOnAction(new EventHandler<ActionEvent>() {
            @FXML
            public void handle(ActionEvent event) {
				String cuser = username.getText();
				String cpass1 = pass.getText();
				String cpass2 = pass1.getText();
				String cemail = email.getText();
				
				int check=0;
				try {
					check = checkRegister(cuser, cpass1, cpass2, cemail);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				String msg;
				if(check==0) msg="Uforventet fejl";
				else if(check==1) msg="Brugernavnet skal være 4-24 tegn";
				else if(check==2) msg="Brugernavnet må kun indeholde tal, bogstaver og følgende tegn: -,_";
				else if(check==3) msg="Begge passwords skal være ens";
				else if(check==4) msg="Passwordet skal være 8-24 tegn";
				else if(check==5) msg="Passwordet skal mindst indeholde 1 stort bogstav, 1 lille bogstav og 1 tal";
				else if(check==6) msg="Passwordet må kun indeholde tal, bogstaver og følgende tegn:<br><h2>!\"#$%&'(,)*+-./:;<=>?@[\\]^_`{|}~</h2>";
				else if(check==7) msg="Ugyldigt email";
				else if(check==8) msg="Brugeren eksisterer allerede i systemet";
				else msg="Du er registreret";
				
				if(check==9){
					try {
						boolean bool=addUser(cuser, cpass1, cemail);
					} catch (SQLException | NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(panel,
							"<html>Du er registreret!<br><br>"+msg+"</html>",panel.getName(),
		                    JOptionPane.INFORMATION_MESSAGE);
					startFrame2 start = new startFrame2();
					dispose();
					start.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(panel,
		                    "<html>Registrering mislykkedes!<br><br>"+msg+"</html>", panel.getName(),
		                    JOptionPane.WARNING_MESSAGE);
					pass.setText("");
					pass1.setText("");
					username.requestFocusInWindow();
				}
			}
		});
	}
	
	public boolean addUser(String user, String pass, String email) throws SQLException, NoSuchAlgorithmException {
		Connector con=Function.mysql();
		con.update("INSERT INTO users (username,password,email,timestamp) VALUES (?,?,?,?)",
				new String[]{"s",user},
				new String[]{"s",Function.md5(pass)},
				new String[]{"s",email},
				new String[]{"l",Long.toString(Function.timestamp())});
		return false;
	}

	public int checkRegister(String user, String pass1, String pass2, String email) throws SQLException {
		Connector con=Function.mysql();
		boolean bool;
		int in;
		in=Function.checkUsername(user);
		if(in==1) return 1;
		else if(in==2) return 2;
		if(!pass1.equals(pass2)) return 3;
		in=Function.checkPassword(pass1);
		if(in==1) return 4;
		else if(in==2) return 5;
		else if(in==3) return 6;
		if(!Function.checkEmail(email)) return 7;
		bool=con.check("SELECT username FROM users WHERE username=?",user);
		if(bool) return 8;
		else return 9;
	}
	
	public void actionBack() {
		bback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				startFrame2 startFrame = new startFrame2();
				dispose();
				startFrame.setVisible(true);
			}
		});
	}
}