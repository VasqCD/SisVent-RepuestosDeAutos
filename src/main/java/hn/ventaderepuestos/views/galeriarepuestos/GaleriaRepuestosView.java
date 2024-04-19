package hn.ventaderepuestos.views.galeriarepuestos;


import ch.qos.logback.classic.model.LevelModel;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import hn.ventaderepuestos.controller.InteractorGaleriaRepuesto;
import hn.ventaderepuestos.controller.InteractorImplGaleriaRepuesto;
import hn.ventaderepuestos.data.GaleriaRepuesto;
import hn.ventaderepuestos.data.Proveedor;
import hn.ventaderepuestos.data.Repuesto;
import hn.ventaderepuestos.views.MainLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@PageTitle("GaleriaRepuestos")
@Route(value = "galeriaRepuestos", layout = MainLayout.class)
public class GaleriaRepuestosView extends Main implements HasComponents, HasStyle, ViewModelGaleriaRepuestos {

    private OrderedList imageContainer;

    private GaleriaRepuesto repuestoSeleccionado;
    private List<GaleriaRepuesto> repuestos;
    private InteractorGaleriaRepuesto controlador;


    public GaleriaRepuestosView() {

        controlador = new InteractorImplGaleriaRepuesto(this);
        repuestos = new ArrayList<>();

        controlador.consultarGaleriaRepuesto();

        constructUI();

        for (GaleriaRepuesto repuesto : repuestos) {


            imageContainer.add(new GaleriaRepuestosViewCard(repuesto.getNombre(), repuesto.getRepuestoid(),
                    "https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        }
    }

    private void constructUI() {
        addClassNames("galeria-repuestos-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Galeria de repuestos");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Galeria de repuestos disponibles en la tienda");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);



        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer);
        add(container, imageContainer);
    }


    @Override
    public void mostrarGaleriaRepuesto(List<GaleriaRepuesto> items){
        Collection<GaleriaRepuesto> itemsCollection = items;
        this.repuestos = new ArrayList<>(itemsCollection);
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        Notification n = Notification.show(mensaje);
        n.setPosition(Notification.Position.MIDDLE);
        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

}