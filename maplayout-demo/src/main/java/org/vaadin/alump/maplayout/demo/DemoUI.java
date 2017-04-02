package org.vaadin.alump.maplayout.demo;

import javax.servlet.annotation.WebServlet;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.vaadin.alump.maplayout.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Theme("demo")
@Title("MapLayout Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    private WorldMap map;
    private Set<CountryCode> clickToggled = new HashSet<>();

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.alump.maplayout.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        map = new WorldMap();
        map.addStyleName("smooth-color-transition");
        map.setAddTitles(false);
        map.setStyleNamesToItems(MapColors.GREEN, CountryCode.FI, CountryCode.US, CountryCode.DE);
        map.setStyleNamesToItems(MapColors.YELLOW, CountryCode.AR, CountryCode.PL);
        map.setWidth(100, Unit.PERCENTAGE);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);

        CheckBox sayClicked = new CheckBox("Clickable", false);
        sayClicked.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.addStyleName("clickable");
                map.addMapLayoutClickListener(this::onMapLayoutClicked);
            } else {
                map.removeStyleName("clickable");
                map.removeMapLayoutClickListener(this::onMapLayoutClicked);
            }
        });

        CheckBox vaadinOffices = new CheckBox("Offices", false);
        vaadinOffices.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.addComponentToViewBox(new VaadinPin("San Jose"), 402.0, 426.0);
                map.addComponentToViewBox(new VaadinPin("Turku"), 1420.5, 238.5);
                map.addComponentToViewBox(new VaadinPin("Berlin"), 1361.0, 299.5);
            } else {
                removeVaadinPins();
            }
        });

        CheckBox charts = new CheckBox("Charts", false);
        charts.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.addComponentToViewBox(new VaadinChart("Sydney"), 2370.0, 1052.0);
                map.addComponentToViewBox(new VaadinChart("Rio de Janeiro"), 945.0, 952.0);
                map.addComponentToViewBox(new VaadinChart("Beijing"), 2115.0, 415.0);
            } else {
                removeVaadinCharts();
            }
        });

        CheckBox zoom = new CheckBox("Zoom", false);
        zoom.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.setViewBox(280.0,80.0,2300.0,1150.0);
            } else {
                map.setViewBox(82.992,45.607,2528.5721,1428.3294);
            }
        });

        CheckBox highlightAustralia = new CheckBox("Australia", false);
        highlightAustralia.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.PURPLE, CountryCode.AU, e.getValue()));

        CheckBox highlightBrazil = new CheckBox("Brazil", false);
        highlightBrazil.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.RED, CountryCode.BR, e.getValue()));

        CheckBox highlightChina = new CheckBox("China", false);
        highlightChina.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.BLUE, CountryCode.CN, e.getValue()));

        CheckBox highlightDenmark = new CheckBox("Denmark", false);
        highlightDenmark.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.YELLOW, CountryCode.DK, e.getValue()));

        CheckBox highlightEgypt = new CheckBox("Egypt", false);
        highlightEgypt.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.GREEN, CountryCode.EG, e.getValue()));

        CheckBox blueOcean = new CheckBox("Blue Ocean", false);
        blueOcean.addValueChangeListener(e -> map.setStyleName(MapColors.OCEAN_BLUE, e.getValue()));

        buttons.addComponents(vaadinOffices, charts, zoom, sayClicked,
                highlightAustralia, highlightBrazil, highlightChina, highlightDenmark, highlightEgypt,
                blueOcean);

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponents(buttons, map);
        setContent(layout);
    }

    public void onMapLayoutClicked(MapLayoutClickEvent<CountryCode> event) {
        Optional<CountryCode> cc = event.getMapItem();
        if(cc.isPresent()) {
            CountryCode code = cc.get();
            Notification.show(code.getName() + " clicked!");
            if(clickToggled.contains(code)) {
                clickToggled.remove(code);
            } else {
                clickToggled.add(code);
            }
            map.setStyleNameOfItem(MapColors.ORANGE, code, clickToggled.contains(code));
        } else {
            Notification.show("Click to X:"
                    + event.getClientX() + " "
                    + event.getRelativeX()
                    + " (" + event.getViewBoxX().map(d -> Long.toString(Math.round(d))).orElse("n/a") + "),  Y:"
                    + event.getClientY() + " "
                    + event.getRelativeY()
                    + " (" + event.getViewBoxY().map(d -> Long.toString(Math.round(d))).orElse("n/a") + ")" );
        }
    }

    private void removeVaadinPins() {
        removeAll(VaadinPin.class);
    }

    private void removeVaadinCharts() {
        removeAll(VaadinChart.class);
    }

    private void removeAll(Class<? extends Component> klass) {
        Set<Component> remove = new HashSet<>();
        map.iterator().forEachRemaining(c -> {
            if(klass.isInstance(c)) {
                remove.add(c);
            }
        });
        remove.forEach(c -> map.removeComponent(c));
    }
}
