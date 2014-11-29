package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import controlador.BeanPedido;
import modelo.CrudPedido;
import modelo.CrudInv;


/**
 * Servlet implementation class CRUDTomarPedido
 */

@WebServlet("/CRUDTomarPedido")
public class CRUDTomarPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
private CrudPedido dao;
private CrudInv inv;
String tipo_material;
String estado;
String id_pedido;
String nombre_empastador;
String tipo_impresion;
String tamano_tapa;
String cantidad_pagina;
String color;
    
    public CRUDTomarPedido() {
        dao=new CrudPedido();
        inv=new CrudInv();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")!=null){
			List<BeanPedido> lstPedidos=new ArrayList<BeanPedido>();
			HttpSession session = request.getSession(true);
			String action=(String)request.getParameter("action");
			Gson gson = new Gson();
			response.setContentType("application/json");

			if(action.equals("list") ){
				try
				{						
					//Fetch Data from User Table
					int startPageIndex=Integer.parseInt(request.getParameter("jtStartIndex"));
					int numRecordsPerPage=Integer.parseInt(request.getParameter("jtPageSize"));
					String sorting = request.getParameter("jtSorting");
					String searchId_pedido = request.getParameter("searchidpedido");
					String searchNombre_cliente = request.getParameter("searchnombre_cliente");
					String searchNombre_empleado = request.getParameter("searchnombre_empleado");
					String searchColor = request.getParameter("searchcolor");
					String searchTamano_tapa = request.getParameter("searchtamano_tapa");
					String searchCantidad_pagina = request.getParameter("searchcantidad_pagina");
					String searchPrecio = request.getParameter("searchprecio");
					String searchTipo_material = request.getParameter("searchtipo_material");
					String searchTipo_impresion = request.getParameter("searchtipo_impresion");
					String searchNombre_empastador = request.getParameter("searchnombre_empastador");
					String searchEstado = request.getParameter("searchestado");
					
					if(searchEstado==null || searchEstado.equals("0"))
					{
						searchEstado = "";
						
					}
					//System.out.println(sorting+searchNombre+searchRut+searchDireccion+searchComuna+searchCiudad+searchRegion+searchCorreo+searchTelefono);
					
					lstPedidos=dao.getEmpastadorPedidos(searchId_pedido, searchNombre_cliente,searchNombre_empleado,searchTipo_material,searchTipo_impresion, searchColor, searchTamano_tapa, searchCantidad_pagina, searchPrecio,searchNombre_empastador,searchEstado,startPageIndex, numRecordsPerPage, sorting);
					int pedidosCount=dao.getPedidosCount();		
					JsonElement element = gson.toJsonTree(lstPedidos, new TypeToken<List<BeanPedido>>() {}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listData=jsonArray.toString();				
					listData="{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+pedidosCount+"}";			
					response.getWriter().print(listData);

					}
					catch(Exception ex)
					{
						String error="{\"Result\":\"ERROR\",\"Message\":skdjdskjkjds"+ex.getMessage()+"}";
						response.getWriter().print(error);
						ex.printStackTrace();
					}	
			}
			else if(action.equals("lista2"))
			{
				try{
					int startPageIndex=Integer.parseInt(request.getParameter("jtStartIndex"));
					int numRecordsPerPage=Integer.parseInt(request.getParameter("jtPageSize"));
					String sorting = request.getParameter("jtSorting");
					
					lstPedidos=dao.getAllUsers2(startPageIndex,numRecordsPerPage,sorting );
					
					int userCount=dao.getUserCount();		
					JsonElement element = gson.toJsonTree(lstPedidos, new TypeToken<List<BeanPedido>>() {}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listData=jsonArray.toString();				
					listData="{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+userCount+"}";			
					response.getWriter().print(listData);
				}
				catch(Exception ex)
				{
					String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
					response.getWriter().print(error);
					ex.printStackTrace();
				}
			}
			else if(action.equals("update")){
				nombre_empastador =  (String) session.getAttribute("name");
				//System.out.println(nombre_empastador);
				BeanPedido pedido=new BeanPedido();
				if(request.getParameter("id_pedido")!=null){
					   id_pedido=(String)request.getParameter("id_pedido");
					   pedido.setIdPedido(id_pedido);
				}
				if(request.getParameter("nombre_cliente")!=null){
					   String nombre_cliente=(String)request.getParameter("nombre_cliente");
					   pedido.setNombreCliente(nombre_cliente);
				}
				if(request.getParameter("nombre_empleado")!=null){
					   String nombre_empleado=(String)request.getParameter("nombre_empleado");
					   pedido.setNombreEmpleado(nombre_empleado);
				}
				if(request.getParameter("tipo_material")!=null){
					   tipo_material=(String)request.getParameter("tipo_material");
					   pedido.setTipoMaterial(tipo_material);
				}
				if(request.getParameter("tipo_impresion")!=null){
					   tipo_impresion=(String)request.getParameter("tipo_impresion");
					   pedido.setTipoImpresion(tipo_impresion);
				}
				if((String) session.getAttribute("name")!=null){
					   nombre_empastador =  (String) session.getAttribute("name");
					   pedido.setNombreEmpastador(nombre_empastador);

				}
				if(request.getParameter("estado")!=null){
					   estado=(String)request.getParameter("estado");
					   pedido.setEstado(estado);
				}
				if(request.getParameter("color")!=null){
					   color=(String)request.getParameter("color");
					   pedido.setColor(color);
				}
				if(request.getParameter("tamano_tapa")!=null){
					   tamano_tapa=(String)request.getParameter("tamano_tapa");
					   pedido.setTamanoTapa(tamano_tapa);
				}
				if(request.getParameter("cantidad_pagina")!=null){
					   cantidad_pagina=(String)request.getParameter("cantidad_pagina");
					   pedido.setCantidadPagina(cantidad_pagina);
				}
				if(request.getParameter("precio")!=null){
					   String precio=(String)request.getParameter("precio");
					   pedido.setPrecio(precio);
				}
				
				String estado_antiguo = (String)request.getParameter("estado");
				System.out.println(estado_antiguo);
				if(estado_antiguo.equals("2")){
					try
					{	
						if(inv.existeinventario(tipo_material)) 
						{
							inv.restarinventario(tipo_material);
							//System.out.println("tamano_tapa " + tipo_material);
							dao.tomarPedido(pedido);
							String listData="{\"Result\":\"OK\"}";									
							response.getWriter().print(listData);
						}
						else
						{
							String error="{\"Result\":\"ERROR\",\"Message\":\"No hay suficientes materiales para realizar el pedido.\"}";
							response.getWriter().print(error);
						}
					}
					catch(Exception ex){
						String error="{\"Result\":\"ERROR\",\"Message\":\"No hay suficientes materiales para realizar el pedido.\"}";
						response.getWriter().print(error);
					}
				}
				else if(estado_antiguo.equals("1"))
				{
					String listData="{\"Result\":\"OK\"}";									
					response.getWriter().print(listData);
					
				}
						
		}
 }
}
}
