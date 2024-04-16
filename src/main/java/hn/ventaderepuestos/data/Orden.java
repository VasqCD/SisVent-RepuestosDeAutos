package hn.ventaderepuestos.data;

public class Orden {

	private int ordenid;
	private int repuestoid;
	private int proveedorid;
	private int cantidad;
	private String fecha;
	private String observaciones;
	private int estado;
	private String tipo;
	private String nombre_repuesto;
	private String nombre_proveedor;

	public int getOrdenid() {
		return ordenid;
	}

	public void setOrdenid(int ordenid) {
		this.ordenid = ordenid;
	}

	public int getRepuestoid() {
		return repuestoid;
	}

	public void setRepuestoid(int repuestoid) {
		this.repuestoid = repuestoid;
	}

	public int getProveedorid() {
		return proveedorid;
	}

	public void setProveedorid(int proveedorid) {
		this.proveedorid = proveedorid;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre_repuesto() {
		return nombre_repuesto;
	}

	public void setNombre_repuesto(String nombre_repuesto) {
		this.nombre_repuesto = nombre_repuesto;
	}

	public String getNombre_proveedor() {
		return nombre_proveedor;
	}

	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}
	
	
}
