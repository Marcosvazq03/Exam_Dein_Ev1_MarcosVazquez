package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class EjercicioExamControllerImagen implements Initializable{
    
    private EjercicioExamControllerTabla ejExamController; 
   
    @FXML
    private ImageView verImg;
    
    public void initialize(URL location, ResourceBundle resources) {
    	if (ejExamController!=null) {
    		if(ejExamController.getTbProducto().getSelectionModel().getSelectedItem().getImage() != null) {
		 		verImg.setImage(new Image(ejExamController.getTbProducto().getSelectionModel().getSelectedItem().getImage()));
		 	}
		}
    }
    
    public void setControlerExam(EjercicioExamControllerTabla ej) {
    	this.ejExamController= ej;
    }
    
}
