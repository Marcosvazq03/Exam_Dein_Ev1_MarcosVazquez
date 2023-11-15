package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EjercicioExamControllerImagen implements Initializable{
    
    private EjercicioExamControllerTabla ejExamController; 
   
    @FXML
    private ImageView verImg;
    
    public void initialize(URL location, ResourceBundle resources) {
    	//Carga la imagen
    	if (ejExamController!=null) {
    		if(ejExamController.getTbProducto().getSelectionModel().getSelectedItem().getImage() != null) {
		 		verImg.setImage(new Image(ejExamController.getaD().cargarImg(ejExamController.getTbProducto().getSelectionModel().getSelectedItem().getCodigo())));
		 	}
		}
    }
    
    public void setControlerExam(EjercicioExamControllerTabla ej) {
    	this.ejExamController= ej;
    }
    
}
