package com.example.application.views.ganttchart;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.charts.model.style.Style;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;


@PageTitle("GANTT Chart 2")
@Route(value = "GANTT 2", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Menu(order = 2, icon = "line-awesome/svg/home-solid.svg")
@AnonymousAllowed
public class GANTTChartView2 extends VerticalLayout {

    public GANTTChartView2() {
        setSpacing(false);
        add(createOpenBidRequestTimeline());
    }

    private void configureYearsMonthsWeeksOnXAxis(Configuration configuration) {
        configureDaysAxis(configuration);
        configureWeeksAxis(configuration);          // Setting week together with days will make a mess :-)
        configureMonthsAxis(configuration);
    }

    private void configureDaysAxis(Configuration configuration) {
        XAxis axis = new XAxis();
        configuration.addxAxis(axis);
        axis.setMinPadding(0.05);
        axis.setMaxPadding(0.05);
        axis.setUnits(new TimeUnitMultiples(TimeUnit.DAY, 1));

        final Labels labels = new Labels();
        labels.setPadding(1);
        labels.setAlign(HorizontalAlign.LEFT);
        axis.setLabels(labels);

        axis.setGrid(new AxisGrid());
        axis.getGrid().setCellHeight(20);
    }

    private void configureWeeksAxis(Configuration configuration) {
        XAxis axis = new XAxis();
        configuration.addxAxis(axis);
        axis.setMinPadding(0.05);
        axis.setMaxPadding(0.05);
        axis.setUnits(new TimeUnitMultiples(TimeUnit.WEEK, 1));

        final Labels labels = new Labels();
        labels.setPadding(1);
        labels.setAlign(HorizontalAlign.LEFT);
        axis.setLabels(labels);

        axis.setGrid(new AxisGrid());
        axis.getGrid().setCellHeight(20);
    }

    private void configureMonthsAxis(Configuration configuration) {
        XAxis axis = new XAxis();
        configuration.addxAxis(axis);
        axis.setMinPadding(0.05);
        axis.setMaxPadding(0.05);
        axis.setTickInterval(1000 * 60 * 60 * 24 * 30L);
        axis.setUnits(new TimeUnitMultiples(TimeUnit.MONTH, 1));

        final Labels labels = new Labels();
        labels.setAlign(HorizontalAlign.LEFT);
        var style = new Style();
        style.setFontSize("16px");
        labels.setStyle(style);
        axis.setLabels(labels);

        axis.setGrid(new AxisGrid());
        axis.getGrid().setCellHeight(20);
    }


    private Component createOpenBidRequestTimeline() {
        Div content = new Div();
        content.setSizeFull();
        content.add(bidRequestGantt());
        return content;
    }


    private Chart bidRequestGantt()  {
        Chart chart = new Chart(ChartType.GANTT);
        chart.setSizeFull();

// Modify the default configuration
        Configuration configuration = chart.getConfiguration();
        configuration.setTitle("Active Bid Request Timeline");
        configureYearsMonthsWeeksOnXAxis(configuration);

// Add data
        GanttSeries series = new GanttSeries("");
        PlotOptionsGantt plotOptionsGantt = new PlotOptionsGantt();
        series.setPlotOptions(plotOptionsGantt);

        GanttSeriesItem bidGanttItem = new GanttSeriesItem("ATEA NAS II",
            LocalDateTime.now().toInstant(ZoneOffset.ofHours(1)).truncatedTo(ChronoUnit.DAYS),
            LocalDateTime.now().plusDays(23).toInstant(ZoneOffset.ofHours(1)).truncatedTo(ChronoUnit.DAYS));
        bidGanttItem.setColor(SolidColor.CADETBLUE);
        bidGanttItem.setCompleted(new Completed(25, new SolidColor("#0f728f")));
        series.add(bidGanttItem);

        configuration.addSeries(series);
        return chart;
    }

}
