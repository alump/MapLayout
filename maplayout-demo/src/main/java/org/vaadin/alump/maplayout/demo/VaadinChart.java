package org.vaadin.alump.maplayout.demo;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.CssLayout;

import java.util.Random;

/**
 * Created by alump on 02/04/2017.
 */
public class VaadinChart extends CssLayout {

    private static Random RAND = new Random(0xDEADBEEF);

    public VaadinChart(String title) {
        addStyleName("chart-wrapper");

        Chart chart = new Chart(ChartType.PIE);
        chart.setSizeFull();

        Configuration conf = chart.getConfiguration();

        Style titleStyle = new Style();
        titleStyle.setFontSize("10px");
        titleStyle.setColor(SolidColor.BLACK);
        conf.getTitle().setText(title);
        conf.getTitle().setStyle(titleStyle);
        conf.getTitle().setMargin(0);

        conf.getChart().setBackgroundColor(new SolidColor("transparent"));
        conf.getChart().setPlotBackgroundColor(null);

        final DataSeries series = new DataSeries();
        series.setName("Amount");
        series.add(new DataSeriesItem("Blue Pixels", getValueForPie()));
        series.add(new DataSeriesItem("Red Pixels", getValueForPie()));
        series.add(new DataSeriesItem("Green Pixels", getValueForPie()));
        conf.addSeries(series);

        PlotOptionsPie optionsPie = new PlotOptionsPie();
        optionsPie.getDataLabels().setEnabled(false);
        optionsPie.setAllowPointSelect(false);
        optionsPie.setShadow(true);
        series.setPlotOptions(optionsPie);

        chart.drawChart(conf);
        addComponent(chart);
    }

    private static double getValueForPie() {
        return Math.round(1.0 + RAND.nextDouble() * 80.0);
    }
}
