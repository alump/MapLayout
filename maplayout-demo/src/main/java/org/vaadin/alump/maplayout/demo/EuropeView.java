package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.vaadin.alump.maplayout.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EuropeView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "europe";
    private Set<EuropeanCountry> clickToggled = new HashSet<>();

    private EuropeMap map;

    public EuropeView() {
        map = new EuropeMap();
        map.setWidth(600, Unit.PIXELS);
        map.addStyleName(MapLayout.TRANSPARENT_BG_STYLENAME);
        map.addMapLayoutClickListener(this::onMapLayoutClicked);

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

    public void onMapLayoutClicked(MapLayoutClickEvent<EuropeanCountry> event) {
        Optional<EuropeanCountry> cc = event.getMapItem();
        if(cc.isPresent()) {
            EuropeanCountry code = cc.get();
            Notification.show(code.getCountryCode().getName() + " clicked!");
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


}
