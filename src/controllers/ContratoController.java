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
import java.sql.Date;
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

public class ContratoController implements Initializable {

	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;

	@FXML
	private JFXComboBox<Integer> combo_searchLocation;

	@FXML
	private JFXTextField txt_loyer;

	@FXML
	private JFXTextField txt_adr;

	@FXML
	private JFXTextField txt_locataire;

	@FXML
	private JFXTextField txt_region;

	@FXML
	private JFXTextField txt_dateFin;

	@FXML
	private JFXTextField txt_logement;

	@FXML
	private JFXTextField txt_NAP;

	@FXML
	private ImageView imageLog;

	@FXML
	private JFXTextField txt_forfaitaire;

	@FXML
	private JFXTextField txt_type;

	@FXML
	private JFXTextField txt_dateDebut;

	@FXML
	void imprimer() {
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream ("contrat.pdf"));
			doc.open();
			String format = "dd/MM/YY hh:mm";
			SimpleDateFormat formater = new SimpleDateFormat(format);
			java.util.Date date  = new java.util.Date();
			com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("C:\\Users\\PROBOOK\\eclipse-workspace\\HealthCareApp-\\src\\images\\ContratoPDF.png");
			img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			doc.add(img);
			doc.add(new Paragraph("En este contrato se especifican los detalles de los"
								+ "servicios medicos solicitados en la aplicaci�n, de tal "
								+ "manera que se haga mas f�cil el hecho de poder interpretar " 
								+ "la informaci�n a trav�s de un consolidado que puede ser impreso en medios fisicos." 
								+ "\n\n DATOS DEL SERVICIO M�DICO"
								+ "\n Doctor: " + txt_locataire.getText() + ""
								+ "\n El servicio se prestar� desde el: " + txt_dateDebut.getText()
								+ " hasta el: " + txt_dateFin.getText() + ""
								+ "\n El servicio se prestar� en: " + txt_type.getText() + ""
								+ "\n Denominada: " + txt_adr.getText() + ""
								+ "\n Ubicada en: " + txt_region.getText() + ""
								+ "\n Generado automaticamente por HealthCareApp el: " + formater.format(date) + ""
								+ "\n\n Cabe destacar que la informaci�n aqu� presentada es de caracter educativo y por " 
								+ " lo tanto este documento no representa ning�n tipo de validez legal."
								+ "\n\n Firmado Electr�nicament por,", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK)));
			com.itextpdf.text.Image img1 = com.itextpdf.text.Image.getInstance("C:\\Users\\PROBOOK\\eclipse-workspace\\HealthCareApp-\\src\\images\\Firma.png");
			img1.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
			doc.add(img1);
			doc.close();
			Desktop.getDesktop().open(new File("contrat.pdf"));
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
	void searchLocation() {
		String sql = "select logement, nomprenomL, dateDebut, dateFin from locataire, location where idLocation ='" + combo_searchLocation.getValue() + "' and locataire.idL=location.locataire";
		int log = 0;
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				log = result.getInt("logement");
				txt_logement.setText(String.valueOf(log));
				txt_locataire.setText(result.getString("nomprenomL"));
				Date dated = result.getDate("dateDebut");
				txt_dateDebut.setText(String.valueOf(dated));
				Date datef = result.getDate("dateFin");
				txt_dateFin.setText(String.valueOf(datef));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql2 = "select adrL, loyer, nomtype, nomRegion, forfaitaire, image from logement, type, region where logement.type=type.idType and logement.region=region.idRegion and idLogement='" + txt_logement.getText() + "'";
		int loy = 0;
		int forf = 0;
		float NAP = 0;
		try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			byte byteImag[];
			Blob blob;
			if(result.next()) {
				txt_adr.setText(result.getString("adrL"));
				loy = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(loy));
				txt_type.setText(result.getString("nomType"));
				txt_region.setText(result.getString("nomregion"));
				forf = result.getInt("forfaitaire");
				txt_forfaitaire.setText(String.valueOf(forf));
				NAP = loy + forf;
				txt_NAP.setText(String.valueOf(NAP));
				blob = result.getBlob("image");
				byteImag = blob.getBytes(1, (int) blob.length());
				Image img = new Image(new ByteArrayInputStream(byteImag), imageLog.getFitWidth(), imageLog.getFitHeight(), true, true);
				imageLog.setImage(img); 
;			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remplirCombo() {
		String sql = "select idLocation from location";
		List<Integer> list = new ArrayList<Integer>();
		try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			while(result.next()) {
				list.add(result.getInt("idLocation"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		combo_searchLocation.setItems(FXCollections.observableArrayList(list));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();
		remplirCombo();
	}
}