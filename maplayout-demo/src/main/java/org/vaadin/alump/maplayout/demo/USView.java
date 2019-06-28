package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.alump.maplayout.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class USView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "usa";

    private USStatesMap map;

    private Set<USState> selectedStates = new HashSet<>();

    private Random random = new Random(0xDEADBEEF);

    public USView() {

        map = new USStatesMap();
        map.addStyleName(USStatesMap.SMOOTH_COLOR_TRANSITION_STYLENAME);
        map.addStyleName(MapLayout.TRANSPARENT_BG_STYLENAME);
        map.setWidth(800, Unit.PIXELS);
        map.setHeight(Math.round(800.0 / USStatesMap.DEFAULT_ASPECT_RATIO), Unit.PIXELS);

        map.addMapLayoutClickListener(this::mapClicked);

        map.setStyleNamesToItems(MapColors.BLUE, USState.CALIFORNIA);
        map.setStyleNamesToItems(MapColors.ORANGE, USState.TEXAS);
        map.setStyleNamesToItems(MapColors.PURPLE, USState.ILLINOIS);

        setMargin(true);
        setSpacing(true);
        setWidth(100, Unit.PERCENTAGE);

        addComponents(createTopBar(), map);
    }

    private HorizontalLayout createTopBar() {
        HorizontalLayout bar = new HorizontalLayout();
        bar.setSpacing(true);

        Button menu = new Button();
        menu.setIcon(VaadinIcons.MENU);
        menu.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(MenuView.VIEW_NAME));

        CheckBox hideText = new CheckBox("Hide text");
        hideText.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.addStyleName(USStatesMap.HIDE_TEXTS_STYLESNAME);
            } else {
                map.removeStyleName(USStatesMap.HIDE_TEXTS_STYLESNAME);
            }
        });

        CheckBox hidePR = new CheckBox("Hide PR");
        hidePR.addValueChangeListener(e -> {
            if(e.getValue()) {
                map.addStyleName(USStatesMap.HIDE_PUERTO_RICO_STYLESNAME);
            } else {
                map.removeStyleName(USStatesMap.HIDE_PUERTO_RICO_STYLESNAME);
            }
        });

        Button shade = new Button("Shade");
        shade.addClickListener(e -> {
            USState.stream().forEach(s -> {
                int randomColorValue = random.nextInt(208);
                map.setStateColor(s, randomColorValue, randomColorValue, 255);
            });
        });

        Button clearColor = new Button("Clear");
        clearColor.addClickListener(e -> {
            USState.stream().forEach(s -> map.clearStateColor(s));
        });

        bar.addComponents(menu, hideText, hidePR, shade, clearColor);
        return bar;
    }

    private void mapClicked(MapLayoutClickEvent<USState> event) {
        event.getMapItem().ifPresent(state -> {
           Notification.show("State " + state.getName() + " clicked");
           if(!selectedStates.remove(state)) {
               selectedStates.add(state);
               map.setStateColor(state, 255, 0, 0);
           } else {
               map.clearStateColor(state);
           }
        });
    }
}
