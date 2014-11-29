package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controlador.BeanInventario;
public class CrudInv {
	
	private Connection con;

	public CrudInv() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}

	public void addUser(BeanInventario inventario) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into inventario(nombre,grupo_material,cantidad,costeporunidad) values (?, ?, ?, ? )");
			preparedStatement.setString(1, inventario.getNombre());
			preparedStatement.setString(2, inventario.getGrupo());
			preparedStatement.setString(3, inventario.getCantidad());			
			preparedStatement.setString(4, inventario.getCosto());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String nombreInventario) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from inventario where codigo_material=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombreInventario);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(BeanInventario inventario) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update inventario set nombre=?, grupo_material=?, cantidad=?,costeporunidad=?" +
							"where codigo_material=?");
			
			// Parameters start with 1	
			
			preparedStatement.setString(1, inventario.getNombre());
			preparedStatement.setString(2, inventario.getGrupo());
			preparedStatement.setString(3, inventario.getCantidad());			
			preparedStatement.setString(4, inventario.getCosto());
			preparedStatement.setString(5, inventario.getCodigo());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public BeanInventario getUserByNombre(String nombreInventario) {
		BeanInventario inventario = new BeanInventario();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from inventario where codigo_material=?");
			preparedStatement.setString(1, nombreInventario);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				inventario.setCodigo(rs.getString("codigo_material"));
				inventario.setNombre(rs.getString("nombre"));
				inventario.setGrupo(rs.getString("grupo_material"));
				inventario.setCantidad(rs.getString("cantidad"));				
				inventario.setCosto(rs.getString("costeporunidad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inventario;
	}
	
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from inventario");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<BeanInventario> getAllUsers(String searchCodigo,String searchNombre, String searchGrupo, String searchCantidad,String searchCosto,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanInventario> inventarios = new ArrayList<BeanInventario>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from inventario where codigo_material like '%" + searchCodigo + "%' and " + "nombre like '%" + searchNombre + "%' and grupo_material like '%" + searchGrupo + "%' and cantidad like '%" + searchCantidad + "%' and costeporunidad like '%" + searchCosto+  "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanInventario inventario = new BeanInventario();
				inventario.setCodigo(rs.getString("codigo_material"));
				inventario.setNombre(rs.getString("nombre"));
				inventario.setGrupo(rs.getString("grupo_material"));
				inventario.setCantidad(rs.getString("cantidad"));		
				inventario.setCosto(rs.getString("costeporunidad"));	
				inventarios.add(inventario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inventarios;
	}
	public void restarinventario(String nombre){
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update inventario set cantidad=cantidad-1 where codigo_material=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombre);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sumarinventario(String nombre){
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update inventario set cantidad=cantidad+1 where nombre=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombre);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean existeinventario(String nombre){
		try{
			PreparedStatement preparedStatement = con.prepareStatement("select * from inventario where codigo_material=?");
			preparedStatement.setString(1, nombre);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int cantidad=rs.getInt("cantidad");
				String nombreee=rs.getString("nombre");
				if (cantidad > 0 && nombreee!=null){
					return true;
				}
			}
		return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void restarinventariopagina(String nombre){
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update inventario set cantidad=cantidad-? where nombre=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, "hojas");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sumarinventariopagina(String nombre){
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update inventario set cantidad=cantidad+? where nombre=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombre);
			preparedStatement.setString(2, "hojas");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean existepagina(String nombre){
		try{
			PreparedStatement preparedStatement = con.prepareStatement("select * from inventario where nombre=?");
			preparedStatement.setString(1, "hojas");
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				int cantidad=rs.getInt("cantidad");
				cantidad = cantidad - Integer.parseInt(nombre);
				if (cantidad > 0 ){
					return true;
				}
			}
		return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public int obtenerPrecio(String nombre){
		int precio=0;
		try{
			PreparedStatement preparedStatement = con.prepareStatement("select * from inventario where codigo_material	=?");
			preparedStatement.setString(1, nombre);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				precio=rs.getInt("costeporunidad");
				return precio;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return precio;
		}
		return precio;
	}
	public int obtenerPrecioImpresion(String nombre){
		String precio="0";
		try{
			PreparedStatement preparedStatement = con.prepareStatement("select * from impresion where id=?");
			preparedStatement.setString(1, nombre);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				precio=rs.getString("precio");
				return Integer.parseInt(precio);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return Integer.parseInt(precio);
		}
		return Integer.parseInt(precio);
	}
	public int obtenerPreciohojas(String nombre, String cantidaddehojas){
		int precio=0;
		try{
			PreparedStatement preparedStatement = con.prepareStatement("select * from inventario where nombre=?");
			preparedStatement.setString(1, nombre);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				precio=rs.getInt("costeporunidad")*Integer.parseInt(cantidaddehojas);
				return precio;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return precio;
		}
		return precio;
	}
}
