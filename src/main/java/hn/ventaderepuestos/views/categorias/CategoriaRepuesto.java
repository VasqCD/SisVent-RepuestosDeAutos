package hn.ventaderepuestos.views.categorias;

public class CategoriaRepuesto {

    private int id;
    private String img;
    private String nombreCategoria;
    private double descripcion;
    private String estado;
    private String fechaCreacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClient() {
        return nombreCategoria;
    }

    public void setClient(String cliente) {
        this.nombreCategoria = cliente;
    }

    public double getAmount() {
        return descripcion;
    }

    public void setAmount(double monto) {
        this.descripcion = monto;
    }

    public String getStatus() {
        return estado;
    }

    public void setStatus(String estado) {
        this.estado = estado;
    }

    public String getDate() {
        return fechaCreacion;
    }

    public void setDate(String dato) {
        this.fechaCreacion = dato;
    }
}
