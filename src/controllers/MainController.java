package controllers;

import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class MainController implements Initializable {


    @FXML
    private Button btn_seconnecter;

    @FXML
    private Button btn_sinscrire;

    @FXML
    private VBox VBox;

    private Parent fxml;
    @FXML
    void openSignIn() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
		t.setToX(VBox.getLayoutX()*10);
		t.play();
		t.setOnFinished(e -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} 
			
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    }

    @FXML
    void openSignUp() {
    	TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
    	t.setToX(5);
    	t.play();
    	t.setOnFinished(e -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/interfaces/SignUp.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} 
			
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    }
	
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), VBox);
		t.setToX(VBox.getLayoutX()*10);
		t.play();
		t.setOnFinished(e -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
				VBox.getChildren().removeAll();
				VBox.getChildren().setAll(fxml);
			} 
			
			catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
}