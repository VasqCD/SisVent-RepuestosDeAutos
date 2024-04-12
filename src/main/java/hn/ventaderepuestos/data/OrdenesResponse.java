package hn.ventaderepuestos.data;

import java.util.List;

public class OrdenesResponse {

    private List<Orden> items;
    private int count;
    public List<Orden> getItems() {
        return items;
    }
    public void setItems(List<Orden> items) {
        this.items = items;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}
