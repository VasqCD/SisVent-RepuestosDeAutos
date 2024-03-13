package hn.ventaderepuestos.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import java.time.LocalDate;

@Entity
public class Repuesto extends AbstractEntity {

    @Lob
    @Column(length = 1000000)
    private byte[] imagen;
    private String nombreRepuesto;
    private String precioUnitario;
    private LocalDate fechaIngreso;
    private Integer unidadesStock;
    private String estado;
    
    
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public String getNombreRepuesto() {
		return nombreRepuesto;
	}
	public void setNombreRepuesto(String nombreRepuesto) {
		this.nombreRepuesto = nombreRepuesto;
	}
	public String getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Integer getUnidadesStock() {
		return unidadesStock;
	}
	public void setUnidadesStock(Integer unidadesStock) {
		this.unidadesStock = unidadesStock;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

    
}
