package org.vaadin.alump.maplayout.demo;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.alump.maplayout.EuropeMap;
import org.vaadin.alump.maplayout.EuropeanCountry;
import org.vaadin.alump.maplayout.MapLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EuropeChartView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "europeChart";
    private Set<EuropeanCountry> clickToggled = new HashSet<>();
    private Map<EuropeanCountry, Double> values = new HashMap<>();
    private final static Random RANDOM = new Random(0xDEADBEEF);

    private DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.##");

    private final static Double MAX = 100.0;
    private final static Double MIN = 0.0;
    private final static String UNIT = "%";

    private EuropeMap map;

    public EuropeChartView() {
        map = new EuropeMap();
        map.addStyleName("demo-europe-chart");
        map.setWidth(800, Unit.PIXELS);
        map.setHeight(Math.round(800.0 / EuropeMap.DEFAULT_ASPECT_RATIO), Unit.PIXELS);
        map.addStyleName(EuropeMap.SMOOTH_COLOR_TRANSITION_STYLENAME);
        map.addStyleName(MapLayout.TRANSPARENT_BG_STYLENAME);
        map.setTooltipContentMode(ContentMode.HTML);

        map.addComponentToViewBox(createLegend(), 0.0, 0.0);

        addComponents(createTopBar(), map);

        regenerateValues();

    }

    private Component createTopBar() {
        HorizontalLayout bar = new HorizontalLayout();
        bar.setSpacing(true);

        Button menu = new Button();
        menu.setIcon(VaadinIcons.MENU);
        menu.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(MenuView.VIEW_NAME));

        Button regenerate = new Button("Regenerate", e -> regenerateValues());

        bar.addComponents(regenerate);

        return bar;
    }

    private void regenerateValues() {
        EuropeanCountry.stream().forEach(ec -> {
            double value = MIN + RANDOM.nextDouble() * (MAX - MIN);
            values.put(ec, value);
            map.setTooltip(ec, ec.getFlag() + " " + ec.getCountryCode().getName() + ": <b>"
                    + NUMBER_FORMAT.format(value)
                    + " " + UNIT + "</b>");

            int color = (int)Math.round(value / (MAX - MIN) * 255);
            String hexString = Integer.toHexString(color);
            while(hexString.length() < 2) {
                hexString = "0" + hexString;
            }

            map.setCountryColor(ec, "#" + hexString + "0000");
        });
    }

    private Component createLegend() {
        CssLayout legendLayout = new CssLayout();
        legendLayout.addStyleName("legend-box");

        Label title = new Label("\uD83D\uDE0D this add-on " + UNIT);
        title.addStyleName("legend-title");

        CssLayout gradient = new CssLayout();
        gradient.addStyleName("legend-gradient");

        Label maxLabel = new Label(NUMBER_FORMAT.format(MAX) + " " + UNIT);
        maxLabel.addStyleName("legend-max-label");

        Label minLabel = new Label(NUMBER_FORMAT.format(MIN)+ " " + UNIT);
        minLabel.addStyleName("legend-min-label");

        legendLayout.addComponents(title, gradient, maxLabel, minLabel);

        return legendLayout;
    }
}
