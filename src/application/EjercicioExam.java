package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EjercicioExam extends Application{

	@Override
	public void start(Stage primaryStage) {
        try {
        	//Inicia la aplicacion
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/EjercicioExamfxmlTabla.fxml"));
            Scene scene = new Scene(root,700,500);
            scene.getStylesheets().add(getClass() .getResource("/css/estilos.css").toExternalForm());
            primaryStage.setTitle("Productos");
            primaryStage.getIcons().add(new Image(getClass().getResource("/img/carrito.png").toString()));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
        
    }
	
}
