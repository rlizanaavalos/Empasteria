package controlador;
public class BeanInventario {
    public String codigo_material;
    public String nombre;
    public String grupo_material;
    public String cantidad;
    public String costeporunidad;
    
    public String getCodigo() {
        return codigo_material;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getGrupo() {
    	return grupo_material;
    }
    
    public String getCantidad() {
        return cantidad;
    }
    public String getCosto() {
        return costeporunidad;
    }
    public void setCodigo(String codigo) {
        this.codigo_material = codigo;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setGrupo(String grupo) {
        this.grupo_material = grupo;
    }
    
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    public void setCosto(String costo) {
        this.costeporunidad = costo;
    }   
    @Override
	public String toString() {
		return "Inventario [codigo_material=" + codigo_material + ", nombre=" + nombre + 
				", grupo_material=" + grupo_material + ", cantidad=" + cantidad + ", costeporunidad="+
				costeporunidad + "]";
	}
}
