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

        List<CategoriaRepuesto> categorias = getCategorias();
        gridListDataView = grid.setItems(categorias);
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
            span.setText(categoriarepuestos.getNombreCategoria());
            hl.add(img, span);
            return hl;
        })).setComparator(CategoriaRepuesto::getNombreCategoria).setHeader("Nombre Categoria");
    }

    private void createDescripcionColumna() {
        descripcionColumn = grid
                .addColumn(CategoriaRepuesto::getDescripcion)
                .setComparator(CategoriaRepuesto::getDescripcion)
                .setHeader("Descripcion");
    }

    private void createEstadoColumna() {
        estadoColumn = grid
                .addColumn(new ComponentRenderer<>(categoriarepuestos -> {
                    Span span = new Span();
                    span.setText(categoriarepuestos.getEstado());
                    span.getElement().setAttribute("theme", "badge " + categoriarepuestos.getEstado().toLowerCase());
                    return span;
                })).setHeader("Estado");
    }

     private void createFechaCreacionColumna() {
        fechaCreacionColumn = grid
                .addColumn(new LocalDateRenderer<>(categoriarepuestos -> LocalDate.parse(categoriarepuestos.getFechaCreacion()),
                        () -> DateTimeFormatter.ofPattern("M/d/yyyy")))
                .setComparator(categoriarepuestos -> categoriarepuestos.getFechaCreacion()).setHeader("Fecha Creacion").setWidth("180px").setFlexGrow(0);
    }
     

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField categoriaFilter = new TextField();
        categoriaFilter.setPlaceholder("Filtro");
        categoriaFilter.setClearButtonVisible(true);
        categoriaFilter.setWidth("100%");
        categoriaFilter.setValueChangeMode(ValueChangeMode.EAGER);
        categoriaFilter.addValueChangeListener(event -> gridListDataView
                .addFilter(categoriarepuestos -> StringUtils.containsIgnoreCase(categoriarepuestos.getNombreCategoria(), categoriaFilter.getValue())));
        filterRow.getCell(nombreCategoriaColumn).setComponent(categoriaFilter);

        ComboBox<String> estadoFilter = new ComboBox<>();
        estadoFilter.setItems(Arrays.asList("Pendiente", "Exito", "Error"));
        estadoFilter.setPlaceholder("Filtro");
        estadoFilter.setClearButtonVisible(true);
        estadoFilter.setWidth("100%");
        estadoFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(categoriarepuestos -> areStatusesEqual(categoriarepuestos, estadoFilter)));
        filterRow.getCell(estadoColumn).setComponent(estadoFilter);

        DatePicker fechaFilter = new DatePicker();
        fechaFilter.setPlaceholder("Filtro");
        fechaFilter.setClearButtonVisible(true);
        fechaFilter.setWidth("100%");
        fechaFilter.addValueChangeListener(
                event -> gridListDataView.addFilter(categoriarepuestos -> areDatesEqual(categoriarepuestos, fechaFilter)));
        filterRow.getCell(fechaCreacionColumn).setComponent(fechaFilter);
    }

    private boolean areStatusesEqual(CategoriaRepuesto categoria, ComboBox<String> estadoFilter) {
        String estadoFilterValue = estadoFilter.getValue();
        if (estadoFilterValue != null) {
            return StringUtils.equals(categoria.getEstado(), estadoFilterValue);
        }
        return true;
    }

    private boolean areDatesEqual(CategoriaRepuesto categoria, DatePicker fechaFilter) {
        LocalDate fechaFilterValue = fechaFilter.getValue();
        if (fechaFilterValue != null) {
            LocalDate fechaCategoria = LocalDate.parse(categoria.getFechaCreacion());
            return fechaFilterValue.equals(fechaCategoria);
        }
        return true;
    }

    private List<CategoriaRepuesto> getCategorias() {
        return Arrays.asList(
                createCategoriaRepuesto(8942, "https://www.pruebaderuta.com/wp-content/uploads/2017/02/repuestos.jpg", "Sistema de suspencion", "Descripción de la categoría", "Exito", "2024-03-14")
        );
    }

    private CategoriaRepuesto createCategoriaRepuesto(int id, String img, String nombreCategoria, String descripcion, String estado, String fechaCreacion) {
        CategoriaRepuesto c = new CategoriaRepuesto();
        c.setId(id);
        c.setImg(img);
        c.setNombreCategoria(nombreCategoria);
        c.setDescripcion(descripcion);
        c.setEstado(estado);
        c.setFechaCreacion(fechaCreacion);
        return c;
    }
}

