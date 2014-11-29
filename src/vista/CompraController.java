package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.CrudCompra;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import controlador.BeanCompra;
import controlador.BeanEmpleadoPedido;
import controlador.BeanProveedorCompra;

/**
 * Servlet implementation class CompraController
 */
@WebServlet("/CompraController")
public class CompraController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CrudCompra compra;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompraController() {
    	 compra = new CrudCompra();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("action")!=null){
			List<BeanCompra> lstUser=new ArrayList<BeanCompra>();
			String action=(String)request.getParameter("action");
			Gson gson = new Gson();
			response.setContentType("application/json");
			
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
					String searchEmpleado = request.getParameter("searchEmpleado");
					String searchProveedor = request.getParameter("searchProveedor");
					String searchFecha = request.getParameter("searchFecha");
					String searchMedio_pago = request.getParameter("searchMedio_pago");
					String searchTotal = request.getParameter("searchTotal");
					
				    lstUser=compra.getAllUsers(searchId,searchEmpleado,searchProveedor,searchFecha,searchMedio_pago,searchTotal,startPageIndex,numRecordsPerPage,sorting );
				    
				    int userCount=compra.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanCompra>>() {}.getType());
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
				BeanCompra compra1=new BeanCompra();
				if(request.getParameter("codigo_compra")!=null){	
				   String codigo_compra=(String)request.getParameter("codigo_compra");
				   compra1.setId_compra(codigo_compra);
				}
				if(request.getParameter("nombre_empleado")!=null){
				   String empleado=(String)request.getParameter("nombre_empleado");
				   compra1.setNombre_empleado(empleado);
				}
				if(request.getParameter("nombre_proveedor")!=null){
				   String proveedor = 	(String)request.getParameter("nombre_proveedor");
				   compra1.setNombre_proveedor(proveedor);
				}
				if(request.getParameter("fecha")!=null){
					   String fecha=(String)request.getParameter("fecha");
					   compra1.setFecha(fecha);
				}
				if(request.getParameter("medio_pago")!=null){
					   String medio_pago=(String)request.getParameter("medio_pago");
					   compra1.setMedio_pago(medio_pago);
					}
				if(request.getParameter("total")!=null){
					   String total=(String)request.getParameter("total");
					   compra1.setTotal(total);
				}
				/*####################################################################*/
				try{											
					if(action.equals("create")){//Create new record
						
						compra.addCompra(compra1);					
						lstUser.add(compra1);
						String json=gson.toJson(compra1);					
						String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
						response.getWriter().print(listData);
					}
					else if(action.equals("update")){//Update existing record
						compra.updateCompra(compra1);
						String listData="{\"Result\":\"OK\"}";									
						response.getWriter().print(listData);
					}
				}catch(Exception ex){
						String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
						response.getWriter().print(error);
				}
				}
		
		else if(action.equals("delete"))
		{
		try{
				
				if(request.getParameter("codigo_compra")!=null)
				{
					String codigo_compra=(String)request.getParameter("codigo_compra");
					compra.deleteCompra(codigo_compra);
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

	else if(action.equals("getProveedorCompra"))
	{
		try{
	    List<BeanProveedorCompra> lstCliente=new ArrayList<BeanProveedorCompra>();
		lstCliente=compra.getProveedorCompra();		
		JsonElement element = gson.toJsonTree(lstCliente, new TypeToken<List<BeanProveedorCompra>>() {}.getType());
		JsonArray jsonArray = element.getAsJsonArray();
		String listData=jsonArray.toString();		
		listData="{\"Result\":\"OK\",\"Options\":"+listData+"}";		
		System.out.println(listData);
		response.getWriter().print(listData);
		}
		catch(Exception ex)
		{
			String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
			response.getWriter().print(error);
			ex.printStackTrace();
		}
	}
	else if(action.equals("getEmpleadoCompra"))
	{
		try{
	    List<BeanEmpleadoPedido> lstCliente=new ArrayList<BeanEmpleadoPedido>();
		lstCliente=compra.getEmpleadoPedido();		
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
	
 }
}
}
