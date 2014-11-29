package controlador;

/**
 *
 * @author rlizana
 */
public class BeanPedido {
	public String id_pedido;
    public String nombre_cliente;
    public String nombre_empleado;
    public String tipo_impresion;
    public String color;
    public String tipo_material;
    public String tamano_tapa;
    public String cantidad_pagina;
    public String precio;
    public String pago;
    public String nombre_empastador;
    public String estado;
    public String fecha;
    
    public String getIdPedido(){
    	return id_pedido;
    }
    
    public String getFecha(){
    	return fecha;
    }
    
    
    public String getNombreCliente() {
        return nombre_cliente;
    }
 
    public String getNombreEmpleado() {
        return nombre_empleado;
    }
    
 
    public String getTipoImpresion() {
        return tipo_impresion;
    }
    
    
    public String getColor(){
    	return color;
    }
    
    public String getTipoMaterial(){
    	return tipo_material;
    }
    
    public String getTamanoTapa(){
    	return tamano_tapa;
    }
    
    public String getCantidadPagina(){
    	return cantidad_pagina;
    }
    
    public String getPrecio(){
    	return precio;
    }
    
    public String getPago(){
    	return pago;
    }
    
    public void setIdPedido(String id_pedido){
    	this.id_pedido = id_pedido;
    }
    
    public void setNombreCliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
    
    public void setNombreEmpleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }
    
    
    public void setTipoImpresion(String tipo_impresion) {
        this.tipo_impresion = tipo_impresion;
    }
    
    
    public void setColor(String color){
    	this.color = color;
    }
    
    public void setTipoMaterial(String tipo_material){
    	this.tipo_material = tipo_material;
    }
    
    public void setTamanoTapa(String tamano_tapa){
    	this.tamano_tapa = tamano_tapa;
    }
    
    public void setCantidadPagina(String cantidad_pagina){
    	this.cantidad_pagina = cantidad_pagina;
    }
    
    public void setPrecio(String precio){
    	this.precio = precio;
    }
    
    public void setPago(String pago){
    	this.pago = pago;
    }
    public String getNombreEmpastador() {
		return nombre_empastador;
	}

	public void setNombreEmpastador(String nombre_empastador) {
		this.nombre_empastador = nombre_empastador;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	 public void setFecha(String fecha) {
	        this.fecha = fecha;
	    }
	 
    public String toString() {
		return "pedido [id_pedido=" + id_pedido + ", nombre_cliente=" + nombre_cliente+", nombre_empleado=" + nombre_empleado
				+ ", tipo_impresion=" + tipo_impresion + ", color="+ color + ", tipo_material="+ tipo_material + ", tamano_tapa="+ tamano_tapa
				+ ", cantidad_pagina="+ cantidad_pagina + ", precio="+ precio + ", pago="+ pago +", estado="+ estado +", fecha="+ fecha +"]";
	}
    
}
