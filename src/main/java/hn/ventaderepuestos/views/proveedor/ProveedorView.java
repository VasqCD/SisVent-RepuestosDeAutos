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
import com.vaadin.flow.router.*;

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
@Route(value = "proveedor/:proveedorid?/:action?(edit)", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class ProveedorView extends Div implements BeforeEnterObserver, ViewModelProveedor {

    private final String SAMPLEPERSON_ID = "proveedorid";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "proveedor/%s/edit";

    private final Grid<Proveedor> grid = new Grid<>(Proveedor.class, false);

    private TextField proveedorid;
    private TextField nombre;
    private TextField direccion;
    private TextField correo;
    private TextField telefono;
    private TextField pais;



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
        grid.addColumn("proveedorid").setAutoWidth(true).setHeader("ID");
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true);
        grid.addColumn("correo").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("pais").setAutoWidth(true);
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getProveedorid()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ProveedorView.class);
            }
        });
        
        controlador.consultarProveedor();
        

        // Configure Form
        cancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        //boton guardar
        guardar.addClickListener(e -> {
            try {
                if (this.proveedorSeleccionado == null) {
                    //creacion de un nuevo proveedor
                    this.proveedorSeleccionado = new Proveedor();

                    this.proveedorSeleccionado.setProveedorid(Integer.parseInt(proveedorid.getValue())); //se convierte a entero
                    this.proveedorSeleccionado.setNombre(nombre.getValue());
                    this.proveedorSeleccionado.setDireccion(direccion.getValue());
                    this.proveedorSeleccionado.setCorreo(correo.getValue());
                    this.proveedorSeleccionado.setTelefono(telefono.getValue());
                    this.proveedorSeleccionado.setPais(pais.getValue());
                    
                    this.controlador.crearProveedor(proveedorSeleccionado);
                }else {
                    //actualizacion de un proveedor
                    this.proveedorSeleccionado.setNombre(nombre.getValue());
                    this.proveedorSeleccionado.setDireccion(direccion.getValue());
                    this.proveedorSeleccionado.setCorreo(correo.getValue());
                    this.proveedorSeleccionado.setTelefono(telefono.getValue());
                    this.proveedorSeleccionado.setPais(pais.getValue());

                    this.controlador.actualizarProveedor(proveedorSeleccionado);
                	
                }
                
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(ProveedorView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        //boton eliminar
        eliminar.addClickListener( e-> {
       	             	if(proveedorSeleccionado == null) {
       	             	mostrarMensajeError("Seleccione un proveedor para poder eliminar");
            	}else {
            		this.controlador.eliminarProveedor(String.valueOf(proveedorSeleccionado.getProveedorid()));
            		clearForm();
            		refreshGrid();
            		UI.getCurrent().navigate(ProveedorView.class);
            	}
       });
    }
    
    //metodo que se ejecuta al seleccionar u elemento del grid

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> codigoProveedor = event.getRouteParameters().get(SAMPLEPERSON_ID);
        if (codigoProveedor.isPresent()) {
        	Proveedor proveedorObtenido = obtenerProveedor(codigoProveedor.get());
            if (proveedorObtenido != null) {
                populateForm(proveedorObtenido);
            } else {
                Notification.show(
                        String.format("El proveedor con ID = %s no existe", codigoProveedor.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(ProveedorView.class);
            }
            
        }
    }
    
    private Proveedor obtenerProveedor(String proveedorid) {
		Proveedor encontrado = null;
    	for(Proveedor prov: elementos) {
			if(prov.getProveedorid() == Integer.parseInt(proveedorid)) {
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
        proveedorid = new TextField("ID");
        proveedorid.setId("txt_idProveedor");
        proveedorid.setValue("0");
        proveedorid.setEnabled(false);

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

        //agreaga el control a la vista
        formLayout.add(proveedorid, nombre, direccion, correo, telefono, pais);

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
        this.controlador.consultarProveedor();
    }

    private void clearForm() {
        populateForm(null);
    }
    

    private void populateForm(Proveedor value) {
    	this.proveedorSeleccionado = value;
        if(value != null) {

            proveedorid.setValue(String.valueOf(value.getProveedorid())); //se convierte a string
        	nombre.setValue(value.getNombre());
        	direccion.setValue(value.getDireccion());
        	correo.setValue(value.getCorreo());
        	telefono.setValue(value.getTelefono());
        	pais.setValue(value.getPais());
        	
        }else {
            proveedorid.setValue("0");
        	nombre.setValue("");
        	direccion.setValue("");
        	correo.setValue("");
        	telefono.setValue("");
        	pais.setValue("");
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
	
	@Override
    public void mostrarMensajeExito(String mensaje) {
    	Notification.show(mensaje);
    }
	
}
