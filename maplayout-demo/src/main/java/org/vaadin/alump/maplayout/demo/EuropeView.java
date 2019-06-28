package org.vaadin.alump.maplayout.demo;

import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
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
        map.addStyleName("demo-europe");
        map.setWidth(800, Unit.PIXELS);
        map.setHeight(Math.round(800.0 / EuropeMap.DEFAULT_ASPECT_RATIO), Unit.PIXELS);
        map.addStyleName(EuropeMap.SMOOTH_COLOR_TRANSITION_STYLENAME);
        map.addStyleName(MapLayout.TRANSPARENT_BG_STYLENAME);
        map.addStyleName(EuropeMap.CLICKABLE_STYLENAME);
        map.addMapLayoutClickListener(this::onMapLayoutClicked);

        map.setTooltip(EuropeanCountry.FINLAND, "At start, only <b>Finland</b> has tooltip");
        map.setTooltipContentMode(ContentMode.HTML);

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

        CheckBox overlay = new CheckBox("Overlay components");
        overlay.addValueChangeListener(this::overlayChanged);

        CheckBox tooltips = new CheckBox("Tooltips");
        tooltips.addValueChangeListener(this::tooltipsChanged);

        bar.addComponents(menu, greenIt, shade, eu, clear, overlay, tooltips);
        bar.setComponentAlignment(overlay, Alignment.BOTTOM_CENTER);
        bar.setComponentAlignment(tooltips, Alignment.BOTTOM_CENTER);
        return bar;
    }

    private void tooltipsChanged(HasValue.ValueChangeEvent<Boolean> event) {
        if(event.getValue()) {
            map.addStyleName(EuropeMap.HIGHLIGHT_HOVER_STYLENAME);
            map.setTooltipContentMode(ContentMode.TEXT);
            EuropeanCountry.stream().forEach(ec -> {
                String tooltip = ec.getFlag() + " " + ec.getCountryCode().getName();
                map.setTooltip(ec, tooltip);
            });
        } else {
            map.removeStyleName(EuropeMap.HIGHLIGHT_HOVER_STYLENAME);
            map.removeAllTooltips();
        }
    }

    private void overlayChanged(HasValue.ValueChangeEvent<Boolean> event) {
        if(event.getValue()) {
            Label label = new Label("Map of Europe");
            label.addStyleName("title");
            map.addComponentToViewBox(label, 200.0, 200.0);

            Label icon = new Label(VaadinIcons.LOCATION_ARROW.getHtml(), ContentMode.HTML);
            icon.addStyleName("arrow");
            map.addComponentToViewBox(icon, 5400.0, 2800.0);

            Button button = new Button("Highlight Brexit",
                    e -> map.setCountryColor(EuropeanCountry.UK, 0, 0, 0));
            map.addComponentToViewBox(button, 422.0, 3200.0);
        } else {
            map.removeAllComponents();
        }
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
            greens.remove(code);
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
