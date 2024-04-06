package hn.ventaderepuestos.views.repuestos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import hn.ventaderepuestos.controller.InteractorImplRepuesto;
import hn.ventaderepuestos.controller.InteractorRepuesto;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.views.MainLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Repuestos")
@Route(value = "repuestos/:nombre?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class RepuestosView extends Div implements BeforeEnterObserver, ViewModelRepuesto {

    private final String SAMPLEBOOK_ID = "nombre";
    private final String SAMPLEBOOK_EDIT_ROUTE_TEMPLATE = "repuestos/%s/edit";

    private final Grid<Repuesto> grid = new Grid<>(Repuesto.class, false);


    private TextField nombre;
    private TextField marca;
    private TextField precio;
    private TextField stock;
    private TextField estado;
    private TextField proveedor;

    private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    private final Button eliminar = new Button("Eliminar");

    private Repuesto repuestoSeleccionado;
    private List<Repuesto> elementos;
    private InteractorRepuesto controlador;


    public RepuestosView() {
       
        addClassNames("repuestos-view");
        
        controlador = new InteractorImplRepuesto(this);
        elementos = new ArrayList<>();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid

        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("marca").setAutoWidth(true);
        grid.addColumn("precio").setAutoWidth(true);
        grid.addColumn("stock").setAutoWidth(true);
        grid.addColumn("estado").setAutoWidth(true);
        grid.addColumn("proveedor").setAutoWidth(true);
        

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEBOOK_EDIT_ROUTE_TEMPLATE, event.getValue().getNombre()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RepuestosView.class);
            }
        });
        
        controlador.consultarRepuesto();

        
        // Configure Form
        
    

        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        //BOTON GUARDAR
        guardar.addClickListener(e -> {
            try {
                if (this.repuestoSeleccionado == null) {
                    this.repuestoSeleccionado = new Repuesto();
                    
                    this.repuestoSeleccionado.setNombre(nombre.getValue());
                    this.repuestoSeleccionado.setMarca(marca.getValue());
                    this.repuestoSeleccionado.setPrecio(precio.getValue());
                    this.repuestoSeleccionado.setStock(Integer.parseInt(stock.getValue()));
                    this.repuestoSeleccionado.setEstado(estado.getValue());
                    this.repuestoSeleccionado.setProveedor(proveedor.getValue());
                    
                    this.controlador.crearRepuesto(repuestoSeleccionado);
                }else {
                	
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
        
        // BOTN ELIMINAR
        eliminar.addClickListener( e-> {
          	 Notification n = Notification.show("Botón eliminar seleccionado, aún no hay nada que eliminar");
          	 n.setPosition(Position.MIDDLE);
               n.addThemeVariants(NotificationVariant.LUMO_WARNING);
          });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> nombre = event.getRouteParameters().get(SAMPLEBOOK_ID);
        if (nombre.isPresent()) {
        	Repuesto repuestoObtenido = obtenerRepuesto(nombre.get());
            if (repuestoObtenido != null) {
                populateForm(repuestoObtenido);
            } else {
                Notification.show(String.format("El repuesto de nombre = %s no existe", nombre.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(RepuestosView.class);
            }
        }
    }
    
    private Repuesto obtenerRepuesto(String nombre) {
    	Repuesto encontrado = null;
    	for(Repuesto rep: elementos) {
    		if(rep.getNombre().equals(nombre)) {
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
        nombre = new TextField("Nombre de repuesto");
        nombre.setId("txt_nombreRepuesto");
        marca = new TextField("Marca de repuesto");
        marca.setId("txt_marcaRepuesto");
        precio = new TextField("Precio unitario");
        precio.setId("txt_precioUnitario");
        stock = new TextField("Unidades en Stock");
        stock.setId("txt_unidades");
        estado = new TextField("Estado");
        estado.setId("txt_estado");
        proveedor = new TextField("Proveedor");
        proveedor.setId("txt_proveedor");
       
        formLayout.add(nombre, marca, precio, stock, estado, proveedor);

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
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        eliminar.setId("btn_eliminar");
        
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
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Repuesto value) {
       this.repuestoSeleccionado = value;
       if(value != null) {
    	   nombre.setValue(value.getNombre());
    	   marca.setValue(value.getMarca());
    	   precio.setValue(value.getPrecio());
    	   stock.setValue(String.valueOf(value.getStock()));
    	   estado.setValue(value.getEstado());
    	   proveedor.setValue(value.getProveedor());
    	   
       }else {
    	   nombre.setValue("");
    	   marca.setValue("");
    	   precio.setValue("");
    	   stock.setValue("");
    	   estado.setValue("");
    	   proveedor.setValue("");
       }

    }
    
    @Override
    public void mostrarRepuestoEnGrid(List<Repuesto> items) {
    	Collection<Repuesto> itemsCollection = items;
    	grid.setItems(itemsCollection);
    	this.elementos = items;
    }
    
    @Override
    public void mostrarMensajeError(String mensaje) {
    	Notification.show(mensaje);
    }
    
    @Override
    public void mostrarMensajeExito(String mensaje) {
    	Notification.show(mensaje);
    }
    
}
