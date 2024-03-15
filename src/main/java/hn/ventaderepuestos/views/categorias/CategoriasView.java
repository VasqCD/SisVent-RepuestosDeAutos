package hn.ventaderepuestos.views.categorias;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hn.ventaderepuestos.views.MainLayout;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

@PageTitle("Categorias")
@Route(value = "categorias", layout = MainLayout.class)
public class CategoriasView extends Div {

	private static final long serialVersionUID = 1L;
	private GridPro<CategoriaRepuesto> grid;
    private GridListDataView<CategoriaRepuesto> gridListDataView;

    private Grid.Column<CategoriaRepuesto> nombreCategoriaColumn;
    private Grid.Column<CategoriaRepuesto> descripcionColumn;
    private Grid.Column<CategoriaRepuesto> estadoColumn;
    private Grid.Column<CategoriaRepuesto> fechaCreacionColumn;

    public CategoriasView() {
        addClassName("categorias-view");
        setSizeFull();
        createGrid();
        add(grid);
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid = new GridPro<>();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        List<CategoriaRepuesto> clients = getClients();
        gridListDataView = grid.setItems(clients);
    }

    private void addColumnsToGrid() {
        createNombreCategoriaColumna();
        createDescripcionColumna();
        createEstadoColumna();
        createFechaCreacionColumna();
    }

    private void createNombreCategoriaColumna() {
        nombreCategoriaColumn = grid.addColumn(new ComponentRenderer<>(categoriarepuestos -> {
            HorizontalLayout hl = new HorizontalLayout();
            hl.setAlignItems(Alignment.CENTER);
            Image img = new Image(categoriarepuestos.getImg(), "");
            Span span = new Span();
            span.setClassName("nombre");
            span.setText(categoriarepuestos.getClient());
            hl.add(img, span);
            return hl;
        })).setComparator(categoriarespuestos -> categoriarespuestos.getClient()).setHeader("Nombre Categoria");
    }

    private void createDescripcionColumna() {
        descripcionColumn = grid
                .addEditColumn(CategoriaRepuesto::getAmount,
                        new NumberRenderer<>(categoriarepuestos -> categoriarepuestos.getAmount(), NumberFormat.getCurrencyInstance(Locale.US)))
                .text((item, newValue) -> item.setAmount(Double.parseDouble(newValue)))
                .setComparator(categoriarepuestos -> categoriarepuestos.getAmount()).setHeader("Descripcion");
    }

    private void createEstadoColumna() {
        estadoColumn = grid.addEditColumn(CategoriaRepuesto::getClient, new ComponentRenderer<>(client -> {
            Span span = new Span();
            span.setText(client.getStatus());
            span.getElement().setAttribute("theme", "badge " + client.getStatus().toLowerCase());
            return span;
        })).select((item, newValue) -> item.setStatus(newValue), Arrays.asList("Pendiente", "Exito", "Error"))
                .setComparator(categoriarepuestos -> categoriarepuestos.getStatus()).setHeader("Estado");
    }

