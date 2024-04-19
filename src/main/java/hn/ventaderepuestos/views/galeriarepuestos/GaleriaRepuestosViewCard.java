package hn.ventaderepuestos.views.galeriarepuestos;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class GaleriaRepuestosViewCard extends ListItem {

    public GaleriaRepuestosViewCard(String nombre, int codigo, String url) {
        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE);

        Div fotografia = new Div();
        fotografia.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        fotografia.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt(String.valueOf(codigo));

        fotografia.add(image);

        Span nombreRepuesto = new Span();
        nombreRepuesto.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        nombreRepuesto.setText(nombre);

        Span repuestoid = new Span();
        repuestoid.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        repuestoid.setText("Codigo: " + String.valueOf(codigo));

        Paragraph marcaRepuesto = new Paragraph("Marca: ");
        marcaRepuesto.addClassName(LumoUtility.Margin.Vertical.MEDIUM);

        Span estado = new Span();
        estado.getElement().setAttribute("theme", "badge");
        estado.setText("Label");

        add(fotografia, nombreRepuesto, repuestoid, marcaRepuesto, estado);
    }
}