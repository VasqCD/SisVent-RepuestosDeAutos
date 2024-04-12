package hn.ventaderepuestos.controller;

import hn.ventaderepuestos.data.Orden;

public interface InteractorOrden {

        void consultarOrden();
        void crearOrden(Orden nueva);
}
