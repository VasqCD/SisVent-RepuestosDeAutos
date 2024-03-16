package hn.ventaderepuestos.controller;



import hn.ventaderepuestos.data.ProveedoresResponse;
import hn.ventaderepuestos.model.DatabaseRepositoryImpl;
import hn.ventaderepuestos.views.proveedor.ViewModelProveedor;

public class InteractorImplProveedor implements InteractorProveedor {
	
	private DatabaseRepositoryImpl modelo;
	private ViewModelProveedor vista;
	
	public InteractorImplProveedor(ViewModelProveedor view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
		//https://apex.oracle.com/pls/apex/cvasq/svra/Proveedor
		
	}
	
	@Override
	public void consultarProveedor() {
		try {
			ProveedoresResponse respuesta = this.modelo.consultarProveedor();
			if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay empleados a mostrar");
			}else {
				this.vista.mostrarProveedorEnGrid(respuesta.getItems());
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}

}
