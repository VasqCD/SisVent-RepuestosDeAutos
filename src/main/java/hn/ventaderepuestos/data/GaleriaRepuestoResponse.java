package hn.ventaderepuestos.data;

import java.util.List;

public class GaleriaRepuestoResponse {

    private List<GaleriaRepuesto> items;
    private int count;

    public List<GaleriaRepuesto> getItems() {
        return items;
    }

    public void setItems(List<GaleriaRepuesto> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
