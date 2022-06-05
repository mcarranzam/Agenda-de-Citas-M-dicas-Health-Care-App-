package controllers;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FacturaController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	@FXML
	private JFXTextField txt_loyer;

	@FXML
	private JFXComboBox<Integer> cb_logement;

	@FXML
	private JFXTextField txt_adr;

	@FXML
	private JFXTextField txt_region;

	@FXML
	private JFXTextField txt_locataire;

	@FXML
	private JFXTextField txt_NAP;

	@FXML
	private ImageView imageLog;

	@FXML
	private JFXTextField txt_forfaitaire;

	@FXML
	private JFXTextField txt_type;

	@FXML
	private JFXComboBox<String> cb_locataire;

	@FXML
	void imprimer() {
		Document docu = new Document();
		try {
			PdfWriter.getInstance(docu, new FileOutputStream("facture.pdf"));
			docu.open();
			String format = "dd/MM/YY hh:mm";
			SimpleDateFormat formater = new SimpleDateFormat(format);
			java.util.Date date  = new java.util.Date();
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("C:\\Users\\PROBOOK\\eclipse-workspace\\HealthCareApp-\\src\\images\\FacturaPDF.png");
			img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			docu.add(img);
			docu.add(new Paragraph("Doctor: " + txt_locataire.getText() + ""
									+ "\n El servicio se prestará en: " + txt_type.getText() + ""
									+ "\n Denominada: " + txt_adr.getText() + ""
									+ "\n Ubicada en: " + txt_region.getText() + ""
									+ "\n Servicio: " + txt_loyer.getText() + ""
									+ "\n Total: " + txt_forfaitaire.getText() + ""
									+ "\n Monto a Pagar: " + txt_forfaitaire.getText() + ""
									+ "\n Generado automaticamente por HealthCareApp el: " + formater.format(date) + ""
									+ "\n\n Cabe destacar que la información aquí presentada es de caracter educativo y por " 
									+ " lo tanto este documento no representa ningún tipo de validez legal."
									+ "\n\n Firmado Electrónicament por,", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK)));
			com.itextpdf.text.Image img1 = com.itextpdf.text.Image.getInstance("C:\\Users\\PROBOOK\\eclipse-workspace\\HealthCareApp-\\src\\images\\Firma.png");
			img1.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			docu.add(img1);
			docu.close();
			Desktop.getDesktop().open(new File("facture.pdf"));
		} 
		catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void remplirLocataire() {
		String sql = "select nomprenomL from locataire, location where locataire.idL=location.locataire and location.logement='" + cb_logement.getValue() + "'";
		List<String> names = new ArrayList<String> ();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				names.add(result.getString("nomprenomL"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_locataire.setItems(FXCollections.observableArrayList(names));
	}

	@FXML
	void search() {
		Integer id = cb_logement.getValue();
		String sql = "select adrL, loyer, nomType, forfaitaire, nomRegion, image from logement, type, region where logement.type=type.idType and logement.region=region.idRegion and logement.idLogement='" + id + "'";
		int nap;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			byte byteImg[];
			Blob blob;
			if(result.next()) {
				txt_adr.setText(result.getString("adrL"));
				int forf = result.getInt("forfaitaire");
				txt_forfaitaire.setText(String.valueOf(forf));
				int loy = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(loy));
				txt_type.setText(result.getString("nomType"));
				txt_region.setText(result.getString("nomregion"));
				txt_locataire.setText(cb_locataire.getValue());
				nap = forf + loy;
				txt_NAP.setText(String.valueOf(nap));
				blob = result.getBlob("image");
				byteImg = blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImg), imageLog.getFitWidth(), imageLog.getFitHeight(), true, true);
				imageLog.setImage(img);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remplirLogement() {
		String sql = "select DISTINCT logement from  location";
		List<Integer> ids = new ArrayList<Integer> ();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				ids.add(result.getInt("logement"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		cb_logement.setItems(FXCollections.observableArrayList(ids));
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();
		remplirLogement();
	}
}	