package hn.ventaderepuestos.data;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.List;

public class RepuestosReport implements JRDataSource {

    private List<Repuesto> repuestos;
    private int counter = -1;
    private int maxCounter = 0;

    public List<Repuesto> getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(List<Repuesto> repuestos) {
        this.repuestos = repuestos;
        this.maxCounter = this.repuestos.size()-1;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getMaxCountrer() {
        return maxCounter;
    }

    public void setMaxCountrer(int maxCountrer) {
        this.maxCounter = maxCountrer;
    }

    @Override
    public boolean next() throws JRException {
        if(counter < maxCounter){
            counter++;
            return true; //hay mas datos aun
        }
        return false; //no hay mas datos
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if ("REPUESTOID".equals(jrField.getName())) {
            return repuestos.get(counter).getRepuestoid();
        } else if ("NOMBRE".equals(jrField.getName())) {
            return repuestos.get(counter).getNombre();
        } else if ("MARCA".equals(jrField.getName())) {
            return repuestos.get(counter).getMarca();
        } else if ("NOMBRE_PROVEEDOR".equals(jrField.getName())) {
            return repuestos.get(counter).getNombre_proveedor();
        }else if ("ESTADO".equals(jrField.getName())) {
            return repuestos.get(counter).getEstado();
        }
        return "";
    }
}
