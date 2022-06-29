package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SignUpController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	@FXML
	private JFXPasswordField txt_password;

	@FXML
	private JFXTextField txt_email;

	@FXML
	private JFXTextField txt_userName;
	
    @FXML
    private JFXPasswordField txt_CIN;
    
    @FXML
    private ImageView icon_importer;
    
    @FXML
    private Label lab_url;
    
    @FXML
    private ImageView image_photo;
    
	@FXML
	void importerImage() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File f = fc.showOpenDialog(null);
		if (f != null) {
			lab_url.setText(f.getAbsolutePath());
			Image image = new Image(f.toURI().toString(), image_photo.getFitWidth(), image_photo.getFitHeight(), true, true);
			image_photo.setImage(image);
		}
	}

	@FXML
	void addAdminn() {
		String userName = txt_userName.getText();
		String password = txt_password.getText();
		String email = txt_email.getText();
		String cin = txt_CIN.getText();
		File image = new File (lab_url.getText());
		String sql = "insert into admin (userName, password, photo, adrMail, CIN) values (?, ?, ?, ?)";
		if (!userName.equals("") && !password.equals("") && !email.equals("") && !cin.equals("")) {
			try {
				st = cnx.prepareStatement(sql);
				st.setString(1, userName);
				st.setString(2, password);
				st.setString(3, email);
				st.setString(4, cin);
				fs = new FileInputStream(image);
				st.setBinaryStream(5, fs, image.length());
				st.executeUpdate();
				txt_userName.setText("");
				txt_password.setText("");
				txt_email.setText("");
				txt_CIN.setText("");
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Usuario Agregado con Éxito", ButtonType.OK);
				alert.showAndWait();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Error al Agregar Usuario", ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	@FXML
	void addAdmin() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Usuario Agregado con Éxito", ButtonType.OK);
		alert.showAndWait();
	}
	
	private FileInputStream fs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();
	}
}