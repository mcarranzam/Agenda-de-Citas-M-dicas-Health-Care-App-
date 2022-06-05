package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.Hospital;

public class HospitalController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	@FXML
	private JFXTextField txt_searchId;

	@FXML
	private Label lab_url;

	@FXML
	private JFXTextField txt_adr;

	@FXML
	private JFXComboBox<String> cb_province;

	@FXML
	private TableView<Hospital> table_logement;

	@FXML
	private TableColumn<Hospital, String> cin_adr;

	@FXML
	private JFXButton btn_edit;

	@FXML
	private JFXTextField txt_superficie;

	@FXML
	private TableColumn<Hospital, Integer> cin_loyer;

	@FXML
	private JFXComboBox<String> cb_commune;

	@FXML
	private JFXComboBox<String> cb_type;

	@FXML
	private ImageView image_logement;

	@FXML
	private JFXTextField txt_loyer;

	@FXML
	private JFXButton btn_add;

	@FXML
	private TableColumn<Hospital, Integer> cin_superficie;

	@FXML
	private JFXComboBox<String> cb_region;

	@FXML
	private TableColumn<Hospital, Integer> cin_id;

	@FXML
	private TableColumn<Hospital, String> cin_type;

	@FXML
	private JFXButton btn_delete;

	@FXML
	private ImageView icon_importer;

	@FXML
	private TableColumn<Hospital, String> cin_region;

	@FXML
	void remplirProvince() {
		String sql = "select nomProvince from province where regiom=(select idRegion from region where nomRegion = '" + cb_region.getValue() + "')";
		List<String> provinces = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				provinces.add(result.getString("nomProvince"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_province.setItems(FXCollections.observableArrayList(provinces));
	}

	@FXML
	void remplirCommune() {
		String sql = "select nomCommune from commune where province=(select idProvince from province where nomProvince = '" + cb_province.getValue() + "')";
		List<String> communes = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				communes.add(result.getString("nomCommune"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_commune.setItems(FXCollections.observableArrayList(communes));
	}

	@FXML
	void modifierLogement() {
		String adr = txt_adr.getText();
		String superf = txt_superficie.getText();
		int superficie = Integer.parseInt(superf);
		String loy = txt_loyer.getText();
		int loyer = Integer.parseInt(loy);
		String typ = cb_type.getValue();
		String sql1 = "select idType from type where nomType ='" + typ + "'";
		int type = 0;
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if(result.next()) {
				type = result.getInt("idType");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String reg = cb_region.getValue();
		String sql2 = "select idregion from region where nomregion ='" + reg + "'";
		int region = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			if(result.next()) {
				region = result.getInt("idRegion");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String prov = cb_province.getValue();
		String sql3 = "select idProvince from province where nomProvince ='" + prov + "'";
		int province = 0;
		try {
			st = cnx.prepareStatement(sql3);
			result = st.executeQuery();
			if(result.next()) {
				province = result.getInt("idProvince");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String com = cb_commune.getValue();
		String sql4 = "select idCommune from commune where nomCommune ='" + com + "'";
		int commune = 0;
		try {
			st = cnx.prepareStatement(sql4);
			result = st.executeQuery();
			if(result.next()) {
				commune = result.getInt("idCommune");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		File image = new File (lab_url.getText());
		String sql = "update logement set adrL=?, superficie=?, loyer=?, type=?, region=?, province=?, commune=?, image=? where idLogement ='" + txt_searchId.getText() + "'";
		try {
			st = cnx.prepareStatement(sql);
			st.setString(1, adr);
			st.setInt(2, superficie);
			st.setInt(3, loyer);
			st.setInt(4, type);
			st.setInt(5, region);
			st.setInt(6, province);
			st.setInt(7, commune);
			fs = new FileInputStream(image);
			st.setBinaryStream(8, fs, image.length());
			st.executeUpdate();
			showLogement();
			lab_url.setText("Aucune Selectione");
			txt_adr.setText("");
			txt_loyer.setText("");
			txt_searchId.setText("");
			txt_superficie.setText("");
			cb_commune.setValue("commune");
			cb_region.setValue("region");
			cb_type.setValue("type");
			cb_province.setValue("province");
			image_logement.setImage(null);
			Alert alert = new Alert (AlertType.CONFIRMATION, "Hospital Modificado con Éxito", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void supprimerLogement() {
		String sql = "delete from logement where idLogement ='" + txt_searchId.getText() + "'";
		try {
			st = cnx.prepareStatement(sql);
			st.executeUpdate();
			showLogement();
			lab_url.setText("Aucune Selectione");
			txt_adr.setText("");
			txt_loyer.setText("");
			txt_searchId.setText("");
			txt_superficie.setText("");
			cb_commune.setValue("commune");
			cb_region.setValue("region");
			cb_type.setValue("type");
			cb_province.setValue("province");
			image_logement.setImage(null);
			Alert alert = new Alert (AlertType.CONFIRMATION, "Hospital Eliminado con Éxito", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private FileInputStream fs;
	
	@FXML
	void ajouerLogement() {
		String adr = txt_adr.getText();
		String superf = txt_superficie.getText();
		int superficie = Integer.parseInt(superf);
		String loy = txt_loyer.getText();
		int loyer = Integer.parseInt(loy);
		String typ = cb_type.getValue();
		String sql1 = "select idType from type where nomType ='" + typ + "'";
		int type = 0;
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if(result.next()) {
				type = result.getInt("idType");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String reg = cb_region.getValue();
		String sql2 = "select idregion from region where nomregion ='" + reg + "'";
		int region = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			if(result.next()) {
				region = result.getInt("idRegion");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String prov = cb_province.getValue();
		String sql3 = "select idProvince from province where nomProvince ='" + prov + "'";
		int province = 0;
		try {
			st = cnx.prepareStatement(sql3);
			result = st.executeQuery();
			if(result.next()) {
				province = result.getInt("idProvince");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String com = cb_commune.getValue();
		String sql4 = "select idCommune from commune where nomCommune ='" + com + "'";
		int commune = 0;
		try {
			st = cnx.prepareStatement(sql4);
			result = st.executeQuery();
			if(result.next()) {
				commune = result.getInt("idCommune");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		File image = new File (lab_url.getText());
		String sql = "insert into logement(adrL, superficie, loyer, type, region, province, commune, image) values(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			st = cnx.prepareStatement(sql);
			st.setString(1, adr);
			st.setInt(2, superficie);
			st.setInt(3, loyer);
			st.setInt(4, type);
			st.setInt(5, region);
			st.setInt(6, province);
			st.setInt(7, commune);
			fs = new FileInputStream(image);
			st.setBinaryStream(8, fs, image.length());
			st.executeUpdate();
			showLogement();
			lab_url.setText("Aucune Selectione");
			txt_adr.setText("");
			txt_loyer.setText("");
			txt_searchId.setText("");
			txt_superficie.setText("");
			cb_commune.setValue("commune");
			cb_region.setValue("region");
			cb_type.setValue("type");
			cb_province.setValue("province");
			image_logement.setImage(null);
			Alert alert = new Alert (AlertType.CONFIRMATION, "Hospital Agregado con Éxito", javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void importerImage() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File f = fc.showOpenDialog(null);
		if (f != null) {
			lab_url.setText(f.getAbsolutePath());
			Image image = new Image(f.toURI().toString(), image_logement.getFitWidth(), image_logement.getFitHeight(), true, true);
			image_logement.setImage(image);
		}
	}

	@FXML
	void searchLogement() {
		String sql = "select nomProvince from province";
		List<String> provinces = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				provinces.add(result.getString("nomProvince"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_province.setItems(FXCollections.observableArrayList(provinces));
		
		String sql1 = "select nomCommune from commune";
		List<String> communes = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			while(result.next()) {
				communes.add(result.getString("nomCommune"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_commune.setItems(FXCollections.observableArrayList(communes));
		
		String sql2 = "select idLogement, adrL, superficie, loyer, nomType, nomregion, nomProvince, nomCommune, image from logement, type, province, commune, region where logement.type=type.idType and logement.region=region.idRegion and logement.commune=commune.idCommune and logement.province=province.idProvince and idLogement=?";
		try {
			st = cnx.prepareStatement(sql2);
			st.setString(1, txt_searchId.getText());
			result = st.executeQuery();
			byte byteImage[];
			Blob blob;
			while(result.next()) {
				int id = result.getInt("idLogement");
				txt_searchId.setText(String.valueOf(id));
				txt_adr.setText(result.getString("adrL"));
				int sur = result.getInt("superficie");
				txt_superficie.setText(String.valueOf(sur));
				int loyer = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(loyer));
				cb_type.setValue(result.getString("nomType"));
				cb_region.setValue(result.getString("nomRegion"));
				cb_province.setValue(result.getString("nomProvince"));
				cb_commune.setValue(result.getString("nomCommune"));
				blob = result.getBlob("image");
				byteImage = blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImage), image_logement.getFitWidth(), image_logement.getFitHeight(), true, true);
				image_logement.setImage(img);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void tableLogEvent() {
		String sql = "select nomProvince from province";
		List<String> provinces = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				provinces.add(result.getString("nomProvince"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_province.setItems(FXCollections.observableArrayList(provinces));
		
		String sql1 = "select nomCommune from commune";
		List<String> communes = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			while(result.next()) {
				communes.add(result.getString("nomCommune"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_commune.setItems(FXCollections.observableArrayList(communes));
		
		Hospital logement = table_logement.getSelectionModel().getSelectedItem();
		String sql2 = "select idLogement, adrL, superficie, loyer, nomType, nomregion, nomProvince, nomCommune, image from logement, type, province, commune, region where logement.type=type.idType and logement.region=region.idRegion and logement.commune=commune.idCommune and logement.province=province.idProvince and idLogement=?";
		try {
			st = cnx.prepareStatement(sql2);
			st.setInt(1, logement.getId());
			result = st.executeQuery();
			byte byteImage[];
			Blob blob;
			while(result.next()) {
				int id = result.getInt("idLogement");
				txt_searchId.setText(String.valueOf(id));
				txt_adr.setText(result.getString("adrL"));
				int sur = result.getInt("superficie");
				txt_superficie.setText(String.valueOf(sur));
				int loyer = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(loyer));
				cb_type.setValue(result.getString("nomType"));
				cb_region.setValue(result.getString("nomRegion"));
				cb_province.setValue(result.getString("nomProvince"));
				cb_commune.setValue(result.getString("nomCommune"));
				blob = result.getBlob("image");
				byteImage = blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImage), image_logement.getFitWidth(), image_logement.getFitHeight(), true, true);
				image_logement.setImage(img);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
    }

	ObservableList<Hospital> listLog = FXCollections.observableArrayList();

	public void showLogement() {
		table_logement.getItems().clear();
		String sql = "select idLogement, adrL, superficie, loyer, nomType, nomRegion from logement, type, region where logement.region=region.idRegion and logement.type=type.idType";
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while (result.next()) {
				listLog.add(new Hospital(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), result.getString(5), result.getString(6)));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		cin_id.setCellValueFactory(new PropertyValueFactory<Hospital, Integer> ("id"));
		cin_adr.setCellValueFactory(new PropertyValueFactory<Hospital, String> ("adr"));
		cin_loyer.setCellValueFactory(new PropertyValueFactory<Hospital, Integer> ("loyer"));
		cin_region.setCellValueFactory(new PropertyValueFactory<Hospital, String> ("region"));
		cin_superficie.setCellValueFactory(new PropertyValueFactory<Hospital, Integer> ("superficie"));
		cin_type.setCellValueFactory(new PropertyValueFactory<Hospital, String> ("type"));
		table_logement.setItems(listLog);
	}
	
	public void remplirType() {
		String sql = "select nomType from type";
		List<String> types = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				types.add(result.getString("nomType"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_type.setItems(FXCollections.observableArrayList(types));
	}
	
	public void remplirRegion() {
		String sql = "select nomRegion from region";
		List<String> types = new ArrayList<String>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				types.add(result.getString("nomRegion"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_region.setItems(FXCollections.observableArrayList(types));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cnx = ConnexionMysql.connexionDB();
		showLogement();
		remplirType();
		remplirRegion();
	}
}