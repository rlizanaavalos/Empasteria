package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;




import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import controlador.BeanCompra;
import controlador.BeanEmpleadoPedido;
import controlador.BeanProveedorCompra;

public class CrudCompra {
	private Connection con;

	public CrudCompra() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}
	
	public void addCompra(BeanCompra compra) {
		try {
			Calendar fecha = Calendar.getInstance();
			PreparedStatement preparedStatement = con.prepareStatement("insert into compra(nombre_empleado, nombre_proveedor, fecha, medio_pago,total) values (?,?,?,?,? )");
			// Parameters start with 1
			preparedStatement.setString(1, compra.getNombre_empleado());
			preparedStatement.setString(2, compra.getNombre_proveedor());
			preparedStatement.setString(3, compra.getFecha());			
			preparedStatement.setString(4, compra.getMedio_pago());
			preparedStatement.setString(5, compra.getTotal());
			//preparedStatement.setString(12, fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH)+1) + "-" + fecha.get(Calendar.DATE));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCompra(String codigo_compra) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from compra where codigo_compra=?");
			// Parameters start with 1
			preparedStatement.setString(1, codigo_compra);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCompra(BeanCompra compra) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update compra set nombre_empleado=?,nombre_proveedor=?,fecha=?,medio_pago=?,total=?" +
							"where codigo_compra=?");
			
			preparedStatement.setString(1, compra.getNombre_empleado());
			preparedStatement.setString(2, compra.getNombre_proveedor());
			preparedStatement.setString(3, compra.getFecha());			
			preparedStatement.setString(4, compra.getMedio_pago());
			preparedStatement.setString(5, compra.getTotal());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public BeanCompra getUserByNombre(String codigo_compra) {
		BeanCompra compra = new BeanCompra();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from compra where codigo_compra=?");
			preparedStatement.setString(1, codigo_compra);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				compra.setId_compra(rs.getString("codigo_compra"));
				compra.setNombre_empleado(rs.getString("nombre_empleado"));
				compra.setNombre_proveedor(rs.getString("nombre_proveedor"));				
				compra.setFecha(rs.getString("fecha"));
				compra.setMedio_pago(rs.getString("medio_pago"));
				compra.setTotal(rs.getString("total"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return compra;
	}
	
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from compra");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<BeanCompra> getAllUsers(String searchId, String searchEmpleado,String searchProveedor,String searchFecha,String searchMedio_pago,String searchTotal,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanCompra> compras = new ArrayList<BeanCompra>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from compra where codigo_compra like '%" + searchId + "%' and nombre_empleado like '%" + searchEmpleado + "%' and nombre_proveedor like '%" + searchProveedor + "%' and fecha like '%" + searchFecha + "%' and medio_pago like '%" + searchMedio_pago + "%' and total like '%" + searchTotal + "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanCompra compra = new BeanCompra();
				compra.setId_compra(rs.getString("codigo_compra"));
				compra.setNombre_empleado(rs.getString("nombre_empleado"));
				compra.setNombre_proveedor(rs.getString("nombre_proveedor"));				
				compra.setFecha(rs.getString("fecha"));
				compra.setMedio_pago(rs.getString("medio_pago"));
				compra.setTotal(rs.getString("total"));
				compras.add(compra);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compras;
	}
	
public  List<BeanEmpleadoPedido> getEmpleadoPedido(){
		
		List<BeanEmpleadoPedido> clientesPedido = new ArrayList<BeanEmpleadoPedido>();
		
		BeanEmpleadoPedido cli = null;
		String consulta = null;
		consulta = "select * from login where rol = 'Operador'";
		
		try{
			
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(consulta);
			
			while(rs.next()){
				cli = new BeanEmpleadoPedido("","");
				cli.setDisplayText(rs.getString("user"));
				cli.setValue(rs.getString("id"));
				clientesPedido.add(cli);
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return clientesPedido;
	}

public  List<BeanProveedorCompra> getProveedorCompra(){
	
	List<BeanProveedorCompra> clientesPedido = new ArrayList<BeanProveedorCompra>();
	
	BeanProveedorCompra cli = null;
	String consulta = null;
	consulta = "select * from proveedor";
	
	try{
		
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(consulta);
		
		while(rs.next()){
			cli = new BeanProveedorCompra("","");
			cli.setDisplayText(rs.getString("nombre"));
			cli.setValue(rs.getString("rut"));
			clientesPedido.add(cli);
		}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	
	return clientesPedido;
}

}
