package controlador;

public class BeanUser {
	public String id;
    public String user;
    public String password;
    public String rol;
    
    public String getUser() {
        return user;
    }
    public String getId()
    {
    	return id;
    }
    public String getPassword() {
        return password;
    }
 
    public String getRol() {
        return rol;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setId(String id)
    {
    	this.id = id;
    }   
    @Override
	public String toString() {
		return "User [id=" + id + ",user=" + user + ", password=" + password + ", rol=" + rol +"]";
	}

}