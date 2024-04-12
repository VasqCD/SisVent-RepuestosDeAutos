package hn.ventaderepuestos.views.ordenes;

import hn.ventaderepuestos.data.Orden;

import java.util.Collection;

public interface ViewModelOrden {
    void mostrarMensajeError(String mensaje);
    void mostrarOrdenEnGrid(Collection<Orden> ordenes);
    void mostrarMensajeExito(String mensaje);
}
