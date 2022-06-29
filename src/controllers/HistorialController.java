package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Ubicacion;

public class HistorialController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	@FXML
    private JFXTextField txt_telephone;

    @FXML
    private TableColumn<Ubicacion, String> cin_fin;

    @FXML
    private TableColumn<Ubicacion, String> cin_debut;

    @FXML
    private JFXTextField txt_adr;

    @FXML
    private JFXTextField txt_nomprenom;

    @FXML
    private JFXTextField txt_loyer;

    @FXML
    private JFXTextField txt_region;

    @FXML
    private JFXTextField txt_logement;

    @FXML
    private TableColumn<Ubicacion, String> cin_locataire;

    @FXML
    private TableColumn<Ubicacion, Integer> cin_logement;

    @FXML
    private JFXTextField txt_fin;

    @FXML
    private JFXTextField txt_type;

    @FXML
    private JFXTextField txt_cin;

    @FXML
    private JFXTextField txt_debut;

    @FXML
    private TableView<Ubicacion> table;
    
    public ObservableList<Ubicacion> data = FXCollections.observableArrayList();
    
    public ObservableList<Ubicacion> data2 = FXCollections.observableArrayList();

    @FXML
    void tableEvent() {
    	Ubicacion location = table.getSelectionModel().getSelectedItem();
    	String sql = "select teleL, CIN from locataire where nomprenomL='" + location.getLocataire() + "'";
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				txt_telephone.setText(result.getString("teleL"));
				txt_cin.setText(result.getString("CIN"));
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	String sql1 = "select logement, nomprenomL, datedebut, dateFin from location, locataire where logement=? and locataire=(select idL from locataire where nomprenomL=?)";
    	try {
			st = cnx.prepareStatement(sql1);
			st.setInt(1, location.getLogement());
			st.setString(2, location.getLocataire());
			result = st.executeQuery();
			while(result.next()) {
				int id = result.getInt("logement");
				txt_logement.setText(String.valueOf(id));
				txt_nomprenom.setText(result.getString("nomprenomL"));
				Date dated = result.getDate("dateDebut");
				txt_debut.setText(String.valueOf(dated));
				Date datef = result.getDate("dateFin");
				txt_fin.setText(String.valueOf(datef));
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	String sql2 = "select adrL, loyer, nomType, nomRegion from logement, type, region where logement.type=type.idType and logement.region=region.idRegion and idLogement='" + txt_logement.getText() + "'";
    	try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			while(result.next()) {
				int l = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(l));
				txt_adr.setText(result.getString("adrL"));
				txt_type.setText(result.getString("nomType"));
				txt_region.setText(result.getString("nomRegion"));
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void remplirTable() {
    	table.getItems().clear();
		String sql = "select idLocation, logement, nomprenomL, dateDebut, dateFin from location, locataire where location.locataire=locataire.idL";
		
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				data.add(new Ubicacion(result.getInt("idLocation"), result.getInt("logement"), result.getString("nomprenomL"), result.getString("dateDebut"), result.getString("dateFin")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		cin_debut.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("dateDebut"));
		cin_fin.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("dateFin"));
		cin_locataire.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("locataire"));
		cin_logement.setCellValueFactory(new PropertyValueFactory<Ubicacion, Integer>("logement"));
		table.setItems(data);
    }

    @FXML
    void searchLogement() {
    	table.getItems().clear();
    	cin_debut.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("dateDebut"));
		cin_fin.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("dateFin"));
		cin_locataire.setCellValueFactory(new PropertyValueFactory<Ubicacion, String>("locataire"));
		cin_logement.setCellValueFactory(new PropertyValueFactory<Ubicacion, Integer>("logement"));
		int k = 0;
		String sql = "select idLocation, logement, nomprenomL, dateDebut, dateFin from location, locataire where location.locataire=locataire.idL and location.logement='" + txt_logement.getText() + "'";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				data2.add(new Ubicacion(result.getInt("idLocation"), result.getInt("logement"), result.getString("nomprenomL"), result.getString("dateDebut"), result.getString("dateFin")));
				k = 1;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		if(k == 1) {
			table.setItems(data2);
		}
		else {
			Alert alert = new Alert(AlertType.ERROR, "No se encontraron Hospitales", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();
		remplirTable();
	}
}