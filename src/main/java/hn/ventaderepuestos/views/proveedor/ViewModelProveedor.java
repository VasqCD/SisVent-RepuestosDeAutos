package hn.ventaderepuestos.views.proveedor;

import java.util.List;

import hn.ventaderepuestos.data.Proveedor;

public interface ViewModelProveedor {
	
	void mostrarProveedorEnGrid(List<Proveedor> items);
	
	void mostrarMensajeError(String mensaje);
	
	void mostrarMensajeExito(String mensaje);
	

}
