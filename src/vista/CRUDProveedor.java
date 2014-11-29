package vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.CrudProv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import controlador.BeanProveedor;


public class CRUDProveedor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CrudProv dao;
    
    public CRUDProveedor() {
        dao=new CrudProv();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if(request.getParameter("action")!=null){
			List<BeanProveedor> lstUser=new ArrayList<BeanProveedor>();
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
					String searchRut = request.getParameter("searchRut");
					String searchNombre = request.getParameter("searchNombre");
					String searchTipoInsumos = request.getParameter("searchTipo_insumos");
					String searchTelefono = request.getParameter("searchTelefono");
					String searchDireccion = request.getParameter("searchDireccion");
					String searchCorreo = request.getParameter("searchCorreo");
					lstUser=dao.getAllUsers(searchRut,searchNombre,searchTipoInsumos,searchTelefono,searchDireccion,searchCorreo,startPageIndex,numRecordsPerPage,sorting );
					int userCount=dao.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanProveedor>>() {}.getType());
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
				BeanProveedor proveedor=new BeanProveedor();
				if(request.getParameter("rut")!=null){	
				   String rut=(String)request.getParameter("rut");
				   proveedor.setRut(rut);
				}
				if(request.getParameter("nombre")!=null){
					String nombre=(String)request.getParameter("nombre");
					proveedor.setNombre(nombre);
				}
				if(request.getParameter("tipo_insumos")!=null){
				   String tipo_insumo=(String)request.getParameter("tipo_insumos");
				   proveedor.setTipoInsumo(tipo_insumo);
				}
				if(request.getParameter("telefono")!=null){
				   String telefono=(String)request.getParameter("telefono");
				   proveedor.setTelefono(telefono);
				}
				if(request.getParameter("direccion")!=null){
					   String direccion=(String)request.getParameter("direccion");
					   proveedor.setDireccion(direccion);
					}
				if(request.getParameter("correo")!=null){
					   String correo=(String)request.getParameter("correo");
					   proveedor.setCorreo(correo);
					}
				try{											
					if(action.equals("create")){//Create new record
						if(!dao.existeRut(proveedor)){
							dao.addUser(proveedor);					
							lstUser.add(proveedor);
							//Convert Java Object to Json				
							String json=gson.toJson(proveedor);					
							//Return Json in the format required by jTable plugin
							String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
							response.getWriter().print(listData);
						}else{
							String error="{\"Result\":\"ERROR\",\"Message\":\"El RUT ingresado ya existe en la Base de Datos.\"}";
							response.getWriter().print(error);
						}
					}else if(action.equals("update")){//Update existing record
						dao.updateUser(proveedor);
						String listData="{\"Result\":\"OK\"}";									
						response.getWriter().print(listData);
					}
				}
				catch(Exception ex){
						String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
						response.getWriter().print(error);
				}
			}else if(action.equals("delete")){//Delete record
				try{
					if(request.getParameter("rut")!=null){
						String rut=(String)request.getParameter("rut");
						dao.deleteUser(rut);
						String listData="{\"Result\":\"OK\"}";								
						response.getWriter().print(listData);
					}
				}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
				response.getWriter().print(error);
			}				
			}
	 }
  }
}
