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

import modelo.CrudDao;
import controlador.BeanCliente;
import controlador.Cascade;


public class CRUDController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CrudDao dao;
    
    public CRUDController() {
        dao=new CrudDao();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")!=null){
			List<BeanCliente> lstUser=new ArrayList<BeanCliente>();
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
					String searchNombre = request.getParameter("searchNombre");
					String searchRut = request.getParameter("searchRut");
					String searchDireccion = request.getParameter("searchDireccion");
					String searchComuna = request.getParameter("searchComuna");
					String searchCiudad = request.getParameter("searchCiudad");
					String searchRegion = request.getParameter("searchRegion");
					String searchCorreo = request.getParameter("searchCorreo");
					String searchTelefono = request.getParameter("searchTelefono");
										
					if(searchRegion == null || searchRegion.equals("0"))
					{
						searchRegion = "";
					}
					if(searchComuna == null)
					{
						searchComuna = "";
					}
					lstUser=dao.getAllUsers(searchNombre,searchRut,searchDireccion,searchComuna,searchCiudad,searchRegion,searchCorreo,searchTelefono,startPageIndex,numRecordsPerPage,sorting );
					int userCount=dao.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanCliente>>() {}.getType());
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
			else if(action.equals("create") || action.equals("update")){
				BeanCliente cliente=new BeanCliente();
				
				if(request.getParameter("nombre_cliente")!=null){	
				   String nombre=(String)request.getParameter("nombre_cliente");
				   cliente.setNombre(nombre);
				
				}
				if(request.getParameter("rut")!=null){
					String rut=(String)request.getParameter("rut");
					cliente.setRut(rut);
				}
				if(request.getParameter("direccion")!=null){
				   String direccion=(String)request.getParameter("direccion");
				   cliente.setDireccion(direccion);
				}
				if(request.getParameter("comuna")!=null){
				   String comuna=(String)request.getParameter("comuna");
				   cliente.setComuna(comuna);
				}
				if(request.getParameter("ciudad")!=null){
					   String ciudad=(String)request.getParameter("ciudad");
					   cliente.setCiudad(ciudad);
				}
				if(request.getParameter("region")!=null){
					   String searchRegion=(String)request.getParameter("region");
					   if(searchRegion.equals("0"))
						{
							searchRegion = "";
						}
						
					   cliente.setRegion(searchRegion);
					}
				if(request.getParameter("correo")!=null){
					   String correo=(String)request.getParameter("correo");
					   cliente.setCorreo(correo);
					}
				if(request.getParameter("telefono")!=null){
					   String telefono=(String)request.getParameter("telefono");
					   cliente.setTelefono(telefono);
					}
				try{											
					if(action.equals("create")){ //Create new record
						if(!dao.existeRut(cliente)){
							dao.addUser(cliente);					
							lstUser.add(cliente);
							//Convert Java Object to Json				
							String json=gson.toJson(cliente);					
							//Return Json in the format required by jTable plugin
							String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
							response.getWriter().print(listData);
						}else{
							String error="{\"Result\":\"ERROR\",\"Message\":\"El RUT ingresado ya existe en la Base de Datos.\"}";
							response.getWriter().print(error);
						}
					}else if(action.equals("update")){//Update existing record
						String aux = dao.getUserByNombre(cliente.getNombre()).getRut();
						if (!cliente.getRut().equals(aux) && dao.existeRut(cliente)){
							String error="{\"Result\":\"ERROR\",\"Message\":\"El nuevo RUT ingresado ya existe en la Base de Datos.\"}";
							response.getWriter().print(error);
						}else{
							dao.updateUser(cliente);
							String listData="{\"Result\":\"OK\"}";									
							response.getWriter().print(listData);
						}
					}
				}catch(Exception ex){
						String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
						response.getWriter().print(error);
				}
			}
			else if(action.equals("delete")){//Delete record
				try{
					if(request.getParameter("nombre_cliente")!=null){
						String nombre=(String)request.getParameter("nombre_cliente");
						dao.deleteUser(nombre);
						String listData="{\"Result\":\"OK\"}";								
						response.getWriter().print(listData);
					}
				}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
				response.getWriter().print(error);
			}				
			}
			
			else if(action.equals("getRegion"))
			{
				try{
			    List<Cascade> lstRegion=new ArrayList<Cascade>();
				String searchComuna = request.getParameter("searchRegion");
				System.out.println(searchComuna);
				lstRegion=dao.getRegion();		
				JsonElement element = gson.toJsonTree(lstRegion, new TypeToken<List<Cascade>>() {}.getType());
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
			else if(action.equals("getComuna"))
			{
				try{
			    List<Cascade> lstRegion=new ArrayList<Cascade>();
			    
				lstRegion=dao.getComuna("0");		
				JsonElement element = gson.toJsonTree(lstRegion, new TypeToken<List<Cascade>>() {}.getType());
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
			else
			{
				try{
				    List<Cascade> lstRegion=new ArrayList<Cascade>();
				    
					lstRegion=dao.getComuna(action);		
					JsonElement element = gson.toJsonTree(lstRegion, new TypeToken<List<Cascade>>() {}.getType());
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
