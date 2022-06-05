package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class SignInController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	@FXML
	private JFXPasswordField txt_password;

	@FXML
	private JFXButton btn_seconnecter;

	@FXML
	private JFXButton btn_passwordForgoten;

	@FXML
	private JFXTextField txt_userName;

	@FXML
	private VBox VBox;

	@FXML
	void sendPassword() {
		String sql = "select * from admin where userName='" + txt_userName.getText() + "'";
		String email = "empty";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				email = result.getString("adrMail");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		sendMail(email);
	}

	public void sendMail(String recepient) {
		Properties properties = new Properties();
		//properties.put("mail.smtp.auth", "true");
		//properties.put("mail.smtp.starttls.enable", "true");
		//properties.put("mail.smtp.host", "smtp.gmail.com");
		//properties.put("mail.smtp.port", "587");
		//final String myAccountEmail = "HealthCareAppED@gmail.com";
		//final String password = "ArribaElSocialismo";
		
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		final String myAccountEmail = "HealthCareAppED@gmail.com";
		final String password = "ArribaElSocialismo";

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		Message message = prepareMessage(session, myAccountEmail, recepient);
		try {
			Transport.send(message);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Se ha enviado un mail a su correo con la Contraseña", ButtonType.OK);
			alert.showAndWait();
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private Message prepareMessage(Session session, String myAccountEmail, String recepient) {
		String sql = "select * from admin where userName='" + txt_userName.getText() + "'";
		String pass = "empty";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				pass = result.getString("password");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		String text = "La Contraseña asociada a su cuenta en HealthCareApp es: " + pass + "";
		String object = "Recuperación de Contrase�a HealthCareApp";
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject(object);
			String htmlcode = "<h1> " + text + " </h1> <h2><b> </b></h2>";
			message.setContent(htmlcode, "text/html");
			return message;
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Parent fxml;
	@FXML
	void openHome() {
		String nom = txt_userName.getText();
		String pass = txt_password.getText();
		String sql = "select userName, password, id, CIN, adrMail, photo from admin";
		int nb = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				if (nom.equals(result.getString("userName")) && pass.equals(result.getString("password"))) {
					nb = 1;
					String sql2 = "insert into userconnect(userName, password, CIN, adrMail, photo) values(?, ?, ?, ?, ?)";
					st = cnx.prepareStatement(sql2);
					st.setString(1, result.getString("userName"));
					st.setString(2, result.getString("password"));
					st.setString(3, result.getString("CIN"));
					st.setString(4, result.getString("adrMail"));
					st.setBlob(5, result.getBlob("photo"));
					st.executeUpdate();
					VBox.getScene().getWindow().hide();
					Stage home = new Stage();
					try {
						fxml = FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
						Scene scene = new Scene(fxml);
						home.setScene(scene);
						home.show();
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (nb == 0) {
			Alert alert = new Alert(AlertType.ERROR, "Credenciales Incorrectas", ButtonType.OK);
			alert.showAndWait();
			txt_userName.setText("");
			txt_password.setText("");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();	
	}
}