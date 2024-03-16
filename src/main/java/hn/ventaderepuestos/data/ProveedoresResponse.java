package hn.ventaderepuestos.data;

import java.util.List;

public class ProveedoresResponse {
	
	private List<Proveedor> items;
	private int count;
	public List<Proveedor> getItems() {
		return items;
	}
	public void setItems(List<Proveedor> items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
