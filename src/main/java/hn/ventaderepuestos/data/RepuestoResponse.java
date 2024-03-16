package hn.ventaderepuestos.data;

import java.util.List;

public class RepuestoResponse {

	private List<Repuesto> items;
	private int count;
	public List<Repuesto> getItems() {
		return items;
	}
	public void setItems(List<Repuesto> items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
