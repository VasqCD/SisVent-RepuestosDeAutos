package hn.ventaderepuestos.controller;

import hn.ventaderepuestos.data.Proveedor;

public interface InteractorProveedor {
	
	void consultarProveedor();
	void crearProveedor(Proveedor nuevo);

    void actualizarProveedor(Proveedor cambiar);
	void eliminarProveedor(String proveedorid);

}
