package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Doctor;

public class DoctorController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	@FXML
	private TableColumn<Doctor, String> cin_cin;

	@FXML
	private TableColumn<Doctor, Date> cin_date;

	@FXML
	private TableColumn<Doctor, String> cin_nom;

	@FXML
	private JFXButton btn_edit;

	@FXML
	private TableColumn<Doctor, String> cin_tele;

	@FXML
	private DatePicker datePicker;

	@FXML
	private JFXTextField txt_tele;

	@FXML
	private JFXButton btn_add;

	@FXML
	private JFXTextField txt_searchCIN;

	@FXML
	private TableView<Doctor> table_locataire;

	@FXML
	private TableColumn<Doctor, Integer> cin_id;

	@FXML
	private JFXButton btn_delete;

	@FXML
	private JFXTextField txt_nom;

	@FXML
	private JFXTextField txt_CIN;

	@FXML
	void editLocataire() {
		String nom = txt_nom.getText();
		String tele = txt_tele.getText();
		String cin = txt_CIN.getText();
		String sql = "update locataire set nomprenomL=?, datenaissL=?, teleL=?, CIN=? where CIN = '" + txt_searchCIN.getText() + "'";
		if (!nom.equals("") && !tele.equals("") && !cin.equals("") && !datePicker.getValue().equals(null)) {
			try {
				st = cnx.prepareStatement(sql);
				st.setString(1, nom);
				java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date sqlDate = new Date(date.getTime());
				st.setDate(2, sqlDate);
				st.setString(3, tele);
				st.setString(4, cin);
				st.executeUpdate();
				txt_CIN.setText("");
				txt_nom.setText("");
				txt_searchCIN.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Doctor Modificado con Éxito", javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				showLocataire();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Llenar todos los espacios disponibles", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void deleteLocataire() {
		String sql = "delete from locataire where CIN = '" + txt_searchCIN.getText() + "'";
		try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			txt_CIN.setText("");
			txt_nom.setText("");
			txt_searchCIN.setText("");
			txt_tele.setText("");
			datePicker.setValue(null);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Doctor Eliminado con Éxito", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
			showLocataire();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void addLocataire() {
		String nom = txt_nom.getText();
		String tele = txt_tele.getText();
		String cin = txt_CIN.getText();
		String sql = "insert into locataire (nomprenomL, datenaissL, teleL, CIN) values (?, ?, ?, ?)";
		if (!nom.equals("") && !tele.equals("") && !cin.equals("") && !datePicker.getValue().equals(null)) {
			try {
				st = cnx.prepareStatement(sql);
				st.setString(1, nom);
				java.util.Date date = java.util.Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date sqlDate = new Date(date.getTime());
				st.setDate(2, sqlDate);
				st.setString(3, tele);
				st.setString(4, cin);
				st.execute();
				txt_CIN.setText("");
				txt_nom.setText("");
				txt_searchCIN.setText("");
				txt_tele.setText("");
				datePicker.setValue(null);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Doctor Agregado con Éxito", javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
				showLocataire();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Llenar todos los espacios disponibles", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void searchLocataire() {
		String sql = "select nomprenomL, CIN, datenaissL, teleL from locataire where CIN = '" + txt_searchCIN.getText() + "'";
		int m = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if (result.next()) {
				txt_CIN.setText(result.getString("CIN"));
				txt_tele.setText(result.getString("teleL"));
				txt_nom.setText(result.getString("nomprenomL"));
				Date date = result.getDate("datenaissL");
				datePicker.setValue(date.toLocalDate());
				m = 1;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (m == 0) {
			Alert alert = new Alert(AlertType.ERROR, "No se encontró ningún Doctor con CC =" + txt_searchCIN.getText(), javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		}
	}

	public ObservableList<Doctor> data = FXCollections.observableArrayList();

	public void showLocataire() {
		table_locataire.getItems().clear();
		String sql = "select * from locataire";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				data.add(new Doctor(result.getInt("idL"), result.getString("nomprenomL"), result.getDate("datenaissL"), result.getString("teleL"), result.getString("CIN")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		cin_cin.setCellValueFactory(new PropertyValueFactory<Doctor, String>("cin"));
		cin_date.setCellValueFactory(new PropertyValueFactory<Doctor, Date>("dateNaiss"));
		cin_id.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("id"));
		cin_nom.setCellValueFactory(new PropertyValueFactory<Doctor, String>("nomprenom"));
		cin_tele.setCellValueFactory(new PropertyValueFactory<Doctor, String>("tele"));
		table_locataire.setItems(data);
	}

    @FXML
    void tableLocataireEvent() {
    	Doctor locataire = table_locataire.getSelectionModel().getSelectedItem();
    	String sql = "select * from locataire where idL=?";
    	try {
			st = cnx.prepareStatement(sql);
			st.setInt(1, locataire.getId());
			result = st.executeQuery();
			if (result.next()) {
				txt_CIN.setText(result.getString("CIN"));
				txt_tele.setText(result.getString("teleL"));
				txt_nom.setText(result.getString("nomprenomL"));
				Date date = result.getDate("datenaissL");
				datePicker.setValue(date.toLocalDate());
				txt_searchCIN.setText(result.getString("CIN"));
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		cnx = ConnexionMysql.connexionDB();
		showLocataire();
	}
}
