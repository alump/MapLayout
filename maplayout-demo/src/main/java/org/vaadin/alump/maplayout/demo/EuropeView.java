package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.alump.maplayout.EuropeMap;
import org.vaadin.alump.maplayout.MapLayout;

public class EuropeView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "europe";

    private EuropeMap map;

    public EuropeView() {
        map = new EuropeMap();
        map.setWidth(600, Unit.PIXELS);
        map.addStyleName(MapLayout.TRANSPARENT_BG_STYLENAME);

        addComponents(createTopBar(), map);
    }

    private HorizontalLayout createTopBar() {
        HorizontalLayout bar = new HorizontalLayout();
        bar.setSpacing(true);

        Button menu = new Button();
        menu.setIcon(VaadinIcons.MENU);
        menu.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(MenuView.VIEW_NAME));

        bar.addComponents(menu);
        return bar;
    }


}
