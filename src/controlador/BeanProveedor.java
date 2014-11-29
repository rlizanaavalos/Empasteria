/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

/**
 *
 * @author rlizana
 */
public class BeanProveedor {
    public String nombre;
    public String rut;
    public String tipo_insumos;
    public String direccion;
    public String telefono;
    public String correo;
    
    public String getNombre() {
        return nombre;
    }
    
    public String getRut() {
        return rut;
    }
 
    public String getTipoInsumo() {
        return tipo_insumos;
    }
    
    public String getDireccion() {
        return direccion;
    }
 
    public String getTelefono() {
        return telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public void setTipoInsumo(String tipoinsumos) {
        this.tipo_insumos = tipoinsumos;
    }
        
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
        
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
 	public String toString() {
 		return "Inventario [rut=" + rut + ", nombre=" + nombre	+ ", tipo_insumos =" + tipo_insumos + ", telefono="+ telefono + ", direccion="+ direccion + ", correo="+ correo + "]";
 	}

}