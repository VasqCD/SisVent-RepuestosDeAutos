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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import hn.ventaderepuestos.data.Orden;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.services.SamplePersonService;
import hn.ventaderepuestos.views.MainLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Ordenes")
@Route(value = "ordenes", layout = MainLayout.class)
@Uses(Icon.class)
public class OrdenesView extends Composite<VerticalLayout> {

    private final Grid<Orden> historialOrdenes = new Grid<>(Orden.class, false);

    public OrdenesView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        Hr hr = new Hr();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Checkbox checkbox = new Checkbox();
        Checkbox checkbox2 = new Checkbox();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField textField3 = new TextField();
        EmailField emailField = new EmailField();
        TextField textField4 = new TextField();
        ComboBox comboBox = new ComboBox();
        ComboBox comboBox2 = new ComboBox();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        Hr hr2 = new Hr();
        H3 h32 = new H3();

        //Grid historialOrdenes = new Grid(Proveedor.class);

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
        checkbox.setLabel("Venta");
        checkbox.setWidth("min-content");
        checkbox2.setLabel("Compra");
        checkbox2.setWidth("min-content");
        formLayout2Col.setWidth("100%");
        textField.setLabel("First Name");
        textField2.setLabel("Last Name");
        datePicker.setLabel("Birthday");
        textField3.setLabel("Phone Number");
        emailField.setLabel("Email");
        textField4.setLabel("Occupation");
        comboBox.setLabel("Producto");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        comboBox2.setLabel("Proveedor");
        comboBox2.setWidth("min-content");
        setComboBoxSampleData(comboBox2);
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


        historialOrdenes.addColumn("nombre").setAutoWidth(true);
        historialOrdenes.addColumn("descripcion").setAutoWidth(true);
        historialOrdenes.addColumn("cantidad").setAutoWidth(true);
        historialOrdenes.addColumn("precio").setAutoWidth(true);
        historialOrdenes.addColumn("total").setAutoWidth(true);
        historialOrdenes.addColumn("proveedor").setAutoWidth(true);
        historialOrdenes.addColumn("fecha").setAutoWidth(true);
        historialOrdenes.addColumn("estado").setAutoWidth(true);
        historialOrdenes.addColumn("observaciones").setAutoWidth(true);


        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow);
        layoutRow.add(checkbox);
        layoutRow.add(checkbox2);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(datePicker);
        formLayout2Col.add(textField3);
        formLayout2Col.add(emailField);
        formLayout2Col.add(textField4);
        formLayout2Col.add(comboBox);
        formLayout2Col.add(comboBox2);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
        layoutColumn2.add(hr2);
        layoutColumn2.add(h32);

        layoutColumn2.add(historialOrdenes);

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

    public void mostrarOrdenEnGrid(List<Orden> items) {
    Collection<Orden> itemsCollection = items;
    historialOrdenes.setItems(itemsCollection);
}


}