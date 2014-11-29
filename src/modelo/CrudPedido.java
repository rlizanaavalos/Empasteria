
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

import controlador.BeanClientePedido;
import controlador.BeanEmpleadoPedido;
import controlador.BeanImpresion;
import controlador.BeanInventarioPedido;
import controlador.BeanPedido;
//import controlador.cliente;

public class CrudPedido {

	private Connection con;

	public CrudPedido() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}
	
	public void addPedido(BeanPedido pedido) {
		try {
			Calendar fecha = Calendar.getInstance();
			PreparedStatement preparedStatement = con.prepareStatement("insert into pedido(nombre_cliente, nombre_empleado, tipo_impresion, color, tipo_material,tamano_tapa,cantidad_pagina,precio,pago,nombre_empastador,estado,fecha) values (?, ?, ?,?,?,?,?,?,?,?,?,? )");
			// Parameters start with 1
			preparedStatement.setString(1, pedido.getNombreCliente());
			preparedStatement.setString(2, pedido.getNombreEmpleado());
			preparedStatement.setString(3, pedido.getTipoImpresion());			
			preparedStatement.setString(4, pedido.getColor());
			preparedStatement.setString(5, pedido.getTipoMaterial());
			preparedStatement.setString(6, pedido.getTamanoTapa());			
			preparedStatement.setString(7, pedido.getCantidadPagina());
			preparedStatement.setString(8, pedido.getPrecio());
			preparedStatement.setString(9, pedido.getPago());
			preparedStatement.setString(10, pedido.getNombreEmpastador());
			preparedStatement.setString(11, pedido.getEstado());		
			preparedStatement.setString(12, fecha.get(Calendar.YEAR) + "-" + (fecha.get(Calendar.MONTH)+1) + "-" + fecha.get(Calendar.DATE));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePedido(String idPedido) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from pedido where id_pedido=?");
			// Parameters start with 1
			preparedStatement.setString(1, idPedido);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePedido(BeanPedido pedido) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update pedido set nombre_cliente=?,nombre_empleado=?,tipo_impresion=?,color=?,tipo_material=?,tamano_tapa=?,cantidad_pagina=?,precio=?,pago=?,nombre_empastador=?,estado=?" +
							"where id_pedido=?");
			
			preparedStatement.setString(1, pedido.getNombreCliente());
			preparedStatement.setString(2, pedido.getNombreEmpleado());			
			preparedStatement.setString(3, pedido.getTipoImpresion());		
			preparedStatement.setString(4, pedido.getColor());
			preparedStatement.setString(5, pedido.getTipoMaterial());
			preparedStatement.setString(6, pedido.getTamanoTapa());			
			preparedStatement.setString(7, pedido.getCantidadPagina());
			preparedStatement.setString(8, pedido.getPrecio());
			preparedStatement.setString(9, pedido.getPago());
			preparedStatement.setString(10, pedido.getNombreEmpastador());
			preparedStatement.setString(11, pedido.getEstado());
			preparedStatement.setString(12, pedido.getIdPedido());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public BeanPedido getUserByNombre(String idPedido) {
		BeanPedido pedido = new BeanPedido();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from pedido where id_pedido=?");
			preparedStatement.setString(1, idPedido);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				pedido.setIdPedido(rs.getString("id_pedido"));
				pedido.setNombreCliente(rs.getString("nombre_cliente"));
				pedido.setNombreEmpleado(rs.getString("nombre_empleado"));				
				pedido.setTipoImpresion(rs.getString("tipo_impresion"));
				pedido.setColor(rs.getString("color"));
				pedido.setTipoMaterial(rs.getString("tipo_material"));
				pedido.setTamanoTapa(rs.getString("tamano_tapa"));				
				pedido.setCantidadPagina(rs.getString("cantidad_pagina"));
				pedido.setPrecio(rs.getString("precio"));
				pedido.setPago(rs.getString("pago"));
				pedido.setNombreEmpastador(rs.getString("nombre_empastador"));
				pedido.setEstado(rs.getString("estado"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pedido;
	}
	
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from pedido");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public List<BeanPedido> getAllUsers(String searchId, String searchCliente,String searchEmpleado,String searchImpresion,String searchColor,String searchMaterial,String searchTapa,String searchPagina,String searchPrecio,String searchPago,String searchEmpastador, String searchEstado,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanPedido> pedidos = new ArrayList<BeanPedido>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from pedido where id_pedido like '%" + searchId + "%' and nombre_cliente like '%" + searchCliente + "%' and nombre_empleado like '%" + searchEmpleado + "%' and tipo_impresion like '%" + searchImpresion + "%' and color like '%" + searchColor + "%' and tipo_material like '%" + searchMaterial + "%' and tamano_tapa like '%" + searchTapa + "%' and cantidad_pagina like '%" + searchPagina +"%' and precio like '%" + searchPrecio + "%' and pago like '%" + searchPago + "%' and nombre_empastador like '%" + searchEmpastador + "%' and estado like '%" + searchEstado + "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanPedido pedido = new BeanPedido();
				pedido.setIdPedido(rs.getString("id_pedido"));
				pedido.setNombreCliente(rs.getString("nombre_cliente"));
				pedido.setNombreEmpleado(rs.getString("nombre_empleado"));				
				pedido.setTipoImpresion(rs.getString("tipo_impresion"));
				pedido.setColor(rs.getString("color"));
				pedido.setTipoMaterial(rs.getString("tipo_material"));
				pedido.setTamanoTapa(rs.getString("tamano_tapa"));				
				pedido.setCantidadPagina(rs.getString("cantidad_pagina"));
				pedido.setPrecio(rs.getString("precio"));
				pedido.setPago(rs.getString("pago"));
				pedido.setNombreEmpastador(rs.getString("nombre_empastador"));
				pedido.setEstado(rs.getString("estado"));
				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pedidos;
	}
	
	public List<BeanPedido> getAllUsers2(int jtStartIndex, int jtPageSize, String sort) {
		List<BeanPedido> pedidos = new ArrayList<BeanPedido>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from pedido where estado = '1' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanPedido pedido = new BeanPedido();
				pedido.setIdPedido(rs.getString("id_pedido"));
				pedido.setNombreCliente(rs.getString("nombre_cliente"));
				pedido.setNombreEmpleado(rs.getString("nombre_empleado"));				
				pedido.setTipoImpresion(rs.getString("tipo_impresion"));
				pedido.setColor(rs.getString("color"));
				pedido.setTipoMaterial(rs.getString("tipo_material"));
				pedido.setTamanoTapa(rs.getString("tamano_tapa"));				
				pedido.setCantidadPagina(rs.getString("cantidad_pagina"));
				pedido.setPrecio(rs.getString("precio"));
				pedido.setPago(rs.getString("pago"));
				pedido.setNombreEmpastador(rs.getString("nombre_empastador"));
				pedido.setEstado(rs.getString("estado"));
				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pedidos;
	}
	
	public  List<BeanClientePedido> getClientePedido(){
		
		List<BeanClientePedido> clientesPedido = new ArrayList<BeanClientePedido>();
		
		BeanClientePedido cli = null;
		String consulta = null;
		consulta = "select * from cliente";
		
		try{
			
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(consulta);
			
			while(rs.next()){
				cli = new BeanClientePedido("","");
				cli.setDisplayText(rs.getString("nombre_cliente"));
				cli.setValue(rs.getString("rut"));
				clientesPedido.add(cli);
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return clientesPedido;
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
//retorna lista de impresion

public  List<BeanInventarioPedido> getTapa(){
	
	List<BeanInventarioPedido> clientesPedido = new ArrayList<BeanInventarioPedido>();
	
	BeanInventarioPedido cli = null;
	String consulta = null;
	consulta = "select * from inventario where grupo_material = '2'";
	
	try{
		
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(consulta);
		
		while(rs.next()){
			cli = new BeanInventarioPedido("","");
			cli.setDisplayText(rs.getString("nombre"));
			cli.setValue(rs.getString("codigo_material"));
			clientesPedido.add(cli);
		}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	
	return clientesPedido;
}
public  List<BeanImpresion> getImpresion(){
	
	List<BeanImpresion> clientesPedido = new ArrayList<BeanImpresion>();
	
	BeanImpresion cli = null;
	String consulta = null;
	consulta = "select * from impresion";
	
	try{
		
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(consulta);
		
		while(rs.next()){
			cli = new BeanImpresion("","");
			cli.setDisplayText(rs.getString("nombre"));
			cli.setValue(rs.getString("id"));
			clientesPedido.add(cli);
		}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	
	return clientesPedido;
}


public void tomarPedido(BeanPedido pedido) throws ParseException {
	try {
		PreparedStatement preparedStatement = con.prepareStatement("update pedido set nombre_empastador=?,estado=?" +
						"where id_pedido=?");
		
		// Parameters start with 1	
		
		//preparedStatement.setString(1, pedido.getNombre_cliente());
		//preparedStatement.setString(2, pedido.getNombre_empleado());			
		//preparedStatement.setString(3, pedido.getNombre_material());
		//preparedStatement.setString(4, pedido.getTipo_impresion());
		if(pedido.getEstado().equals("0")){
			preparedStatement.setString(1, "No asignado");
		}else{
			preparedStatement.setString(1, pedido.getNombreEmpastador()/*pedido.getNombre_empastador()*/);
		}
		preparedStatement.setString(2, pedido.getEstado());			
		preparedStatement.setString(3, pedido.getIdPedido());
		preparedStatement.executeUpdate();

	} catch (SQLException e) {
		e.printStackTrace();
	}
}
/*public BeanPedido getUserByNombre(String nombreCliente) {
	BeanPedido cliente = new BeanPedido();
	try {
		PreparedStatement preparedStatement = con.prepareStatement("select * from pedidos where id_pedido=?");
		preparedStatement.setString(1, nombreCliente);
		ResultSet rs = preparedStatement.executeQuery();
		
		if (rs.next()) {
			cliente.setNombre_cliente(rs.getString("nombre_cliente"));
			cliente.setNombre_empastador(rs.getString("nombre_empastador"));
			cliente.setId_pedido(rs.getString("id_pedido"));				
			cliente.setNombre_empleado(rs.getString("nombre_empleado"));
			cliente.setNombre_material(rs.getString("nombre_material"));
			cliente.setTipo_impresion(rs.getString("tipo_impresion"));
			cliente.setEstado(rs.getString("estado"));				
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return cliente;
}
*/
/*public int getUserCount(){
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
}*/
public List<BeanPedido> getEmpastadorPedidos(String searchId_pedido, String searchNombre_cliente,String searchNombre_empleado,String searchTipo_material,String searchTipo_impresion, String searchColor, String tamano_tapa, String cantidad_pagina, String precio,String searchNombre_empastador,String searchEstado,int jtStartIndex, int jtPageSize, String sort) {
	List<BeanPedido> pedidos = new ArrayList<BeanPedido>();
	String startIndex=Integer.toString(jtStartIndex);
	String pageSize=Integer.toString(jtPageSize);
	String query = "";
	query="select * from pedido where estado = '1' ORDER BY "+sort+" limit "+startIndex+","+pageSize;
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			BeanPedido pedido = new BeanPedido();
			pedido.setIdPedido(rs.getString("id_pedido"));
			pedido.setNombreCliente(rs.getString("nombre_cliente"));
			pedido.setNombreEmpleado(rs.getString("nombre_empleado"));				
			pedido.setTipoMaterial(rs.getString("tipo_material"));
			pedido.setTipoImpresion(rs.getString("tipo_impresion"));
			pedido.setColor(rs.getString("color"));
			pedido.setTamanoTapa(rs.getString("tamano_tapa"));
			pedido.setCantidadPagina(rs.getString("cantidad_pagina"));
			pedido.setPrecio(rs.getString("precio"));
			pedido.setNombreEmpastador(rs.getString("nombre_empastador"));
			pedido.setEstado(rs.getString("estado"));				
			pedidos.add(pedido);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}

	return pedidos;
}

public int getPedidosCount(){
	int count=0;
	try {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("select count(*) as count from pedido");		
		while (rs.next()) {
			count=rs.getInt("count");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return count;
}

public String getEstado(String id_pedido){
	String estado = null;
	try{
		PreparedStatement preparedStatement = con.prepareStatement("select * from pedido where id_pedido=?");
		preparedStatement.setString(1, id_pedido);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			estado=rs.getString("estado");				
		}
		return estado;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return estado;
}

}
