package hn.ventaderepuestos.data;

public class GaleriaRepuesto {

    private int repuestoid;
    private String nombre;
    private String marca;
    private String imagen;
    private String estado;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
}
