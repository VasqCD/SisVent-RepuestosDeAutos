package hn.ventaderepuestos.views.proveedor;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import hn.ventaderepuestos.controller.InteractorProveedor;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.services.SamplePersonService;
import hn.ventaderepuestos.views.MainLayout;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Proveedor")
@Route(value = "proveedor/:nombreEmpleado?/:action?(edit)", layout = MainLayout.class)
@Uses(Icon.class)
public class ProveedorView extends Div implements BeforeEnterObserver, ViewModelProveedor {

    private final String SAMPLEPERSON_ID = "nombreProveedor";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "proveedor/%s/edit";

    private final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private TextField nombreProveedor;
    private TextField direccion;
    private TextField correo;
    private TextField telefono;
    private DatePicker fechaContratacion;
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

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombreProveedor").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true);
        grid.addColumn("correo").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("fechaContratacion").setAutoWidth(true);
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
        Optional<String> proveedorNombr = event.getRouteParameters().get(SAMPLEPERSON_ID);
        if (proveedorNombr.isPresent()) {
        	Proveedor proveedorObtenido = obtenerProveedor(proveedorNombr.get());
            if (proveedorObtenido != null) {
                populateForm(proveedorObtenido);
            } else {
                Notification.show(
                        String.format("El proveedor con nombre = %s no existe", proveedorNombr.get()), 3000,
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
			if(prov.getNombreProveedor().equals(nombre)) {
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
        nombreProveedor = new TextField("Nombre Proveedor");
        direccion = new TextField("Direccion");
        correo = new TextField("Correo Electronico");
        telefono = new TextField("Telefono");
        fechaContratacion = new DatePicker("Fecha Contratacion");
        pais = new TextField("Pais");
        estado = new TextField("Estado");

        
        formLayout.add(nombreProveedor, direccion, correo, telefono, fechaContratacion, pais, estado);

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
        	
        	nombreProveedor.setValue(value.getNombreProveedor());
        	direccion.setValue(value.getDireccion());
        	correo.setValue(value.getCorreo());
        	telefono.setValue(value.getCorreo());
        	fechaContratacion.setValue(value.getFechaContratacion());
        	pais.setValue(value.getPais());
        	estado.setValue(value.getEstado());
        	
        }else {
        	nombreProveedor.setValue("");
        	direccion.setValue("");
        	correo.setValue("");
        	telefono.setValue("");
        	fechaContratacion.setValue(null);
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
