package com.example.application.views.ganttchart;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


@PageTitle("GANTT Chart")
@Route(value = "GANTT", layout = MainLayout.class)
@Menu(order = 1, icon = "line-awesome/svg/home-solid.svg")
@AnonymousAllowed
public class GANTTChartView extends VerticalLayout {

    private static final Instant TODAY = Instant.now()
            .truncatedTo(ChronoUnit.DAYS);

    public GANTTChartView() {
        setSpacing(false);

        Chart chart = new Chart(ChartType.GANTT);

        final Configuration configuration = chart.getConfiguration();

        configuration.setTitle("Gantt Chart with custom labels");
        configuration.setTooltip(new Tooltip(true));
        configuration.getxAxis().setCurrentDateIndicator(true);

        XAxis xAxis = configuration.getxAxis();
        xAxis.setMinPadding(0.05);
        xAxis.setMaxPadding(0.05);

        YAxis yAxis = configuration.getyAxis();
        yAxis.setCategories("Prototyping", "Development", "Testing");

        PlotOptionsGantt plotOptionsGantt = new PlotOptionsGantt();
        plotOptionsGantt.setShowCheckbox(true);
        configuration.setPlotOptions(plotOptionsGantt);

        // tag::snippet[]
        final GanttSeries projectDevelopmentSeries = createProjectDevelopmentSeries();
        PlotOptionsGantt seriesPlotOptions = new PlotOptionsGantt();
        var dataLabels = new ArrayList<DataLabels>();

        var assigneeLabel = new DataLabels(true);
        assigneeLabel.setAlign(HorizontalAlign.LEFT);
        assigneeLabel.setFormat("{point.custom.assignee}");
        dataLabels.add(assigneeLabel);

        var endDateLabel = new DataLabels(true);
        endDateLabel.setAlign(HorizontalAlign.RIGHT);
        endDateLabel.setFormat("{point.end:%e. %b}");
        dataLabels.add(endDateLabel);

        var avatarLabel = new DataLabels(true);
        avatarLabel.setAlign(HorizontalAlign.LEFT);
        avatarLabel.setUseHTML(true);
        avatarLabel.setFormat(
                "<div style=\"width: 20px; height: 20px; overflow: hidden; margin-left: -30px\">"
                        + "                <img src=\"https://ui-avatars.com/api/?background=random&color=fff&size=20&length=1&rounded=true&name={point.custom.assignee}\"> "
                        + "                </div>");
        // dataLabels.add(avatarLabel);

        seriesPlotOptions.setDataLabels(dataLabels);
        projectDevelopmentSeries.setPlotOptions(seriesPlotOptions);
        // end::snippet[]
        configuration.addSeries(projectDevelopmentSeries);

        add(chart);
    }

    private GanttSeries createProjectDevelopmentSeries() {
        GanttSeries series = new GanttSeries();
        series.setName("Project 1");

        GanttSeriesItem item;

        item = new GanttSeriesItem(0, todayPlus(1), todayPlus(3));
        item.setCustom(new TaskCustomData("John"));
        item.setCompleted(new Completed(0.15, SolidColor.LIGHTBLUE));
        series.add(item);

        item = new GanttSeriesItem(1, todayPlus(2), todayPlus(5));
        item.setCustom(new TaskCustomData("William"));
        item.setCompleted(new Completed(0.25, SolidColor.LIGHTBLUE));
        series.add(item);

        item = new GanttSeriesItem(2, todayPlus(5), todayPlus(7));
        item.setCustom(new TaskCustomData("Jane"));
        item.setCompleted(new Completed(0.35, SolidColor.LIGHTBLUE));
        series.add(item);

        item = new GanttSeriesItem(1, todayPlus(8), todayPlus(16));
        item.setCustom(new TaskCustomData("John"));
        item.setCompleted(new Completed(0.45, SolidColor.LIGHTBLUE));
        series.add(item);

        item = new GanttSeriesItem(2, todayPlus(10), todayPlus(23));
        item.setCustom(new TaskCustomData("Jane"));
        item.setCompleted(new Completed(0.55, SolidColor.LIGHTBLUE));
        series.add(item);

        return series;
    }

    private Instant todayPlus(int days) {
        return TODAY.plus(days, ChronoUnit.DAYS);
    }

    @SuppressWarnings("unused")
    static class TaskCustomData extends AbstractConfigurationObject {
        private String assignee;

        public TaskCustomData(String assignee) {
            this.assignee = assignee;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }

}
