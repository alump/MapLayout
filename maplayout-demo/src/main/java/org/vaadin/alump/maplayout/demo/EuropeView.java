package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.vaadin.alump.maplayout.*;

import java.util.*;

public class EuropeView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "europe";
    private Set<EuropeanCountry> clickToggled = new HashSet<>();
    private Set<EuropeanCountry> greens = new HashSet<>();
    private Random random = new Random(0xDEADBEEF);

    private EuropeMap map;

    public EuropeView() {
        map = new EuropeMap();
        map.setWidth(600, Unit.PIXELS);
        map.addStyleName(EuropeMap.SMOOTH_COLOR_TRANSITION_STYLENAME);
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

        Button greenIt = new Button("Random Green");
        greenIt.addClickListener(e -> randomToGreen());

        Button shade = new Button("Shade");
        shade.addClickListener(e -> randomShades());

        Button clear = new Button("Clear dyn colors");
        clear.addClickListener(e -> clearFillColors());

        Button eu = new Button("EU to blue");
        eu.addClickListener(e -> highlightEU());

        bar.addComponents(menu, greenIt, shade, eu, clear);
        return bar;
    }

    private void clearFillColors() {
        EuropeanCountry.stream().forEach(c -> {
            map.removeCountryStyle(c, "fill");
        });
    }

    private void randomToGreen() {
        int count = EuropeanCountry.values().length;
        if(greens.size() == count) {
            Notification.show("Everything is green already :(");
            greens.forEach(c -> map.removeCountryStyle(c, "fill"));
            greens.clear();
        } else {
            EuropeanCountry randomCountry = EuropeanCountry.values()[random.nextInt(count)];
            map.setCountryStyle(randomCountry, "fill", "green");
            greens.add(randomCountry);
            Notification.show("Make " + randomCountry.getCountryCode().getName() + " green",
                    Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void highlightEU() {
        EuropeanCountry.stream().filter(c -> c.isEU()).forEach(c -> {
            map.setCountryStyle(c, "fill", "blue");
        });
        greens.clear();
    }

    private void randomShades() {
        EuropeanCountry.stream().forEach(c -> {
            map.setCountryColor(c, random.nextInt(255), 0, 0);
        });
        greens.clear();
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
            if(clickToggled.contains(code)) {
                map.setCountryColor(code, 255, 255, 0);
            } else {
                map.clearCountryColor(code);
            }
            map.setCountryColor(code, 255, 255, 0);
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
