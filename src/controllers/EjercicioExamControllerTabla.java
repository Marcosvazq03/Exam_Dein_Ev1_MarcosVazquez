package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import dao.ProductoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Producto;

public class EjercicioExamControllerTabla implements Initializable{
    
	@FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;
	
	@FXML
    private CheckBox cbDisponible;
	
	@FXML
    private ContextMenu ctMenu;

    @FXML
    private ImageView imageSelected;

    @FXML
    private TableColumn<Producto, String> lsDisponible;

    @FXML
    private TableColumn<Producto, String> lsCodigo;

    @FXML
    private TableColumn<Producto, String> lsNombre;

    @FXML
    private TableColumn<Producto, Double> lsPrecio;

    @FXML
    private TableView<Producto> tbProducto;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;
	
	private ProductoDao aD;
	
	public ProductoDao getaD() {
		return aD;
	}

	private ObservableList<Producto> o1;
	
	private InputStream imageBinary = null;
	
 	public TableView<Producto> getTbProducto() {
		return tbProducto;
	}

 	/**
 	 * Crear Producto
 	 * @param codigo
 	 * @param nombre
 	 * @param precio
 	 * @param disponible
 	 * @param imagen
 	 * @return
 	 */
	public boolean crearProducto(String codigo, String nombre, Double precio, int disponible, InputStream imagen) {
 		Producto p = new Producto(codigo, nombre, precio, disponible, imagen);
 		boolean esta=false;
 		if (o1 !=null) {
 			//Comprobar si existe en la tabla
 			if (o1.contains(p)) {
 				esta=true;
 			}
 		}
 		if (esta) {
 			return false;
 		}else {
 			//Crear y añadirla a la tabla
 			aD.insertProducto(codigo, nombre, precio, disponible, imagen);		
 			o1.add(p);
 			
 			return true;
 		}
    }
 	
     /**
      * Modificar Producto
      * @param codigo
      * @param nombre
      * @param precio
      * @param disponible
      * @param imagen
      */
	 public void modificarProducto(String codigo, String nombre, Double precio, int disponible, InputStream imagen) {
	 	//Modificar objeto de la tabla
	 	Producto p = new Producto(codigo, nombre, precio, disponible, imagen);
	 	for (int i = 0; i < o1.size(); i++) {
			if (tbProducto.getSelectionModel().getSelectedItem()==o1.get(i)) {
				aD.modProducto(codigo, nombre, precio, disponible, imagen);
				o1.set(i, p);
			}
		}
	 }

	 /**
	  * Seleccionar imagen
	  * @return
	  */
     protected InputStream seleccionarImagen() {
     	InputStream imageBinary = null;
     	FileChooser fileChooser = new FileChooser();
     	Stage stage = new Stage();
     	fileChooser.setTitle("Seleccionar Imagen ");
     	ExtensionFilter jpgFilter = new ExtensionFilter("Imagen JPG (*.jpg)", "*.jpg");
     	fileChooser.getExtensionFilters().add(jpgFilter);
     	File imageFile = fileChooser.showOpenDialog(stage);
     	if(imageFile != null) {
     		try {
     			Image img = new Image(imageFile.toURI().toString());
 				imageBinary = new FileInputStream(imageFile);
 				imageSelected.setVisible(true);
 	    		imageSelected.setImage(img);
 			} catch (FileNotFoundException e) {
 				e.printStackTrace();
 			}
     	}else {
    		imageSelected.setVisible(false);
    		imageSelected.setImage(null);
     	}
     	return imageBinary;
     }
     
     /**
      * Mostrar datos del Producto seleccionado
      * @param event
      */
     @FXML
     void click_Producto(MouseEvent event) {
    	//Comprobar que hay seleccionado un objeto en la tabla
      	if (!tbProducto.getSelectionModel().isEmpty()) {
      		//Modificar objeto de la tabla
  			Producto p = (Producto) tbProducto.getSelectionModel().getSelectedItem();
  			txtCodigo.setText(p.getCodigo());
  			txtNombre.setText(p.getNombre());
  			txtPrecio.setText(p.getPrecio()+"");
  			imageSelected.setImage(null);
  			cbDisponible.setSelected(false);
  			if (p.getImage()!=null) {
				imageSelected.setImage(new Image(aD.cargarImg(p.getCodigo())));
				imageBinary = p.getImage();
			}
  			if (p.getDisponible()==1) {
				cbDisponible.setSelected(true);
			}
  			btnActualizar.setDisable(false);
  			txtCodigo.setDisable(true);
  			btnCrear.setDisable(true);
  		}
    	 
     }
     
