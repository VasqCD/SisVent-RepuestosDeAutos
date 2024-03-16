package hn.ventaderepuestos.views.proveedor;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

import hn.ventaderepuestos.controller.InteractorImplProveedor;
import hn.ventaderepuestos.controller.InteractorProveedor;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.views.MainLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Proveedor")
@Route(value = "proveedor/:nombre?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class ProveedorView extends Div implements BeforeEnterObserver, ViewModelProveedor {

    private final String SAMPLEPERSON_ID = "nombre";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "proveedor/%s/edit";

    private final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private TextField nombre;
    private TextField direccion;
    private TextField correo;
    private TextField telefono;
    private TextField pais;
    private TextField estado;


    private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    private final Button eliminar = new Button("Eliminar", new Icon(VaadinIcon.TRASH));

    private Proveedor proveedorSeleccionado;
    private List<Proveedor> elementos;
    private InteractorProveedor controlador;
    
    
    public ProveedorView() {
        
        addClassNames("proveedor-view");
        
        controlador = new InteractorImplProveedor(this);
        elementos = new ArrayList<>();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true);
        grid.addColumn("correo").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("pais").setAutoWidth(true);
        grid.addColumn("estado").setAutoWidth(true);
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ProveedorView.class);
            }
        });
        
        controlador.consultarProveedor();
        

        // Configure Form
       

        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        guardar.addClickListener(e -> {
            try {
                if (this.proveedorSeleccionado == null) {
                    this.proveedorSeleccionado = new Proveedor();
                }
                
                clearForm();
                refreshGrid();
                Notification.show("Datos actualizados");
                UI.getCurrent().navigate(ProveedorView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        eliminar.addClickListener( e-> {
       	 Notification n = Notification.show("Botón eliminar seleccionado, aún no hay nada que eliminar");
       	 n.setPosition(Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_WARNING);
       });
    }
    
    //metodo que se ejecuta al seleccionar u elemento del grid

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> nombre = event.getRouteParameters().get(SAMPLEPERSON_ID);
        if (nombre.isPresent()) {
        	Proveedor proveedorObtenido = obtenerProveedor(nombre.get());
            if (proveedorObtenido != null) {
                populateForm(proveedorObtenido);
            } else {
                Notification.show(
                        String.format("El proveedor con nombre = %s no existe", nombre.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ProveedorView.class);
            }
            
        }
    }
    
    private Proveedor obtenerProveedor(String nombre) {
		Proveedor encontrado = null;
    	for(Proveedor prov: elementos) {
			if(prov.getNombre().equals(nombre)) {
				encontrado = prov;
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
        nombre = new TextField("Nombre Proveedor");
        nombre.setId("txt_nomProveedor");
        direccion = new TextField("Direccion");
        direccion.setId("txt_Direccion");
        correo = new TextField("Correo Electronico");
        correo.setId("txt_correo");
        telefono = new TextField("Telefono");
        telefono.setId("txt_telefono");
        pais = new TextField("Pais");
        pais.setId("txt_pais");
        estado = new TextField("Estado");
        estado.setId("txt_estado");

        
        formLayout.add(nombre, direccion, correo, telefono, pais, estado);

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
        guardar.setId("btn_gurdar");
        eliminar.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        eliminar.setId("btn_eliminar");
        
        buttonLayout.add(guardar, eliminar, cancelar);
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
    }

    private void clearForm() {
        populateForm(null);
    }
    

    private void populateForm(Proveedor value) {
    	this.proveedorSeleccionado = value;
        if(value != null) {
        	
        	nombre.setValue(value.getNombre());
        	direccion.setValue(value.getDireccion());
        	correo.setValue(value.getCorreo());
        	telefono.setValue(value.getCorreo());
        	pais.setValue(value.getPais());
        	estado.setValue(value.getEstado());
        	
        }else {
        	nombre.setValue("");
        	direccion.setValue("");
        	correo.setValue("");
        	telefono.setValue("");
        	pais.setValue("");
        	estado.setValue("");
        }  

    }
    
    @Override
	public void mostrarProveedorEnGrid(List<Proveedor> items) {
		Collection<Proveedor> itemsCollection = items;
		grid.setItems(itemsCollection);
		this.elementos = items;
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		Notification.show(mensaje);
	}
	
}
