package hn.ventaderepuestos.controller;

import hn.ventaderepuestos.data.Repuesto;

public interface InteractorRepuesto {

	void consultarRepuesto();
	void consultarProveedor();
	void crearRepuesto(Repuesto nuevo);
	void actualizarRepuesto(Repuesto cambiar);
	void eliminarRepuesto(String repuestoid);

}
