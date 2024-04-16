package hn.ventaderepuestos.views.ordenes;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import hn.ventaderepuestos.controller.InteractorImplOrden;
import hn.ventaderepuestos.controller.InteractorOrden;
import hn.ventaderepuestos.data.Orden;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.views.MainLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@PageTitle("Ordenes")
@Route(value = "ordenes", layout = MainLayout.class)
@Uses(Icon.class)
public class OrdenesView extends Composite<VerticalLayout> implements ViewModelOrden {

    private final Grid<Orden> historialOrdenes = new Grid<>(Orden.class, false);

    private List<Orden> elementos;
    private InteractorOrden controlador;
    
    private ComboBox<Proveedor> proveedor;
    private List<Proveedor> proveedores;
    private Proveedor proveedorSeleccionado;

    public OrdenesView() {

        controlador = new InteractorImplOrden((ViewModelOrden) this);
        elementos = new ArrayList<>();
        proveedores = new ArrayList<>();


        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        Hr hr = new Hr();
        HorizontalLayout layoutRow = new HorizontalLayout();

        Checkbox ventaCheckbox = new Checkbox();
        Checkbox compraCheckbox = new Checkbox();
        ComboBox repuestoComboBox = new ComboBox();
        ComboBox proveedorComboBox = new ComboBox();
        
        

        FormLayout formLayout2Col = new FormLayout();
        TextField txtCantidad = new TextField();
        TextField txtObservaciones = new TextField();
        //DatePicker fechaCompra = new DatePicker();
        TextField txtEstado = new TextField();
        TextField txtTipo = new TextField();

        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        Hr hr2 = new Hr();
        H3 h32 = new H3();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Nueva orden");
        h3.setWidth("100%");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");

        ventaCheckbox.setLabel("Venta");
        ventaCheckbox.setWidth("min-content");

        ventaCheckbox.addValueChangeListener(event -> {
            if (event.getValue()) {
                proveedorComboBox.setReadOnly(true);
                compraCheckbox.setValue(false);
                txtTipo.setValue("Venta");
                txtTipo.setReadOnly(true);
            } else {
                proveedorComboBox.setReadOnly(false);
                txtTipo.setValue("");
                txtTipo.setReadOnly(false);
            }
        });

        compraCheckbox.setLabel("Compra");
        compraCheckbox.setWidth("min-content");
        formLayout2Col.setWidth("100%");

        compraCheckbox.addValueChangeListener(event -> {
            if (event.getValue()) {
                ventaCheckbox.setValue(false);
                txtTipo.setValue("Compra");
                txtTipo.setReadOnly(true);
            } else {
                repuestoComboBox.setReadOnly(false);
                txtTipo.setValue("");
                txtTipo.setReadOnly(false);
            }
        });

        repuestoComboBox.setLabel("Repuesto");
        repuestoComboBox.setWidth("min-content");
        setComboBoxSampleData(repuestoComboBox);

        proveedorComboBox.setLabel("Proveedor");
        proveedorComboBox.setWidth("min-content");
        setComboBoxSampleData(proveedorComboBox);
        
        txtCantidad.setLabel("Cantidad");
        txtObservaciones.setLabel("Observaciones");
        //fechaCompra.setLabel("Fecha de compra");
        txtEstado.setLabel("Estado de compra"); //
        txtTipo.setLabel("Tipo");
        //textField4.setLabel("Occupation");
        
 
        
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Guardar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Cancelar");
        buttonSecondary.setWidth("min-content");
        h32.setText("Historial de ordenes");
        h32.setWidth("max-content");

        //PARTE DEL GRID

        historialOrdenes.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        historialOrdenes.setWidth("100%");
        historialOrdenes.getStyle().set("flex-grow", "0");


        historialOrdenes.addColumn("ordenid").setAutoWidth(true);
        //historialOrdenes.addColumn("repuestoid").setAutoWidth(true);
        historialOrdenes.addColumn("nombre_repuesto").setAutoWidth(true);
        historialOrdenes.addColumn("nombre_proveedor").setAutoWidth(true);
        //historialOrdenes.addColumn("proveedorid").setAutoWidth(true);
        historialOrdenes.addColumn("cantidad").setAutoWidth(true);
        historialOrdenes.addColumn("fecha").setAutoWidth(true);
        historialOrdenes.addColumn("observaciones").setAutoWidth(true);
        historialOrdenes.addColumn("estado").setAutoWidth(true);
        historialOrdenes.addColumn("tipo").setAutoWidth(true);


        controlador.consultarOrden();

        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(ventaCheckbox);
        layoutRow.add(compraCheckbox);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(repuestoComboBox);
        formLayout2Col.add(proveedorComboBox);
        formLayout2Col.add(txtCantidad);
        //formLayout2Col.add(fechaCompra);
        formLayout2Col.add(txtObservaciones);
        
        formLayout2Col.add(txtEstado);
        formLayout2Col.add(txtTipo);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
        layoutColumn2.add(hr2);
        layoutColumn2.add(h32);

        layoutColumn2.add(historialOrdenes);

    }

    @Override
    public void mostrarMensajeError(String mensaje) {

    }

    @Override
    public void mostrarOrdenEnGrid(Collection<Orden> ordenes) {
        historialOrdenes.setItems(ordenes);
    }

    @Override
    public void mostrarMensajeExito(String mensaje) {

    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    public void refresGrid() {
        historialOrdenes.select(null);
        historialOrdenes.getDataProvider().refreshAll();
        this.controlador.consultarOrden();
    }
    
    private Proveedor buscarProveedor(int proveedorid) {
    	Proveedor encontrado = null;
    	for(Proveedor prov: proveedores) {
    		if(prov.getProveedorid() == proveedorid) {
    			encontrado = prov;
    			break;
    		}
    	}
    	return encontrado;
    }
    
    
}