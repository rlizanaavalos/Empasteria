package modelo;  
  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import controlador.BeanUser;
  
public class LoginDao {  
	private Connection con;
	public LoginDao() {

		Conexion c=new Conexion();
		con = c.getConexion();
	}
    public boolean validate(String name, String pass) {          
        boolean status = false;  
        Conexion c=new Conexion();
		con = c.getConexion();
        PreparedStatement pst = null;  
        ResultSet rs = null;  
        try {  
            pst = con.prepareStatement("select * from login where user=? and password=?");  
            pst.setString(1, name);  
            pst.setString(2, pass);  
  
            rs = pst.executeQuery();  
            status = rs.next();  
  
        } catch (Exception e) {  
            System.out.println(e);  
        } finally {  
            if (con != null) {  
                try {  
                    con.close();  
                }
                catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (pst != null) {  
                try {  
                    pst.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (rs != null) {  
                try {  
                    rs.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return status;  
    } 
	public int getUserCount(){
		int count=0;
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count from login");		
			while (rs.next()) {
				count=rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
    
	public List<BeanUser> getAllUsers(String searchId, String searchUser,String searchPassword ,String searchRol,int jtStartIndex, int jtPageSize, String sort) {
		List<BeanUser> usuarios = new ArrayList<BeanUser>();
		String startIndex=Integer.toString(jtStartIndex);
		String pageSize=Integer.toString(jtPageSize);
		String query = "";
		query="select * from login where id like '%" + searchId + "%' and user like '%" + searchUser + "%' and " + "password like '%" + searchPassword + "%' and rol like '%" + searchRol +  "%' ORDER BY "+sort+" limit "+startIndex+","+pageSize;

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				BeanUser usuario = new BeanUser();
				usuario.setId(rs.getString("id"));
				usuario.setUser(rs.getString("user"));
				usuario.setPassword(rs.getString("password"));
				usuario.setRol(rs.getString("rol"));			
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usuarios;
	}
	
    public BeanUser getUser(String usuario, String password) {
		BeanUser user = new BeanUser();
		try {
		    user.setUser("");
			user.setPassword("");
			user.setRol("");		
			PreparedStatement preparedStatement = con.prepareStatement("select * from login where user=? and password=?");
			preparedStatement.setString(1, usuario);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();			
			if (rs.next()) {
				user.setId(rs.getString("id"));
				user.setUser(rs.getString("user"));
				user.setPassword(rs.getString("password"));
				user.setRol(rs.getString("rol"));				
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
    public void addUser(BeanUser user) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into login(user,password,rol) values (?, ?, ? )");
			preparedStatement.setString(1, user.getUser());
			preparedStatement.setString(2, user.getPassword());			
			preparedStatement.setString(3, user.getRol());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public void deleteUser(String user) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from login where user=?");
			// Parameters start with 1
			preparedStatement.setString(1, user);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void updateUser(BeanUser usuario) throws ParseException {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update login set user =?, password=?,rol=?" +
							"where id=?");
			preparedStatement.setString(1, usuario.getUser());
			preparedStatement.setString(2, usuario.getPassword());
			preparedStatement.setString(3, usuario.getRol());			
			preparedStatement.setString(4, usuario.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}  