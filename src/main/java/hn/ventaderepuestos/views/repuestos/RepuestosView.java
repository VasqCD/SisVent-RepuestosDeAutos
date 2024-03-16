package hn.ventaderepuestos.views.repuestos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.services.SampleBookService;
import hn.ventaderepuestos.views.MainLayout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Repuestos")
@Route(value = "repuestos/:sampleBookID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class RepuestosView extends Div implements BeforeEnterObserver {

    private final String SAMPLEBOOK_ID = "sampleBookID";
    private final String SAMPLEBOOK_EDIT_ROUTE_TEMPLATE = "repuestos/%s/edit";

    private final Grid<Repuesto> grid = new Grid<>(Repuesto.class, false);


    private TextField nombreRepuesto;
    private TextField precioUnitario;
    private DatePicker fechaIngreso;
    private TextField unidadesStock;
    private TextField estado;

    private final Button cancelar = new Button("Cancelar");
    private final Button guardar = new Button("Guardar");
    private final Button eliminar = new Button("Eliminar");

    private final BeanValidationBinder<Repuesto> binder;

    private Repuesto repuesto;

    private final SampleBookService sampleBookService;

    public RepuestosView(SampleBookService sampleBookService) {
        this.sampleBookService = sampleBookService;
        addClassNames("repuestos-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid

        grid.addColumn("nombreRepuesto").setAutoWidth(true);
        grid.addColumn("precioUnitario").setAutoWidth(true);
        grid.addColumn("fechaIngreso").setAutoWidth(true);
        grid.addColumn("unidadesStock").setAutoWidth(true);
        grid.addColumn("estado").setAutoWidth(true);
        grid.setItems(query -> sampleBookService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLEBOOK_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RepuestosView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Repuesto.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(unidadesStock).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("unidadesStock");

        binder.bindInstanceFields(this);

     

        cancelar.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        guardar.addClickListener(e -> {
            try {
                if (this.repuesto == null) {
                    this.repuesto = new Repuesto();
                }
                binder.writeBean(this.repuesto);
                sampleBookService.update(this.repuesto);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(RepuestosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> sampleBookId = event.getRouteParameters().get(SAMPLEBOOK_ID).map(Long::parseLong);
        if (sampleBookId.isPresent()) {
            Optional<Repuesto> sampleBookFromBackend = sampleBookService.get(sampleBookId.get());
            if (sampleBookFromBackend.isPresent()) {
                populateForm(sampleBookFromBackend.get());
            } else {
                Notification.show(String.format("The requested sampleBook was not found, ID = %s", sampleBookId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(RepuestosView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombreRepuesto = new TextField("Nombre de repuesto");
        nombreRepuesto.setId("txt_nombreRepuesto");
        precioUnitario = new TextField("Precio unitario");
        precioUnitario.setId("txt_precioUnitario");
        fechaIngreso = new DatePicker("Fecha de ingreso");
        fechaIngreso.setId("txt_fechaIngreso");
        unidadesStock = new TextField("Unidades en Stock");
        unidadesStock.setId("txt_unidades");
        estado = new TextField("Estado");
        estado.setId("txt_estado");
        formLayout.add(nombreRepuesto, precioUnitario, fechaIngreso, unidadesStock, estado);

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
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Repuesto value) {
        this.repuesto = value;
        binder.readBean(this.repuesto);

    }
    
}
