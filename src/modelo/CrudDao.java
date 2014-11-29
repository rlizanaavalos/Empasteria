package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controlador.BeanCliente;
import controlador.Cascade;
public class CrudDao {
	
	private Connection con;

	public CrudDao() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}
	public boolean existeRut (BeanCliente cliente){
		try {
			String aux = "select * from cliente where rut = '"+cliente.getRut()+"'";
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

	public void addUser(BeanCliente cliente) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into cliente(nombre_cliente,rut,direccion,comuna,ciudad,region,correo,telefono) values (?,?, ?, ?,?,?,?,? )");
			// Parameters start with 1
			preparedStatement.setString(1, cliente.getNombre());
			preparedStatement.setString(2, cliente.getRut());
			preparedStatement.setString(3, cliente.getDireccion());			
			preparedStatement.setString(4, cliente.getComuna());
			preparedStatement.setString(5, cliente.getCiudad());
			preparedStatement.setString(6, cliente.getRegion());
			preparedStatement.setString(7, cliente.getCorreo());			
			preparedStatement.setString(8, cliente.getTelefono());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(String nombreCliente) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from cliente where nombre_cliente=?");
			// Parameters start with 1
			preparedStatement.setString(1, nombreCliente);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(BeanCliente cliente) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update cliente set rut=?,direccion=?,comuna=?,ciudad=?,region=?,correo=?,telefono=?" +
							"where nombre_cliente=?");
			
			// Parameters start with 1	
			
			preparedStatement.setString(1, cliente.getRut());
			preparedStatement.setString(2, cliente.getDireccion());			
			preparedStatement.setString(3, cliente.getComuna());
			preparedStatement.setString(4, cliente.getCiudad());
			preparedStatement.setString(5, cliente.getRegion());
			preparedStatement.setString(6, cliente.getCorreo());			
			preparedStatement.setString(7, cliente.getTelefono());
			preparedStatement.setString(8, cliente.getNombre());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public BeanCliente getUserByNombre(String nombreCliente) {
		BeanCliente cliente = new BeanCliente();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from cliente where nombre_cliente=?");
			preparedStatement.setString(1, nombreCliente);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				cliente.setNombre(rs.getString("nombre_cliente"));
				cliente.setRut(rs.getString("rut"));
				cliente.setDireccion(rs.getString("direccion"));				
				cliente.setComuna(rs.getString("comuna"));
				cliente.setCiudad(rs.getString("ciudad"));
				cliente.setRegion(rs.getString("region"));
				cliente.setCorreo(rs.getString("correo"));				
				cliente.setTelefono(rs.getString("telefono"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from cliente");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public List<BeanCliente> getAllUsers(String searchNombre,String searchRut,String searchDireccion,String searchComuna,String searchCiudad,String searchRegion,String searchCorreo,String searchTelefono,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanCliente> clientes = new ArrayList<BeanCliente>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";		
		if(searchRegion.equals(""))
		{
			
			query="select * from cliente where nombre_cliente like '%" + searchNombre + "%' and rut like '%" + searchRut + "%' and direccion like '%" + searchDireccion + "%' and comuna like '%" + searchComuna + "%' and ciudad like '%" + searchCiudad + "%' and region like '%" + searchRegion + "%' and correo like '%" + searchCorreo + "%' and telefono like '%" + searchTelefono + "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;
		}
		else
			query="select * from cliente where nombre_cliente like '%" + searchNombre + "%' and rut like '%" + searchRut + "%' and direccion like '%" + searchDireccion + "%' and comuna like '%" + searchComuna + "%' and ciudad like '%" + searchCiudad + "%' and region = '" + searchRegion + "' and correo like '%" + searchCorreo + "%' and telefono like '%" + searchTelefono + "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanCliente cliente = new BeanCliente();
				cliente.setNombre(rs.getString("nombre_cliente"));
				cliente.setRut(rs.getString("rut"));
				cliente.setDireccion(rs.getString("direccion"));				
				cliente.setComuna(rs.getString("comuna"));
				cliente.setCiudad(rs.getString("ciudad"));
				cliente.setRegion(rs.getString("region"));
				cliente.setCorreo(rs.getString("correo"));				
				cliente.setTelefono(rs.getString("telefono"));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clientes;
	}
	//listado de regiones
	
	public List<Cascade> getRegion() {
		List<Cascade> regiones = new ArrayList<Cascade>();
		String query = "";
	    query="select * from region";

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Cascade region = new Cascade("","");
				region.setValue(rs.getString("id"));
				region.setDisplayText(rs.getString("nombre"));
				regiones.add(region);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return regiones;
	} 
	public List<Cascade> getComuna(String id) {
		List<Cascade> regiones = new ArrayList<Cascade>();
		String query = "";
		if(id.equals("0"))
		{
			query="select * from comuna";
		}
		else
		{
			query="select * from comuna where region_id =" +id;
		}
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Cascade region = new Cascade("","");
				region.setValue(rs.getString("id"));
				region.setDisplayText(rs.getString("nombre"));
				regiones.add(region);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return regiones;
	} 
}
