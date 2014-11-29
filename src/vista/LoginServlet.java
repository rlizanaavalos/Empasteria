package vista;  
  
import java.io.IOException;  
import java.io.PrintWriter;  
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import controlador.BeanUser;
import modelo.LoginDao;  
  
public class LoginServlet extends HttpServlet{  
  
    private static final long serialVersionUID = 1L;  
	private LoginDao dao;
    
    public LoginServlet() {
        dao=new LoginDao();
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException 
            {    
    	
			if(request.getParameter("action")!=null){
			List<BeanUser> lstUser=new ArrayList<BeanUser>();
			String action=(String)request.getParameter("action");
			Gson gson = new Gson();
			response.setContentType("application/json");
			if(action.equals("list") )
			{
				try
					{						
					//Fetch Data from User Table
					int startPageIndex=Integer.parseInt(request.getParameter("jtStartIndex"));
					int numRecordsPerPage=Integer.parseInt(request.getParameter("jtPageSize"));
					String sorting = request.getParameter("jtSorting");
					String searchId = request.getParameter("searchId");
					String searchUser = request.getParameter("searchUser");
					String searchPassword = request.getParameter("searchPassword");
					String searchRol = request.getParameter("searchRol");
					
					
					if(searchRol == null || searchRol.equals("0"))
					{
						searchRol = "";
					}
					lstUser=dao.getAllUsers(searchId,searchUser,searchPassword,searchRol,startPageIndex,numRecordsPerPage,sorting );
					int userCount=dao.getUserCount();		
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<BeanUser>>() {}.getType());
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
			BeanUser user=new BeanUser();
			if(request.getParameter("user")!=null){	
			   String usuario=(String)request.getParameter("user");
			   user.setUser(usuario);
			}
			if(request.getParameter("password")!=null){
				String password=(String)request.getParameter("password");
				
				try
			    {
			       char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
			 
			       MessageDigest msgdgt = MessageDigest.getInstance("MD5");
			       byte[] bytes = msgdgt.digest(password.getBytes());
			       StringBuilder strCryptMD5 = new StringBuilder(2 * bytes.length);
			       for (int i = 0; i < bytes.length; i++)
			       {
			           int low = (int)(bytes[i] & 0x0f);
			           int high = (int)((bytes[i] & 0xf0) >> 4);
			           strCryptMD5.append(HEXADECIMALES[high]);
			           strCryptMD5.append(HEXADECIMALES[low]);
			       }
			       password = strCryptMD5.toString();
			    } catch (NoSuchAlgorithmException e) {
			       password = null;
			    }
				
				user.setPassword(password);
			}
			if(request.getParameter("rol")!=null){
			   String rol=(String)request.getParameter("rol");
			   user.setRol(rol);
			}
			if(request.getParameter("id")!=null){
				   String id=(String)request.getParameter("id");
				   user.setId(id);
				}
			try{											
				if(action.equals("create")){//Create new record
					dao.addUser(user);					
					lstUser.add(user);
					//Convert Java Object to Json				
					String json=gson.toJson(user);					
					//Return Json in the format required by jTable plugin
					String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
					response.getWriter().print(listData);
				}else if(action.equals("update")){//Update existing record
					dao.updateUser(user);
					String listData="{\"Result\":\"OK\"}";									
					response.getWriter().print(listData);
				}
			}
			catch(Exception ex){
					String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
					response.getWriter().print(error);
			}
		}
		else if(action.equals("delete"))
		{//Delete record
			try
			{
				if(request.getParameter("user")!=null){
					String usuario=(String)request.getParameter("user");
					dao.deleteUser(usuario);
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
		else if(action.equals("login"))
		{
			    response.setContentType("text/html");    
		        PrintWriter out = response.getWriter();    
		        String n=request.getParameter("username");    
		        String p=request.getParameter("userpass");  
		        
		        try
			    {
			       char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
			 
			       MessageDigest msgdgt = MessageDigest.getInstance("MD5");
			       byte[] bytes = msgdgt.digest(p.getBytes());
			       StringBuilder strCryptMD5 = new StringBuilder(2 * bytes.length);
			       for (int i = 0; i < bytes.length; i++)
			       {
			           int low = (int)(bytes[i] & 0x0f);
			           int high = (int)((bytes[i] & 0xf0) >> 4);
			           strCryptMD5.append(HEXADECIMALES[high]);
			           strCryptMD5.append(HEXADECIMALES[low]);
			       }
			       p = strCryptMD5.toString();
			    } catch (NoSuchAlgorithmException e) {
			       p = null;
			    }
		        
		        HttpSession session = request.getSession(false);  
		        
		        if(session!=null)  
		        session.setAttribute("name", n);  
		        BeanUser user = dao.getUser(n,p);
		        if(user.getRol().equals("Administrador"))
		        { 
		        	
		        	RequestDispatcher rd;
		        	rd = request.getRequestDispatcher("cliente.jsp"); 
		            rd.forward(request,response);    
		        }    
		        else if(user.getRol().equals("Operador"))
				{ 
				        	
				        	RequestDispatcher rd;
				        	rd = request.getRequestDispatcher("clienteO.jsp"); 
				            rd.forward(request,response);    
				}  
		        else
		        { 
		        	
		        	request.setAttribute("errorMessage", "Usuario o contraseña invalida");
		            RequestDispatcher rd=request.getRequestDispatcher("index.jsp"); 
		            rd.include(request,response);    
		        }      
		        out.close();    
			
		}	
 }
}
 }