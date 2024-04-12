package hn.ventaderepuestos.controller;

import hn.ventaderepuestos.model.DatabaseRepositoryImpl;
import hn.ventaderepuestos.data.Orden;
import hn.ventaderepuestos.data.OrdenesResponse;
import hn.ventaderepuestos.views.ordenes.ViewModelOrden;

public class InteractorImplOrden implements InteractorOrden {

    private DatabaseRepositoryImpl modelo;
    private ViewModelOrden vista;

    public InteractorImplOrden(ViewModelOrden view) {
        super();
        this.vista = view;
        this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
        //https://apex.oracle.com/pls/apex/cvasq/svra/Orden

    }

    @Override
    public void consultarOrden() {
        try {
            OrdenesResponse respuesta = this.modelo.consultarOrden();
            if (respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
                this.vista.mostrarMensajeError("No hay orden a mostrar");
            } else {
                this.vista.mostrarOrdenEnGrid(respuesta.getItems());
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    @Override
    public void crearOrden(Orden nueva) {
        try {
            boolean creado = this.modelo.crearOrden(nueva);
            if (creado == true) {
                this.vista.mostrarMensajeExito("Orden creada exitosamente");
            } else {
                this.vista.mostrarMensajeError("Hay un problema al crear la orden");
            }
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

}
