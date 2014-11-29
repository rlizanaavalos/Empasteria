package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controlador.BeanProveedor;
public class CrudProv {
	
	private Connection con;

	public CrudProv() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}
	public boolean existeRut (BeanProveedor cliente){
		try {
			String aux = "select * from proveedor where rut = '"+cliente.getRut()+"'";
			PreparedStatement preparedStatement = con.prepareStatement(aux);
			ResultSet rs = preparedStatement.executeQuery();
			if( rs.next() ){
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void addUser(BeanProveedor proveedor) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into proveedor(rut,nombre,tipo_insumos,telefono,direccion,correo) values (?,?,?,?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setString(1, proveedor.getRut());
			preparedStatement.setString(2, proveedor.getNombre());
			preparedStatement.setString(3, proveedor.getTipoInsumo());			
			preparedStatement.setString(4, proveedor.getTelefono());
			preparedStatement.setString(5, proveedor.getDireccion());			
			preparedStatement.setString(6, proveedor.getCorreo());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String nombreProveedor) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from proveedor where rut=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombreProveedor);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(BeanProveedor proveedor) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update proveedor set nombre=?,tipo_insumos=?,telefono=?,direccion=?,correo=?" +
							"where rut=?");
			
			// Parameters start with 1	
			
			preparedStatement.setString(1, proveedor.getNombre());
			preparedStatement.setString(2, proveedor.getTipoInsumo());			
			preparedStatement.setString(3, proveedor.getTelefono());
			preparedStatement.setString(4, proveedor.getDireccion());			
			preparedStatement.setString(5, proveedor.getCorreo());
			preparedStatement.setString(6, proveedor.getRut());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public BeanProveedor getUserByNombre(String nombreProveedor) {
		BeanProveedor proveedor = new BeanProveedor();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from proveedor where rut=?");
			preparedStatement.setString(1, nombreProveedor);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				proveedor.setRut(rs.getString("rut"));
				proveedor.setNombre(rs.getString("nombre"));
				proveedor.setTipoInsumo(rs.getString("tipo_insumos"));				
				proveedor.setTelefono(rs.getString("telefono"));
				proveedor.setDireccion(rs.getString("direccion"));				
				proveedor.setCorreo(rs.getString("correo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return proveedor;
	}
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from proveedor");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public List<BeanProveedor> getAllUsers(String searchRut,String searchNombre,String searchTipo_insumo,String searchTelefono,String searchDireccion,String searchCorreo,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanProveedor> proveedores = new ArrayList<BeanProveedor>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from proveedor where rut like '%" + searchRut + "%' and " + "nombre like '%" + searchNombre + "%' and tipo_insumos like '%" + searchTipo_insumo + "%'  and telefono like '%" + searchTelefono+  "%'and direccion like '%" + searchDireccion + "%' and correo like '%" + searchCorreo+  "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize ;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanProveedor proveedor = new BeanProveedor();
				proveedor.setRut(rs.getString("rut"));
				proveedor.setNombre(rs.getString("nombre"));
				proveedor.setTipoInsumo(rs.getString("tipo_insumos"));		
				proveedor.setTelefono(rs.getString("telefono"));	
				proveedor.setDireccion(rs.getString("direccion"));		
				proveedor.setCorreo(rs.getString("correo"));	
				proveedores.add(proveedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proveedores;
	}
	

}
