package hn.ventaderepuestos.data;

public class Orden {
	private String repuestoid;
    private String proveedorid;
    private String cantidad;
    private String precio;
    private String fecha;
    private String observaciones;
    private String estado;
    private String tipo;
    
	public String getRepuestoid() {
		return repuestoid;
	}
	public void setRepuestoid(String repuestoid) {
		this.repuestoid = repuestoid;
	}
	public String getProveedorid() {
		return proveedorid;
	}
	public void setProveedorid(String proveedorid) {
		this.proveedorid = proveedorid;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    

}
