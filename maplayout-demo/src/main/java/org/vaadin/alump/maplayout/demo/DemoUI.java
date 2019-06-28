package org.vaadin.alump.maplayout.demo;

import javax.servlet.annotation.WebServlet;

import com.neovisionaries.i18n.CountryCode;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
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

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "org.vaadin.alump.maplayout.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        Navigator navigator = new Navigator(this, this);

        navigator.addView(MenuView.VIEW_NAME, MenuView.class);
        navigator.addView(WorldView.VIEW_NAME, WorldView.class);
        navigator.addView(USView.VIEW_NAME, USView.class);
        navigator.addView(EuropeView.VIEW_NAME, EuropeView.class);
        navigator.addView(EuropeChartView.VIEW_NAME, EuropeChartView.class);
        navigator.setErrorView(MenuView.class);
    }
}