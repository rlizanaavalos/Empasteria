/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

public class BeanCliente {
    public String nombre_cliente;
    public String rut;
    public String direccion;
    public String ciudad;
    public String comuna;
    public String region;
    public String telefono;
    public String correo;
    
    public String getNombre() {
        return nombre_cliente;
    }
    
    public String getRut() {
        return rut;
    }
 
    public String getDireccion() {
        return direccion;
    }
    
    public String getCiudad() {
        return ciudad;
    }
 
    public String getComuna() {
        return comuna;
    }
    
    public String getRegion() {
        return region;
    }
 
    public String getTelefono() {
        return telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
 
    public void setNombre(String nombre) {
        this.nombre_cliente = nombre;
    }
    
    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void setComuna(String comuna) {
        this.comuna = comuna;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
   
    @Override
	public String toString() {
		return "Cliente [nombre_cliente=" + nombre_cliente + ", rut=" + rut
				+ ", direccion=" + direccion + ", ciudad="+ ciudad + ", comuna="+ comuna + ", ="+ region + ", region="+ ciudad + ", ciudad="+ ciudad + "]";
	}

}