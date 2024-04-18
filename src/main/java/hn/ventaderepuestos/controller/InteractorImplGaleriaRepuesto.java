package hn.ventaderepuestos.controller;


import hn.ventaderepuestos.data.GaleriaRepuestoResponse;
import hn.ventaderepuestos.model.DatabaseRepositoryImpl;
import hn.ventaderepuestos.views.galeriarepuestos.ViewModelGaleriaRepuestos;

public class InteractorImplGaleriaRepuesto implements InteractorGaleriaRepuesto {

    private DatabaseRepositoryImpl modelo;
    private ViewModelGaleriaRepuestos vista;

    public InteractorImplGaleriaRepuesto(ViewModelGaleriaRepuestos view) {
        super();
        this.vista = view;
        this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
        //https://apex.oracle.com/pls/apex/cvasq/svra/Repuesto

    }

    @Override
    public void consultarGaleriaRepuesto() {
        try {
            GaleriaRepuestoResponse respuesta = this.modelo.consultarGaleriaRepuesto();
            if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
                this.vista.mostrarMensajeError("No hay repuestos a mostrar");
            }else {
                this.vista.mostrarGaleriaRepuesto(respuesta.getItems());
            }
        }catch(Exception error) {
            error.printStackTrace();
        }
    }

}
