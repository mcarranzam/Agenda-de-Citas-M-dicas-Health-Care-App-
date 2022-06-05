package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	private Parent fxml;
	
	@FXML
	private AnchorPane root;
	
    @FXML
    private Label lab_username;

    @FXML
    private ImageView imageUser;
	
	@FXML
    void accueil(MouseEvent event) {
		try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Inicio.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void logement(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Hospital.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void locataire(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Doctor.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void location(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Ubicación.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void contrat(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Contrato.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void facture(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Factura.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void historiques(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Historial.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    

    @FXML
    void credits(MouseEvent event) {
    	try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Credits.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		cnx = ConnexionMysql.connexionDB();
		String sql = "select userName, photo from userconnect where id=(select MAX(id) from userconnect)";
		byte byteImg[];
		Blob blob;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				lab_username.setText(result.getString("userName"));
				blob = result.getBlob("photo");
				byteImg = blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImg), imageUser.getFitWidth(), imageUser.getFitHeight(), true, true);
				imageUser.setImage(img);
			}
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			fxml = FXMLLoader.load(getClass().getResource("/interfaces/Inicio.fxml"));
			root.getChildren().removeAll();
			root.getChildren().setAll(fxml);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
