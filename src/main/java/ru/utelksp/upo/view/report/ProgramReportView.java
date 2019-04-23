package ru.utelksp.upo.view.report;

import ar.com.fdvs.dj.domain.ExpressionHelper;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.vaadin.reports.PrintPreviewReport;
import ru.utelksp.upo.common.dto.ProgramReportDto;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.repository.ProgramRepository;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.service.OrderService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Форма для отчета по прогрманому обеспечению
 */
@SuppressWarnings("Duplicates")
@Route(value = "programReport", layout = MainLayout.class)
@PageTitle("Отчет по программному обеспечению")
@UIScope
@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class ProgramReportView extends VerticalLayout {
    private final ProgramRepository programRepository;
    private final EmployeeService employeeService;
    private final OrderService orderService;

    private VerticalLayout menuLayout = new VerticalLayout();
    private VerticalLayout reportContainer = new VerticalLayout();
    private ComboBox<Employee> employeeCombobox = new ComboBox<>();
    private ComboBox<Order> orderCombobox = new ComboBox<>();

    public static final String VIEW_NAME = "Отчет по ПО";

    @PostConstruct
    private void init() {
        SplitLayout mainLayout = new SplitLayout(reportContainer, menuLayout);
        menuLayout.setClassName("notPrintDiv");
        mainLayout.setSizeFull();
        mainLayout.setSplitterPosition(80);
        add(mainLayout);

        Button buildReportButton = new Button("Сформировать", e -> {
            reportContainer.removeAll();
            reportContainer.add(buildSimpleReport());
        });

        employeeCombobox.setItems(employeeService.findAll());
        employeeCombobox.setLabel("Пользователь");
        employeeCombobox.setSizeFull();
        employeeCombobox.setItemLabelGenerator(Employee::getShortFio);
        employeeCombobox.setRenderer(TemplateRenderer.<Employee>of("<div>[[item.name]]</div>")
                .withProperty("name", Employee::getShortFio));
        orderCombobox.setItems(orderService.findAll());
        orderCombobox.setLabel("Приказ");
        orderCombobox.setSizeFull();
        orderCombobox.setItemLabelGenerator(Order::getOrderNumber);
        orderCombobox.setRenderer(TemplateRenderer.<Order>of("<div>[[item.number]]</div>")
                .withProperty("number", Order::getOrderNumber));

        var comboBoxLayout = new VerticalLayout(employeeCombobox, orderCombobox);
        menuLayout.add(comboBoxLayout, buildReportButton);
    }

    /**
     * Формирует отчет
     */
    private Component buildSimpleReport() {
        var employeeId = employeeCombobox.getOptionalValue().map(Employee::getId).orElse(null);
        var orderId = orderCombobox.getOptionalValue().map(Order::getId).orElse(null);

        PrintPreviewReport<ProgramReportDto> report = new PrintPreviewReport<>();
        getReportBuilder(report);

        report.setItems(programRepository.findWithParam(employeeId, orderId));
        var div = new Div(report);
        div.setSizeFull();
        div.setClassName("printDiv");
        return div;
    }

    /**
     * Настраивает сетку отчета
     */
    private void getReportBuilder(PrintPreviewReport<ProgramReportDto> report) {
        Style detailStyle = new Style();
        detailStyle.setBorder(Border.THIN());
        detailStyle.getBorder().setColor(Color.BLACK);
        detailStyle.setStretchWithOverflow(true);
        Style headerStyle = new Style();
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setBackgroundColor(new Color(230, 230, 230));
        headerStyle.setBorder(Border.THIN());
        headerStyle.setTransparency(Transparency.OPAQUE);
        Style titleStyle = new Style();
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        Style subtitleStyle = new Style();
        Style amountStyle = new Style();
        amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

        report.getReportBuilder()
                .setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
                .setTitle(VIEW_NAME)
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true)
                .setMargins(20, 0, 0, 20)
                .addColumn(ColumnBuilder.getNew()
                        .setCustomExpression(ExpressionHelper.getRecordsInReport())
                        .setTitle("Порядковый номер")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("nameProgram", String.class)
                        .setTitle("Наименование ПО")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("employeeFio", String.class)
                        .setTitle("ФИО пользователя")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("number", String.class)
                        .setTitle("Номер приказа")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("orderDate", LocalDate.class)
                        .setTitle("Дата приказа")
                        .setTextFormatter(DateTimeFormatter.ISO_DATE.toFormat())
                        .build());
    }
}
