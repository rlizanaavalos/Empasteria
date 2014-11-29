package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import controlador.BeanClientePedido;
import controlador.BeanEmpleadoPedido;
import controlador.BeanImpresion;
import controlador.BeanInventarioPedido;
import controlador.BeanPedido;
import modelo.CrudPedido;
import modelo.CrudInv;
public class PEDIDOController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CrudPedido pedido1;
	private CrudInv inv;
	
    public PEDIDOController() {
        pedido1 =new CrudPedido();
        inv = new CrudInv();
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")!=null){
			List<BeanPedido> lstUser=new ArrayList<BeanPedido>();
			String action=(String)request.getParameter("action");
			Gson gson = new Gson();
			response.setContentType("application/json");
			String impresion = null;
		        String color = null;
		        String material = null;
		        String tapa = null;
		        String pag = null;
		        String pago  = null;
			if(action.equals("list"))
			{
				try
				{						
					//Fetch Data from User Table
					int startPageIndex=Integer.parseInt(request.getParameter("jtStartIndex"));
					int numRecordsPerPage=Integer.parseInt(request.getParameter("jtPageSize"));
					String sorting = request.getParameter("jtSorting");
					//int searchId = Integer.parseInt(id);			
					String searchId = request.getParameter("searchId");
					String searchCliente = request.getParameter("searchCliente");
					String searchEmpleado = request.getParameter("searchEmpleado");
					String searchImpresion = request.getParameter("searchImpresion");
					String searchColor = request.getParameter("searchColor");
					String searchEmpastador = request.getParameter("searchEmpastador");
					String searchEstado = request.getParameter("searchEstado");
					 if(searchColor == null ||searchColor.equals("0"))
						{
							searchColor="";
						}
					 if(searchEstado == null ||searchEstado.equals("0"))
						{
						 searchEstado="";
						}
					String searchMaterial = request.getParameter("searchMaterial");
					String searchTapa = request.getParameter("searchTapa");
					String searchPagina = request.getParameter("searchPagina");
					String searchPrecio = request.getParameter("searchPrecio");
					String searchPago = request.getParameter("searchPago");
				    lstUser=pedido1.getAllUsers(searchId,searchCliente,searchEmpleado,searchImpresion,searchColor,searchMaterial,searchTapa,searchPagina,searchPrecio,searchPago,searchEmpastador,searchEstado,startPageIndex,numRecordsPerPage,sorting );
					
					int userCount=pedido1.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanPedido>>() {}.getType());
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
			else if(action.equals("create") || action.equals("update"))
			{
				BeanPedido pedido=new BeanPedido();
				if(request.getParameter("id_pedido")!=null){	
				   String idPedido=(String)request.getParameter("id_pedido");
				   pedido.setIdPedido(idPedido);
				}
				if(request.getParameter("nombre_cliente")!=null){
					String cliente=(String)request.getParameter("nombre_cliente");
					pedido.setNombreCliente(cliente);
				}
				if(request.getParameter("nombre_empleado")!=null){
				   String empleado=(String)request.getParameter("nombre_empleado");
				   pedido.setNombreEmpleado(empleado);
				}
				if(request.getParameter("tipo_impresion")!=null){
				   impresion = 	(String)request.getParameter("tipo_impresion");
				   System.out.println("Impresion " + impresion);
				  // String impresion=(String)request.getParameter("tipo_impresion");
				   pedido.setTipoImpresion(impresion);
				}
				if(request.getParameter("color")!=null){
					   color=(String)request.getParameter("color");
					   pedido.setColor(color);
				}
				if(request.getParameter("tipo_material")!=null){
					   material=(String)request.getParameter("tipo_material");
					   pedido.setTipoMaterial(material);
					}
				if(request.getParameter("tamano_tapa")!=null){
					   tapa=(String)request.getParameter("tamano_tapa");
					   pedido.setTamanoTapa(tapa);
					}
				if(request.getParameter("cantidad_pagina")!=null){
					   pag=(String)request.getParameter("cantidad_pagina");
					   pedido.setCantidadPagina(pag);
					}
				int precioPagina = Integer.parseInt(pedido.getCantidadPagina())*5;
				String precio = Integer.toString(inv.obtenerPrecioImpresion(impresion) +inv.obtenerPrecio(material) + precioPagina);
				pedido.setPrecio(precio);
				pago=Integer.toString(Integer.parseInt(precio)/2);
				pedido.setPago(pago);
			
				if(request.getParameter("nombre_empastador")!=null){
					   String pa = "No Asignado";
					   pedido.setNombreEmpastador(pa);
					}
				if(request.getParameter("estado")!=null){
					   String pa=(String)request.getParameter("estado");
					  // String pa = "1";
					   pedido.setEstado(pa);
					}
				pedido.setNombreEmpastador("No Asignado");
			  //  pedido.setEstado("1");

				if(inv.existeinventario(material))
				{
					try{											
						if(action.equals("create")){//Create new record
							
							pedido1.addPedido(pedido);					
							lstUser.add(pedido);
							String json=gson.toJson(pedido);					
							String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
							response.getWriter().print(listData);
						}
						else if(action.equals("update")){//Update existing record
							pedido1.updatePedido(pedido);
							String listData="{\"Result\":\"OK\"}";									
							response.getWriter().print(listData);
						}
					}catch(Exception ex){
							String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
							response.getWriter().print(error);
					}
					}
					else{
						String error="{\"Result\":\"ERROR\",\"Message\":\"No hay suficientes materiales.\"}";
						response.getWriter().print(error);
					}
			}
			
			else if(action.equals("delete"))
			{
			try{
					
					if(request.getParameter("id_pedido")!=null)
					{
						String idpedido=(String)request.getParameter("id_pedido");
						pedido1.deletePedido(idpedido);
						String listData="{\"Result\":\"OK\"}";								
						response.getWriter().print(listData);
					}
				}
			catch(Exception ex)
				{
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
				response.getWriter().print(error);
				}				
			}
	
		else if(action.equals("getClientePedido"))
		{
			try{
		    List<BeanClientePedido> lstCliente=new ArrayList<BeanClientePedido>();
			lstCliente=pedido1.getClientePedido();		
			JsonElement element = gson.toJsonTree(lstCliente, new TypeToken<List<BeanClientePedido>>() {}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			String listData=jsonArray.toString();		
			listData="{\"Result\":\"OK\",\"Options\":"+listData+"}";			
			response.getWriter().print(listData);
			}
			catch(Exception ex)
			{
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}
		}
		else if(action.equals("getEmpleadoPedido"))
		{
			try{
		    List<BeanEmpleadoPedido> lstCliente=new ArrayList<BeanEmpleadoPedido>();
			lstCliente=pedido1.getEmpleadoPedido();		
			JsonElement element = gson.toJsonTree(lstCliente, new TypeToken<List<BeanEmpleadoPedido>>() {}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			String listData=jsonArray.toString();		
			listData="{\"Result\":\"OK\",\"Options\":"+listData+"}";			
			response.getWriter().print(listData);
			}
			catch(Exception ex)
			{
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}
		}
		
		else if(action.equals("getImpresion"))
		{
			try{
		    List<BeanImpresion> lstCliente=new ArrayList<BeanImpresion>();
			lstCliente=pedido1.getImpresion();		
			JsonElement element = gson.toJsonTree(lstCliente, new TypeToken<List<BeanImpresion>>() {}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			String listData=jsonArray.toString();		
			listData="{\"Result\":\"OK\",\"Options\":"+listData+"}";			
			response.getWriter().print(listData);
			}
			catch(Exception ex)
			{
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}
		}
		else if(action.equals("getMaterial")) //obtiene las tapas
		{
			try{
		    List<BeanInventarioPedido> lstCliente=new ArrayList<BeanInventarioPedido>();
			lstCliente=pedido1.getTapa();		
			JsonElement element = gson.toJsonTree(lstCliente, new TypeToken<List<BeanInventarioPedido>>() {}.getType());
			JsonArray jsonArray = element.getAsJsonArray();
			String listData=jsonArray.toString();		
			listData="{\"Result\":\"OK\",\"Options\":"+listData+"}";			
			response.getWriter().print(listData);
			}
			catch(Exception ex)
			{
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}
		}
		
	 }
  }
}