     /**
      * Actualizar datos del producto
      * @param event
      */
     @FXML
     void actualizar(ActionEvent event) {
    	//Comprobar que hay seleccionado un objeto en la tabla
     	if (tbProducto.getSelectionModel().isEmpty()) {
     		//Ventana error
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setTitle("Error");
             alert.setContentText("No has seleccionado ningun Producto de la tabla!");
             alert.showAndWait();
 		}else {
 			//Modificar objeto de la tabla
 			//Comprobar que en un numero
 	     	boolean esNumPrecio=true;
 	     	
 	     	//Alerta introducir todos los datos
 	 		if (!txtPrecio.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) {
 	     		esNumPrecio=false;
 	 		}

 	 		if (txtNombre.getText().toString().equals("") || txtCodigo.getText().toString().equals("") 
 	 				|| txtPrecio.getText().equals("") || esNumPrecio==false || txtCodigo.getText().length()!=5) {
 	     		String err = "";
 	 			if (txtNombre.getText().toString().equals("") || txtCodigo.getText().toString().equals("") 
 	 					|| txtPrecio.getText().equals("")) {
 	 				err="Rellenar todos los campos\n";
 	 			}
 	 			String err2 = "";
 	 			if (esNumPrecio==false) {
 	 				err2="Los campos no tienen el correcto formato/n";
 	 			}
 	 			String err3 = "";
 	 			if (txtCodigo.getText().length()!=5) {
 	 				err2="El codigo tiene que tener 5 caracteres";
 	 			}
 	     		
 	     		Alert alert = new Alert(Alert.AlertType.ERROR);
 	             alert.setTitle("TUS DATOS");
 	             alert.setHeaderText(null);
 	             alert.setContentText(err+err2+err3);
 	             alert.showAndWait();
 	 		}else {
 	 			int disp=0;
 	 			if (cbDisponible.isSelected()) {
 					disp=1;
 				}
 	 			modificarProducto(txtCodigo.getText().toString(), txtNombre.getText().toString(), Double.parseDouble(txtPrecio.getText().toString()), disp, imageBinary);
 				//Ventana de informacion
 	        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
 	            alert.setTitle("Info");
 	            alert.setHeaderText(null);
 	            alert.setContentText("Producto añadido correctamente");
 	            alert.showAndWait();
 	          
 	            limpiar(event);
 	            
 	 		}
 		}
     }

     /**
      * Ventana de ayuda, acerca de...
      * @param event
      */
     @FXML
     void ayuda(ActionEvent event) {
    	//Ventana de informacion
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText("Gestion de productos 1.0\nAutor: Marcos Vazquez");
        alert.showAndWait();
     }

     /**
      * Borrar Producto
      * @param event
      */
     @FXML
     void borrar(ActionEvent event) {
    	//Comprobar que hay seleccionado una persona en la tabla
     	if (tbProducto.getSelectionModel().isEmpty()) {
     		//Ventana error
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setTitle("Error");
             alert.setContentText("No has seleccionado ningun Producto de la tabla!");
             alert.showAndWait();
 		}else {
 			//Eliminar objeto de la tabla
 	    	for (int i = 0; i < o1.size(); i++) {
 				if (tbProducto.getSelectionModel().getSelectedItem()==o1.get(i)) {
 					aD.elimProducto(tbProducto.getSelectionModel().getSelectedItem().getCodigo());
 					o1.remove(i);
 				}
 			}
 			
 			//Ventana de informacion
 	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
 	        alert.setTitle("Info");
 	        alert.setHeaderText(null);
 	        alert.setContentText("Producto eliminado correctamente");
 	        alert.showAndWait();
 	        
 	        limpiar(event);

 		}
     }

