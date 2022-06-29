package controllers;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import application.ConnexionMysql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UbicacionController implements Initializable {
	
	Connection cnx;
	public PreparedStatement st;
	public ResultSet result;
	
	@FXML
    private JFXTextField txt_loyer;

    @FXML
    private JFXTextField txt_cinSearch;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private JFXTextField txt_searchLogementId;

    @FXML
    private JFXTextField txt_adr;

    @FXML
    private JFXTextField txt_nomPrenom;

    @FXML
    private JFXTextField txt_region;

    @FXML
    private DatePicker dateFin;

    @FXML
    private JFXTextField txt_type;

    @FXML
    private JFXTextField txt_CIN;

    @FXML
    private JFXTextField txt_tele;

    @FXML
    private JFXTextField txt_periode;
    
    @FXML
    private ImageView imageLog;
    
    @FXML
    void periode() {
    	Date dated = Date.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date dateDebut = new java.sql.Date(dated.getTime());
    	Date datef = Date.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date dateFin = new java.sql.Date(datef.getTime());
    	int days = daysBetween(dateDebut, dateFin);
    	int mois = days;
    	txt_periode.setText(String.valueOf(mois));
    }
    
    public int daysBetween(java.sql.Date d1, java.sql.Date d2) {
    	return (int) ((d2.getTime()-d1.getTime())/(1000*60*60*24));
    }
    
    public Boolean isBetween(java.sql.Date my_date, java.sql.Date my_debut, java.sql.Date my_fin) {
    	return(my_date.equals(my_debut) || my_date.after(my_debut)) && (my_date.equals(my_fin) || my_date.before(my_fin));
    }
    
    public Boolean isOut(java.sql.Date dateDebut, java.sql.Date dateFin, java.sql.Date my_debut, java.sql.Date my_fin) {
    	return(dateDebut.before(my_debut) && dateFin.after(my_fin));
    }

    @FXML
    void addLocation() {
    	String sql = "select idL from locataire where CIN ='" + txt_CIN.getText() + "'";
    	int locataire = 0;
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				locataire = result.getInt("idL");
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	String sql1 = "select idLogement from logement where adrL ='" + txt_adr.getText() + "'";
    	int logement = 0;
    	try {
			st = cnx.prepareStatement(sql1);
			result = st.executeQuery();
			if(result.next()) {
				logement = result.getInt("idLogement");
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	Date datedd = Date.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date dateDebut = new java.sql.Date(datedd.getTime());
    	Date dateff = Date.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	java.sql.Date dateFin = new java.sql.Date(dateff.getTime());
    	
    	String sql2 = "select dateDebut, dateFin from location where logement ='" + logement + "'";
    	Boolean debut = false;
    	Boolean fin = false;
    	java.sql.Date dated = null;
    	java.sql.Date datef = null;
    	Date d = null;
    	Date f = null;
    	try {
			st = cnx.prepareStatement(sql2);
			result = st.executeQuery();
			while(result.next()) {
				dated = result.getDate("dateDebut");
				datef = result.getDate("dateFin");
				if(isBetween(dateFin, dated, datef) == true) {
					fin = true;
				}
				if(isBetween(dateDebut, dated, datef) == true) {
					debut = true;
				}
				if(isOut(dateDebut, dateFin, dated, datef) == true) {
					fin = true;
					debut = true;
				}
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	if(fin == true || debut == true) {
    		Alert alert = new Alert(AlertType.WARNING, "Este Hospital está ocupado durante el período comprendido entre" + dated + " y " + datef, ButtonType.OK);
    		alert.showAndWait();
    	}
    	else {
    		String sql0 = "insert into location(logement, locataire, dateDebut, dateFin) values(?, ?, ?, ?)";
    		try {
				st = cnx.prepareStatement(sql0);
				st.setInt(1, logement);
				st.setInt(2, locataire);
				st.setDate(3, dateDebut);
				st.setDate(4, dateFin);
				st.executeUpdate();
				txt_adr.setText("");
				txt_CIN.setText("");
				txt_loyer.setText("");
				txt_nomPrenom.setText("");
				txt_periode.setText("");
				txt_region.setText("");
				txt_tele.setText("");
				txt_type.setText("");
				this.dateDebut.setValue(null);
				this.dateFin.setValue(null);
				imageLog.setImage(null);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Cita agregado con Éxito", ButtonType.OK);
	    		alert.showAndWait();
			} 
    		catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void searchLogement() {
    	String sql = "select adrL, loyer, nomType, nomRegion, image from logement, type, region where logement.type=type.idType and logement.region=region.idRegion and logement.idLogement='" + txt_searchLogementId.getText() + "'";
    	int nb = 0;
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			byte ByteImg[];
			Blob blob;
			if(result.next()) {
				txt_adr.setText(result.getString("adrL"));
				int loyer = result.getInt("loyer");
				txt_loyer.setText(String.valueOf(loyer));
				txt_type.setText(result.getString("nomType"));
				txt_region.setText(result.getString("nomregion"));
				blob = result.getBlob("image");
				ByteImg = blob.getBytes(1, (int) blob.length());
				Image img = new Image (new ByteArrayInputStream(ByteImg), imageLog.getFitWidth(), imageLog.getFitHeight(), true, true );
				imageLog.setImage(img);
				txt_searchLogementId.setText("");
				nb = 1;
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	if(nb == 0) {
    		Alert alert = new Alert(AlertType.ERROR, "No se encontro ningún Hospital identificado con 	" + txt_searchLogementId.getText() + "", ButtonType.OK );
    		alert.showAndWait();
    	}
    }

    @FXML
    void searchLocataire() {
    	String sql = "select nomprenomL, teleL, CIN from locataire where CIN = '" + txt_cinSearch.getText() + "'";
    	int nbr = 0;
    	try {
			st = cnx.prepareStatement(sql);
			result = st.executeQuery();
			if(result.next()) {
				txt_CIN.setText(result.getString("CIN"));
				txt_nomPrenom.setText(result.getString("nomprenomL"));
				txt_tele.setText(result.getString("teleL"));
				txt_cinSearch.setText("");
				nbr = 1;
			}
		} 
    	catch (SQLException e) {
			e.printStackTrace();
		}
    	if(nbr == 0) {
    		Alert alert = new Alert(AlertType.ERROR, "No se encontró ningún Doctor identicicado con CC " + txt_cinSearch.getText() + "", ButtonType.OK);
    		alert.showAndWait();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnexionMysql.connexionDB();
	}
}