package hn.ventaderepuestos.views.repuestos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import hn.ventaderepuestos.controller.InteractorImplRepuesto;
import hn.ventaderepuestos.controller.InteractorRepuesto;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.views.MainLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Repuestos")
@Route(value = "repuestos/:repuestoid?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class RepuestosView extends Div implements BeforeEnterObserver, ViewModelRepuesto {

    private final String SAMPLEBOOK_ID = "repuestoid";
    private final String SAMPLEBOOK_EDIT_ROUTE_TEMPLATE = "repuestos/%s/edit";

    private final Grid<Repuesto> grid = new Grid<>(Repuesto.class, false);

    private TextField repuestoid;
    private TextField nombre;
    private TextField marca;
    private TextField precio;
    private NumberField stock;
    private TextField estado;
    private ComboBox<Proveedor> proveedor;

    private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    private final Button eliminar = new Button("Eliminar");

    private Repuesto repuestoSeleccionado;
    private List<Repuesto> elementos;
    private List<Proveedor> proveedores;
    private InteractorRepuesto controlador;
    private Proveedor proveedorSeleccionado;


    public RepuestosView() {
       
        addClassNames("repuestos-view");
        
        controlador = new InteractorImplRepuesto(this);
        elementos = new ArrayList<>();
        proveedores = new ArrayList<>();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("repuestoid").setAutoWidth(true).setHeader("Codigo");
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("marca").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
        grid.addColumn("stock").setAutoWidth(true);
        grid.addColumn("nombre_proveedor").setAutoWidth(true).setHeader("Proveedor");
        grid.addColumn(estiloEstado()).setHeader("Estado").setAutoWidth(true);
        //grid.addColumn("estado").setAutoWidth(true);

        

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEBOOK_EDIT_ROUTE_TEMPLATE, event.getValue().getRepuestoid()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RepuestosView.class);
            }
        });
        
        controlador.consultarRepuesto();
        controlador.consultarProveedor();
        
        // Configure Form
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        stock.addValueChangeListener(e -> {
            // Cambiar estado de acuerdo a stock
            estado.setValue(e.getValue().intValue() > 0 ? "Activo" : "Inactivo");
        });

        //BOTON GUARDAR
        guardar.addClickListener(e -> {
            try {
                if (this.repuestoSeleccionado == null) {
                    //creacion
                    this.repuestoSeleccionado = new Repuesto();

                    this.repuestoSeleccionado.setRepuestoid(Integer.parseInt(repuestoid.getValue()));
                    this.repuestoSeleccionado.setNombre(nombre.getValue());
                    this.repuestoSeleccionado.setMarca(marca.getValue());
                    this.repuestoSeleccionado.setPrecio(precio.getValue());
                    int stockValue = stock.getValue().intValue();
                    this.repuestoSeleccionado.setStock(stockValue);
                    // ESTADO EN BASE AL STOCK
                    this.repuestoSeleccionado.setEstado(stockValue > 0 ? "Activo" : "Inactivo");
                    this.repuestoSeleccionado.setProveedor(String.valueOf(proveedor.getValue().getProveedorid()));
                    
                    this.controlador.crearRepuesto(repuestoSeleccionado);
                }else {
                	//actualizacion
                    //this.repuestoSeleccionado.setRepuestoid(Integer.parseInt(repuestoid.getValue()));
                    this.repuestoSeleccionado.setNombre(nombre.getValue());
                    this.repuestoSeleccionado.setMarca(marca.getValue());
                    this.repuestoSeleccionado.setPrecio(precio.getValue());
                    int stockValue = stock.getValue().intValue();
                    this.repuestoSeleccionado.setStock(stockValue);
                    // ESTADO EN BASE AL STOCK
                    this.repuestoSeleccionado.setEstado(stockValue > 0 ? "Activo" : "Inactivo");
                    this.repuestoSeleccionado.setProveedor(String.valueOf(proveedor.getValue().getProveedorid()));

                    this.controlador.actualizarRepuesto(repuestoSeleccionado);
                }

            
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(RepuestosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        // BOTON ELIMINAR
        eliminar.addClickListener(e -> {
            if (repuestoSeleccionado == null) {
                mostrarMensajeError("Seleccione un repuesto para poder eliminar");
            } else {
                this.controlador.eliminarRepuesto(String.valueOf(repuestoSeleccionado.getRepuestoid()));
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(RepuestosView.class);
            }
        });
    }

    private static final SerializableBiConsumer<Span, Repuesto> statusComponentUpdater = (
            span, repuesto) -> {
        boolean estadoActivo = "Activo".equals(repuesto.getEstado());
        String theme = String.format("badge %s",
                estadoActivo ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(repuesto.getEstado());
    };

    private static ComponentRenderer<Span, Repuesto> estiloEstado() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    //SE EJECUTA AL SELECCIONAR UN ELEMENTO DEL GRID
    @Override
public void beforeEnter(BeforeEnterEvent event) {
    Optional<String> codigoRepuesto = event.getRouteParameters().get(SAMPLEBOOK_ID);
    if (codigoRepuesto.isPresent()) {
        Repuesto repuestoObtenido = obtenerRepuesto(codigoRepuesto.get());
        if (repuestoObtenido != null) {
            populateForm(repuestoObtenido);
        } else {
            Notification.show(
                    String.format("El repuesto con ID = %s no existe", codigoRepuesto.get()),
                    3000, Notification.Position.BOTTOM_START);
            refreshGrid();
            event.forwardTo(RepuestosView.class);
        }
    }
}
    
    private Repuesto obtenerRepuesto(String repuestoid) {
    Repuesto encontrado = null;
    for(Repuesto rep: elementos) {
        if(rep.getRepuestoid() == Integer.parseInt(repuestoid)) {
            encontrado = rep;
            break;
        }
    }
    return encontrado;
}

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        repuestoid = new TextField("Codigo de repuesto");
        repuestoid.setId("txt_codigoRepuesto");
        repuestoid.setValue("0");
        repuestoid.setEnabled(false);

        nombre = new TextField("Nombre de repuesto");
        nombre.setId("txt_nombreRepuesto");

        marca = new TextField("Marca de repuesto");
        marca.setId("txt_marcaRepuesto");

        precio = new TextField("Precio unitario");
        precio.setId("txt_precioUnitario");

        stock = new NumberField("Unidades en Stock");
        stock.setId("txt_unidades");

        proveedor = new ComboBox<>("Proveedor");
        proveedor.setId("txt_proveedor");
        proveedor.setItemLabelGenerator(Proveedor::getNombre);

        estado = new TextField("Estado");
        estado.setId("txt_estado");
        estado.setVisible(false); // No mostrar el campo de estado, ya que se calcula en base a stock

        //metodo add agrega el control a la pantalla
        formLayout.add(repuestoid, nombre, marca, precio, stock, proveedor, estado);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");

        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelar.setId("btn_cancelar");

        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.setId("btn_guardar");

        eliminar.setId("btn_eliminar");
        eliminar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        
        buttonLayout.add(guardar, cancelar, eliminar);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
        this.controlador.consultarRepuesto();
        this.controlador.consultarProveedor();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Repuesto value) {
       this.repuestoSeleccionado = value;
       if(value != null) {
           repuestoid.setValue(String.valueOf(value.getRepuestoid()));
    	   nombre.setValue(value.getNombre());
    	   marca.setValue(value.getMarca());
    	   precio.setValue(value.getPrecio());
           stock.setValue(Double.valueOf(value.getStock()));
           proveedorSeleccionado = buscarProveedor(Integer.parseInt(value.getProveedor()));
              proveedor.setValue(proveedorSeleccionado);
    	   estado.setValue(value.getEstado());
    	   
       }else {
           repuestoid.setValue("0");
    	   nombre.setValue("");
    	   marca.setValue("");
    	   precio.setValue("");
    	   stock.setValue(0.0);
           proveedor.clear();
    	   estado.setValue("Activo");

       }
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
    
    @Override
    public void mostrarRepuestoEnGrid(List<Repuesto> items) {
    	Collection<Repuesto> itemsCollection = items;
    	grid.setItems(itemsCollection);
    	this.elementos = items;
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        Notification n = Notification.show(mensaje);
        n.setPosition(Position.MIDDLE);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    @Override
    public void mostrarProveedoresEnCombobox(List<Proveedor> items) {
    	Collection<Proveedor> itemsCollection = items;
    	proveedores = items;
    	proveedor.setItems(items);
    }
    

    
    @Override
    public void mostrarMensajeExito(String mensaje) {
    	Notification.show(mensaje);
    }
    
}