    private void createFechaCreacionColumna() {
        fechaCreacionColumn = grid
                .addColumn(new LocalDateRenderer<>(categoriarepuestos -> LocalDate.parse(categoriarepuestos.getDate()),
                        () -> DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(categoriarepuestos -> categoriarepuestos.getDate()).setHeader("Fecha Creacion").setWidth("180px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField clientFilter = new TextField();
        clientFilter.setPlaceholder("Filtro");
        clientFilter.setClearButtonVisible(true);
        clientFilter.setWidth("100%");
        clientFilter.setValueChangeMode(ValueChangeMode.EAGER);
        clientFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(categoriarepuestos -> StringUtils.containsIgnoreCase(categoriarepuestos.getClient(), clientFilter.getValue())));
        filterRow.getCell(nombreCategoriaColumn).setComponent(clientFilter);

        TextField amountFilter = new TextField();
        amountFilter.setPlaceholder("Filtro");
        amountFilter.setClearButtonVisible(true);
        amountFilter.setWidth("100%");
        amountFilter.setValueChangeMode(ValueChangeMode.EAGER);
        amountFilter.addValueChangeListener(event -> gridListDataView.addFilter(client -> StringUtils
                .containsIgnoreCase(Double.toString(client.getAmount()), amountFilter.getValue())));
        filterRow.getCell(descripcionColumn).setComponent(amountFilter);

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.setItems(Arrays.asList("Pendiente", "Exito", "Error"));
        statusFilter.setPlaceholder("Filtro");
        statusFilter.setClearButtonVisible(true);
        statusFilter.setWidth("100%");
        statusFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(categoriarepuestos -> areStatusesEqual(categoriarepuestos, statusFilter)));
        filterRow.getCell(estadoColumn).setComponent(statusFilter);

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPlaceholder("Filtro");
        dateFilter.setClearButtonVisible(true);
        dateFilter.setWidth("100%");
        dateFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(categoriarepuestos -> areDatesEqual(categoriarepuestos, dateFilter)));
        filterRow.getCell(fechaCreacionColumn).setComponent(dateFilter);
    }

    private boolean areStatusesEqual(CategoriaRepuesto client, ComboBox<String> statusFilter) {
        String statusFilterValue = statusFilter.getValue();
        if (statusFilterValue != null) {
            return StringUtils.equals(client.getStatus(), statusFilterValue);
        }
        return true;
    }

    private boolean areDatesEqual(CategoriaRepuesto client, DatePicker dateFilter) {
        LocalDate dateFilterValue = dateFilter.getValue();
        if (dateFilterValue != null) {
            LocalDate repuestoDato = LocalDate.parse(client.getDate());
            return dateFilterValue.equals(repuestoDato);
        }
        return true;
    }

    private List<CategoriaRepuesto> getClients() {
        return Arrays.asList(
                createCategoriaRepuestos(4957, "https://randomuser.me/api/portraits/women/42.jpg", "Amarachi Nkechi", 47427.0,
                        "Success", "2019-05-09"),
                createCategoriaRepuestos(675, "https://randomuser.me/api/portraits/women/24.jpg", "Bonelwa Ngqawana", 70503.0,
                        "Success", "2019-05-09"),
                createCategoriaRepuestos(6816, "https://randomuser.me/api/portraits/men/42.jpg", "Debashis Bhuiyan", 58931.0,
                        "Success", "2019-05-07"),
                createCategoriaRepuestos(5144, "https://randomuser.me/api/portraits/women/76.jpg", "Jacqueline Asong", 25053.0,
                        "Pending", "2019-04-25"),
                createCategoriaRepuestos(9800, "https://randomuser.me/api/portraits/men/24.jpg", "Kobus van de Vegte", 7319.0,
                        "Pending", "2019-04-22"),
                createCategoriaRepuestos(3599, "https://randomuser.me/api/portraits/women/94.jpg", "Mattie Blooman", 18441.0,
                        "Error", "2019-04-17"),
                createCategoriaRepuestos(3989, "https://randomuser.me/api/portraits/men/76.jpg", "Oea Romana", 33376.0, "Pending",
                        "2019-04-17"),
                createCategoriaRepuestos(1077, "https://randomuser.me/api/portraits/men/94.jpg", "Stephanus Huggins", 75774.0,
                        "Success", "2019-02-26"),
                createCategoriaRepuestos(8942, "https://randomuser.me/api/portraits/men/16.jpg", "Torsten Paulsson", 82531.0,
                        "Pending", "2019-02-21"));
    }

    private CategoriaRepuesto createCategoriaRepuestos(int id, String img, String nombreCategoria, double descripcion, String estado, String fechaCreacion) {
        CategoriaRepuesto c = new CategoriaRepuesto();
        c.setId(id);
        c.setImg(img);
        c.setClient(nombreCategoria);
        c.setAmount(descripcion);
        c.setStatus(estado);
        c.setDate(fechaCreacion);

        return c;
    }
};
