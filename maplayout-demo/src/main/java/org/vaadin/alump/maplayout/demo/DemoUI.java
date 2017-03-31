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

    private DefaultWorldMapLayout map;
    private Set<CountryCode> clickToggled = new HashSet<>();

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.alump.maplayout.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        map = new DefaultWorldMapLayout();
        map.addStyleName("smooth-color-transition");
        map.setAddTitles(false);
        map.setCountryStyleNames(MapColors.GREEN, CountryCode.FI, CountryCode.US, CountryCode.DE);
        map.setCountryStyleNames(MapColors.YELLOW, CountryCode.AR, CountryCode.PL);
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

        CheckBox highlightAustralia = new CheckBox("Australia", false);
        highlightAustralia.addValueChangeListener(e ->
                map.setCountryStyleName(MapColors.PURPLE, CountryCode.AU, e.getValue()));

        CheckBox highlightBrazil = new CheckBox("Brazil", false);
        highlightBrazil.addValueChangeListener(e ->
                map.setCountryStyleName(MapColors.RED, CountryCode.BR, e.getValue()));

        CheckBox highlightChina = new CheckBox("China", false);
        highlightChina.addValueChangeListener(e ->
                map.setCountryStyleName(MapColors.BLUE, CountryCode.CN, e.getValue()));

        CheckBox highlightDenmark = new CheckBox("Denmark", false);
        highlightDenmark.addValueChangeListener(e ->
                map.setCountryStyleName(MapColors.YELLOW, CountryCode.DK, e.getValue()));

        CheckBox highlightEgypt = new CheckBox("Egypt", false);
        highlightEgypt.addValueChangeListener(e ->
                map.setCountryStyleName(MapColors.GREEN, CountryCode.EG, e.getValue()));

        CheckBox blueOcean = new CheckBox("Blue Ocean", false);
        blueOcean.addValueChangeListener(e -> map.setStyleName(MapColors.OCEAN_BLUE, e.getValue()));

        buttons.addComponents(sayClicked,
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

    public void onMapLayoutClicked(MapLayoutClickEvent event) {
        Optional<CountryCode> cc = map.resolveCountryCodeFromElementIds(event.getMapItemIds());
        if(cc.isPresent()) {
            CountryCode code = cc.get();
            Notification.show(code.getName() + " clicked!");
            if(clickToggled.contains(code)) {
                clickToggled.remove(code);
            } else {
                clickToggled.add(code);
            }
            map.setCountryStyleName(MapColors.ORANGE, code, clickToggled.contains(code));
        } else {
            Notification.show("Click to " + event.getRelativeX() + " "  + event.getRelativeY());
        }
    }
}
