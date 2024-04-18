package hn.ventaderepuestos.data;

public class GaleriaRepuesto {

    private int repuestoid;
    private String nombre;

    public int getRepuestoid() {
        return repuestoid;
    }

    public void setRepuestoid(int repuestoid) {
        this.repuestoid = repuestoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return "https://picsum.photos/200/300";
    }
}
