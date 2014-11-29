package controlador;

public class BeanCompra {
	public String codigo_compra;
	public String nombre_empleado;
	public String nombre_proveedor;
	public String fecha;
	public String medio_pago;
	public String total;
	public String getId_compra() {
		return codigo_compra;
	}
	public void setId_compra(String codigo_compra) {
		this.codigo_compra = codigo_compra;
	}
	public String getNombre_empleado() {
		return nombre_empleado;
	}
	public void setNombre_empleado(String nombre_empleado) {
		this.nombre_empleado = nombre_empleado;
	}
	public String getNombre_proveedor() {
		return nombre_proveedor;
	}
	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMedio_pago() {
		return medio_pago;
	}
	public void setMedio_pago(String medio_pago) {
		this.medio_pago = medio_pago;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "BeanCompra [codigo_compra=" + codigo_compra + ", nombre_empleado=" + nombre_empleado + ", nombre_proveedor="
				+ nombre_proveedor + ", fecha=" + fecha + ", medio_pago=" + medio_pago + ", total=" + total + "]";
	}
}
