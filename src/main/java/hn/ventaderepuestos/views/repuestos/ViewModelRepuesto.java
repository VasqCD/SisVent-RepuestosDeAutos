package hn.ventaderepuestos.views.repuestos;

import java.util.List;

import hn.ventaderepuestos.data.Repuesto;

public interface ViewModelRepuesto {

	void mostrarRepuestoEnGrid(List<Repuesto> items);
	
	void mostrarMensajeError(String mensaje);

}
