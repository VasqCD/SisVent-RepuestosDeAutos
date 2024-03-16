package hn.ventaderepuestos.controller;

import hn.ventaderepuestos.data.RepuestoResponse;
import hn.ventaderepuestos.model.DatabaseRepositoryImpl;
import hn.ventaderepuestos.views.repuestos.ViewModelRepuesto;

public class InteractorImplRepuesto implements InteractorRepuesto{
	
	private DatabaseRepositoryImpl modelo;
	private ViewModelRepuesto vista;
	
	public InteractorImplRepuesto(ViewModelRepuesto view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
		//https://apex.oracle.com/pls/apex/cvasq/svra/Repuesto
		
	}
	
	@Override
	public void consultarRepuesto() {
		try {
			RepuestoResponse respuesta = this.modelo.consultarRepuesto();
			if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay repuestos a mostrar");
			}else {
				this.vista.mostrarRepuestoEnGrid(respuesta.getItems());
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
	}

}
