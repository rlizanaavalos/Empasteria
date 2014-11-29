package controlador;
public class BeanCotizacion {
	public String id;
    public String impresion;
    public String color;
    public String material;
    public String paginas;
    public String precio;
    
    public String getId() {
        return id;
    }
    
    public String getImpresion() {
        return impresion;
    }
 
    public String getColor() {
        return color;
    }
    public String getMaterial() {
        return material;
    }
    public String getPaginas() {
        return paginas;
    }
    public String getPrecio() {
        return precio;
    }
   
    public void setId(String id) {
        this.id = id;
    }
    
    public void setImpresion(String impresion) {
        this.impresion = impresion;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    public void setMaterial(String material) {
        this.material = material;
    }   
    public void setPaginas(String paginas) {
        this.paginas = paginas;
    } 
    public void setPrecio(String precio) {
        this.precio = precio;
    } 
    @Override
	public String toString() {
		return "Cotizacion [id=" + id + ", impresion=" + impresion
				+ ", color=" + color + ", material="+ material + ", paginas="+ paginas + ", paginas="+ paginas + "]";
	}
}
