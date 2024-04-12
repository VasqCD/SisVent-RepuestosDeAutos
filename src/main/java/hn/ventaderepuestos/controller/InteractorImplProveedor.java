package hn.ventaderepuestos.controller;



import hn.ventaderepuestos.data.Proveedor;
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
				this.vista.mostrarMensajeError("No hay proveedor a mostrar");
			}else {
				this.vista.mostrarProveedorEnGrid(respuesta.getItems());
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}
	
	@Override
	public void crearProveedor(Proveedor nuevo) {
		try {
			boolean  creado = this.modelo.crearProveedor(nuevo);
			if(creado == true) {
				this.vista.mostrarMensajeExito("Proveedor creado exitosamente");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al crear el proveedor");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}

	@Override
	public void actualizarProveedor(Proveedor cambiar) {
		try {
			boolean modificado = this.modelo.actualizarProveedor(cambiar);
			if(modificado == true) {
				this.vista.mostrarMensajeExito("Proveedor modificado exitosamente");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al modificar el proveedor");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}

	@Override
	public void eliminarProveedor(String codigoid) {
		try {
			boolean eliminado = this.modelo.eliminarProveedor(codigoid);
			if(eliminado == true) {
				this.vista.mostrarMensajeExito("Proveedor eliminado exitosamente");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al eliminar el proveedor");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}

}