     /**
      * Crear producto
      * @param event
      */
     @FXML
     void crear(ActionEvent event) {
    	//Comprobar que en un numero
     	boolean esNumPrecio=true;
     	
     	//Alerta introducir todos los datos
 		if (!txtPrecio.getText().matches("^-?[0-9]+([\\.,][0-9]+)?$")) {
     		esNumPrecio=false;
 		}

 		if (txtNombre.getText().toString().equals("") || txtCodigo.getText().toString().equals("") 
 				|| txtPrecio.getText().equals("") || esNumPrecio==false || txtCodigo.getText().length()!=5) {
     		String err = "";
 			if (txtNombre.getText().toString().equals("") || txtCodigo.getText().toString().equals("") 
 					|| txtPrecio.getText().equals("")) {
 				err="Rellenar todos los campos\n";
 			}
 			String err2 = "";
 			if (esNumPrecio==false) {
 				err2="Los campos no tienen el correcto formato/n";
 			}
 			String err3 = "";
 			if (txtCodigo.getText().length()!=5) {
 				err2="El codigo tiene que tener 5 caracteres";
 			}
     		
     		Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("TUS DATOS");
             alert.setHeaderText(null);
             alert.setContentText(err+err2+err3);
             alert.showAndWait();
 		}else {
 			int disp=0;
 			if (cbDisponible.isSelected()) {
				disp=1;
			}
 			if (crearProducto(txtCodigo.getText().toString(), txtNombre.getText().toString(), Double.parseDouble(txtPrecio.getText().toString()), disp, imageBinary)) {
 				//Ventana de informacion
 	        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
 	            alert.setTitle("Info");
 	            alert.setHeaderText(null);
 	            alert.setContentText("Producto añadido correctamente");
 	            alert.showAndWait();
 	          
 	            limpiar(event);
 			}else {
 				//Alerta persona existe en la tabla
 				Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("TUS DATOS");
                 alert.setHeaderText(null);
                 alert.setContentText("Producto ya existe!");
                 alert.showAndWait();
 			}
 		}
     }

     /**
      * Limpiar datos
      * @param event
      */
     @FXML
     void limpiar(ActionEvent event) {
    	 txtCodigo.setText("");
    	 txtNombre.setText("");
    	 txtPrecio.setText("");
    	 cbDisponible.setSelected(false);
    	 imageSelected.setImage(null);
    	 btnCrear.setDisable(false);
         btnActualizar.setDisable(true);
         txtCodigo.setDisable(false);
    	 
     }

     /**
      * Ver imagen en una ventana
      * @param event
      */
     @FXML
     void verImagen(ActionEvent event) {
    	//Comprobar que hay seleccionado un objeto en la tabla
     	if (tbProducto.getSelectionModel().isEmpty()) {
     		//Ventana error
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText(null);
             alert.setTitle("Error");
             alert.setContentText("No has seleccionado ningun producto de la tabla!");
             alert.showAndWait();
 		}else {
 			try {
 				if (tbProducto.getSelectionModel().getSelectedItem().getImage()==null) {
 					//Ventana error
 		             Alert alert = new Alert(Alert.AlertType.ERROR);
 		             alert.setHeaderText(null);
 		             alert.setTitle("Error");
 		             alert.setContentText("El producto seleccionado no tiene imagen!");
 		             alert.showAndWait();
				}else {
					//Abrir ventana modal
	 				FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/EjercicioExamfxmlImagen.fxml"));
	 		    	Stage stage = new Stage();
	 		    	EjercicioExamControllerImagen ejLC = new EjercicioExamControllerImagen();
	 		    	loader.setController(ejLC);
	 		    	ejLC.setControlerExam(this);
	 		    	Parent root= loader.load();
	 		        stage.setScene(new Scene(root,300,300));
	 		        stage.setResizable(false);
	 		        stage.initOwner(this.tbProducto.getScene().getWindow());
	 		        stage.setTitle("Imagen");
	 		        stage.getIcons().add(new Image(getClass().getResource("/img/carrito.png").toString()));
	 		        stage.initModality(Modality.APPLICATION_MODAL);
	 		        stage.showAndWait();
				}
 	    	}catch (Exception e) {
 	    		System.out.println(e.getMessage());
 			}
 		}
     }
     
     /**
      * Seleccionar imagen
      * @param event
      */
	 @FXML
	 void click_select_imagen(ActionEvent event) {
		imageBinary = seleccionarImagen();
	 }
    
	public void initialize(URL location, ResourceBundle resources) {
		//Valores de la columna de la tabla
		lsCodigo.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
		lsNombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
		lsPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));
		lsDisponible.setCellValueFactory(new PropertyValueFactory<Producto, String>("disponible"));
		
		o1 = FXCollections.observableArrayList();
		
		aD = new ProductoDao();
		
		o1.setAll(aD.cargarProductos());
		
		tbProducto.setItems(o1);
	}
	
}
