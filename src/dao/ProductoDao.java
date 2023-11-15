package dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;

public class ProductoDao {

	private ConexionBD conexion;
	
	/**
	 * Insertar Producto
	 * @param codigo
	 * @param nombre
	 * @param precio
	 * @param disponible
	 * @param imagen
	 */
	public void insertProducto(String codigo, String nombre, Double precio, int disponible, InputStream imagen) {
    	//Inserta objeto en la BBDD
    	try {
            conexion = new ConexionBD();        	
            
			String consulta = "INSERT INTO productos(codigo, nombre, precio, disponible, imagen) VALUES(?,?,?,?,?)";
			
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);  
        	
        	pstmt.setString(1, codigo);
        	pstmt.setString(2, nombre);
        	pstmt.setDouble(3, precio);
        	pstmt.setInt(4, disponible);
        	pstmt.setBinaryStream(5, imagen);
        	
			pstmt.execute();
			
	        conexion.CloseConexion();
	    } catch (SQLException e) {	    	
	    	System.out.println(e.getMessage());
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
	public void modProducto(String codigo, String nombre, Double precio, int disponible, InputStream imagen) {
    	//Modifica objeto en la BBDD
    	try {
	        conexion = new ConexionBD();        	
        	String consulta = "UPDATE examen1.productos SET nombre = ?, precio = ?, disponible = ?, imagen = ? WHERE codigo = ?";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);  
        	pstmt.setString(1, nombre);
        	pstmt.setDouble(2, precio);
        	pstmt.setInt(3, disponible);
        	pstmt.setBinaryStream(4, imagen);
        	pstmt.setString(5, codigo);
			pstmt.execute();
	        conexion.CloseConexion();
	    } catch (SQLException e) {	    	
	    	System.out.println(e.getMessage());
	    }
    }
	
	/**
	 * Eliminar Producto
	 * @param codigo
	 */
	public void elimProducto(String codigo) {
    	//Eliminar objeto en la BBDD
    	try {
            conexion = new ConexionBD();        	
            String consulta = "DELETE FROM examen1.productos WHERE codigo = '"+codigo+"'";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);  
			pstmt.execute();
	        conexion.CloseConexion();
	    } catch (SQLException e) {	    	
	    	System.out.println(e.getMessage());
	    }
    }
	
	/**
	 * Cargar imagen
	 * @param codigo
	 * @return
	 */
	public InputStream cargarImg(String codigo)  {
		InputStream image = null;
		try {
            conexion = new ConexionBD();        	
        	String consulta = "select imagen from productos WHERE codigo = '"+codigo+"'";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);      
        	ResultSet rs = pstmt.executeQuery();   
        	rs.next();
        	image = rs.getBinaryStream("imagen");          
			rs.close();       
	        conexion.CloseConexion();
	    } catch (SQLException e) {	    	
	    	e.printStackTrace();
	    }    
        return image;    
    }
	
	/**
	 * Cargar Productos
	 * @return
	 */
	public ObservableList<Producto> cargarProductos()  {
    	
    	ObservableList<Producto> producto = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();        	
        	String consulta = "select * from productos";
        	PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);      
        	ResultSet rs = pstmt.executeQuery();   
			 while (rs.next()) {
				 String codigo = rs.getString("codigo");
	             String nombre = rs.getString("nombre");
	             Double precio = rs.getDouble("precio");
	             int disponible = rs.getInt("disponible");
	             InputStream image = rs.getBinaryStream("imagen");
	         
                 Producto a = new Producto(codigo, nombre, precio, disponible, image);
                 producto.add(a);
			 }             
			rs.close();       
	        conexion.CloseConexion();
	    } catch (SQLException e) {	    	
	    	e.printStackTrace();
	    }    
        return producto;    
    }
	
}
