package hn.ventaderepuestos.views.repuestos;

import java.util.List;

import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.Repuesto;

public interface ViewModelRepuesto {

	void mostrarRepuestoEnGrid(List<Repuesto> items);

	void mostrarProveedoresEnCombobox(List<Proveedor> items);
	
	void mostrarMensajeError(String mensaje);
	
	void mostrarMensajeExito(String mensaje);

}
