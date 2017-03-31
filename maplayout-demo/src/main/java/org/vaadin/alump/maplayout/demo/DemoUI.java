package org.vaadin.alump.maplayout.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.vaadin.alump.maplayout.DefaultWorldMapLayout;
import org.vaadin.alump.maplayout.DefaultWorldMapLayout.CountryCode;
import org.vaadin.alump.maplayout.MapColors;
import org.vaadin.alump.maplayout.MapLayout;

@Theme("demo")
@Title("MapLayout Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    private MapLayout map;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.alump.maplayout.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        map = new DefaultWorldMapLayout();
        map.setAddTitles(false);
        map.setStyleNamesToItems(MapColors.GREEN, CountryCode.FINLAND, CountryCode.UNITED_STATES,
                CountryCode.GERMANY);
        map.setStyleNamesToItems(MapColors.YELLOW, CountryCode.ARGENTINA, CountryCode.POLAND);
        map.setWidth(100, Unit.PERCENTAGE);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);

        CheckBox highlightAustralia = new CheckBox("Australia", false);
        highlightAustralia.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.PURPLE, CountryCode.AUSTRALIA, e.getValue()));

        CheckBox highlightBrazil = new CheckBox("Brazil", false);
        highlightBrazil.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.RED, CountryCode.BRAZIL, e.getValue()));

        CheckBox highlightChina = new CheckBox("China", false);
        highlightChina.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.BLUE, CountryCode.CHINA, e.getValue()));

        CheckBox highlightDenmark = new CheckBox("Denmark", false);
        highlightDenmark.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.YELLOW, CountryCode.DENMARK, e.getValue()));

        CheckBox highlightEgypt = new CheckBox("Egypt", false);
        highlightEgypt.addValueChangeListener(e ->
                map.setStyleNameOfItem(MapColors.GREEN, CountryCode.EGYPT, e.getValue()));

        CheckBox blueOcean = new CheckBox("Blue Ocean", false);
        blueOcean.addValueChangeListener(e -> map.setStyleName(MapColors.OCEAN_BLUE, e.getValue()));

        buttons.addComponents(highlightAustralia, highlightBrazil, highlightChina, highlightDenmark, highlightEgypt,
                blueOcean);

        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponents(buttons, map);
        setContent(layout);
    }
}
