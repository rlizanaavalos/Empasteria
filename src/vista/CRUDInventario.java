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

import modelo.CrudInv;
import controlador.BeanInventario;


public class CRUDInventario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CrudInv dao;
    
    public CRUDInventario() {
        dao=new CrudInv();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if(request.getParameter("action")!=null){
			List<BeanInventario> lstUser=new ArrayList<BeanInventario>();
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
					String searchCodigo = request.getParameter("searchCodigo");
					String searchNombre = request.getParameter("searchNombre");
					String searchGrupo = request.getParameter("searchGrupo");
					String searchCantidad = request.getParameter("searchCantidad");
					String searchCosto = request.getParameter("searchPrecio");
					 if(searchGrupo == null ||searchGrupo.equals("0"))
						{
						 searchGrupo="";
						}
					 System.out.println("asd" + searchGrupo);
					lstUser=dao.getAllUsers(searchCodigo,searchNombre,searchGrupo,searchCantidad,searchCosto,startPageIndex,numRecordsPerPage,sorting );
					int userCount=dao.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanInventario>>() {}.getType());
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
				BeanInventario inventario=new BeanInventario();
				if(request.getParameter("codigo_material")!=null){	
				   String codigo=(String)request.getParameter("codigo_material");
				   inventario.setCodigo(codigo);
				}
				if(request.getParameter("nombre")!=null){
					String nombre=(String)request.getParameter("nombre");
					inventario.setNombre(nombre);
				}
				if(request.getParameter("grupo_material")!=null){
					String grupo=(String)request.getParameter("grupo_material");
					inventario.setGrupo(grupo);
				}
				if(request.getParameter("cantidad")!=null){
				   String cantidad=(String)request.getParameter("cantidad");
				   inventario.setCantidad(cantidad);
				}
				if(request.getParameter("costeporunidad")!=null){
				   String costo=(String)request.getParameter("costeporunidad");
				   inventario.setCosto(costo);
				}
				try{											
					if(action.equals("create")){//Create new record
						dao.addUser(inventario);					
						lstUser.add(inventario);
						//Convert Java Object to Json				
						String json=gson.toJson(inventario);					
						//Return Json in the format required by jTable plugin
						String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
						response.getWriter().print(listData);
					}else if(action.equals("update")){//Update existing record
						dao.updateUser(inventario);
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
					if(request.getParameter("codigo_material")!=null){
						String codigo=(String)request.getParameter("codigo_material");
						dao.deleteUser(codigo);
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
