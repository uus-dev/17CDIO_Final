package SceneBuild_JavaFX;

import java.io.IOException;
import java.sql.SQLException;

import QuickConnect.EmailVal;
import QuickConnect.FunctionUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EmailWindow implements EventHandler<ActionEvent> {

	private Stage myStage;
	private Scene myScene;
	private Pane EmailFrame;
	private String code;
	private String eMail;
	private String userName;
	private String HostMail;
	private String HostPass;
	@FXML private Label lCode;
	@FXML private Button bAccept, bSend;
	@FXML private TextField inCode;
	private FunctionUser fu;
	private EmailVal eVal = new EmailVal();

	public void start(Stage stage, String email, String username, FunctionUser fu) {
		this.eMail = email;
		this.userName = username;
		this.HostMail = eVal.getMail();
		this.HostPass = eVal.getPass();
		this.myStage = stage;
		this.fu = fu;
		this.myStage.setTitle("QuickConnect");
		this.myStage.setResizable(false);
		showEmailFrame();
		this.myScene = new Scene(EmailFrame);

		this.myStage.setScene(myScene);
		this.myStage.show();
		code = eVal.getCode();
		eVal.sendMail(this.HostMail, this.HostPass, this.eMail, "QuickConnect",
		        "Hej " + userName + "\n\nVenligst indtast følgende kode ind:\n\n" + code);
		System.out.println(this.HostMail + this.HostPass + this.eMail);
	}

	private void showEmailFrame() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(EmailWindow.class.getResource("EmailFrame.fxml"));
		loader.setController(this);
		try {
			EmailFrame = (Pane) loader.load();
		} catch(IOException e) {
			e.printStackTrace();
		}
		bSend.setOnAction(this);
		bAccept.setOnAction(this);
		bAccept.setDefaultButton(true);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == bSend) {
			System.out.println("Send");
			code = eVal.getCode();
			eVal.sendMail(this.HostMail, this.HostPass, this.eMail, "QuickConnect",
			        "Hej " + userName + "\n\nVenligst indtast følgende kode ind:\n\n" + code);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Ny kode sendt");
			alert.setHeaderText(null);
			alert.setContentText("Der er blevet sendt en ny kode til din mail!");
			alert.showAndWait();
		}
		if(event.getSource() == bAccept) {
			System.out.println("Accept");
			if(inCode.getText().equals(code)) {
				try {
					fu.activateUserMail(this.userName);
				} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Email Registeret");
				alert.setHeaderText(null);
				alert.setContentText("Du er nu aktiveret!");
				alert.showAndWait();
				Stage stage = new Stage();
				loginWindow lW = new loginWindow();
				myStage.close();
				try {
					lW.start(stage);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Koden er forkert");
				alert.setHeaderText(null);
				alert.setContentText("Venligst indtast koden igen.");

				alert.showAndWait();
			}

		}
	}
}
