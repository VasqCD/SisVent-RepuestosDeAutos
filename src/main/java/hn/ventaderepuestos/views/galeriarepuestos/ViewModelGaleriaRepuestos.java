package hn.ventaderepuestos.views.galeriarepuestos;

import hn.ventaderepuestos.data.GaleriaRepuesto;

import java.util.List;

public interface ViewModelGaleriaRepuestos {

    void mostrarMensajeError(String mensaje);
    void mostrarGaleriaRepuesto(List<GaleriaRepuesto> items);

}
