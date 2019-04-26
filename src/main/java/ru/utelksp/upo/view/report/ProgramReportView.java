package ru.utelksp.upo.view.report;

import ar.com.fdvs.dj.domain.ExpressionHelper;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import ru.utelksp.upo.common.dto.ProgramReportDto;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.repository.ProgramRepository;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.service.OrderService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;
import static ru.utelksp.upo.view.component.FactoryComponent.*;

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
    private Anchor anchorPdf = new Anchor();

    public static final String VIEW_NAME = "Отчет по ПО";

    @PostConstruct
    private void init() {
        SplitLayout mainLayout = new SplitLayout(reportContainer, menuLayout);
        menuLayout.setClassName("notPrintDiv");
        mainLayout.setSizeFull();
        mainLayout.setSplitterPosition(80);
        add(mainLayout);

        Button printReportButton = new Button("Печать",
                e -> UI.getCurrent().getPage().executeJavaScript(format("window.open(\"%s\", \"_isblank\");", anchorPdf.getHref())));
        printReportButton.setEnabled(false);
        Button buildReportButton = new Button("Сформировать", e -> {
            printReportButton.setEnabled(true);
            reportContainer.removeAll();
            reportContainer.add(buildSimpleReport());
        });
        reportContainer.addDetachListener(e -> {
            printReportButton.setEnabled(false);
            reportContainer.removeAll();
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
        menuLayout.add(comboBoxLayout, new HorizontalLayout(buildReportButton, printReportButton));
    }

    /**
     * Формирует отчет
     */
    private Component buildSimpleReport() {
        var employeeId = employeeCombobox.getOptionalValue().map(Employee::getId).orElse(null);
        var orderId = orderCombobox.getOptionalValue().map(Order::getId).orElse(null);

        ReportBuilder<ProgramReportDto> report = new ReportBuilder<>();
        getReportBuilder(report);

        report.setItems(programRepository.findWithParam(employeeId, orderId));
        anchorPdf = getAnchorPdf(report);
        menuLayout.add(anchorPdf);
        anchorPdf.setVisible(false);
        return report.getHtmlComponent();
    }

    /**
     * Настраивает сетку отчета
     */
    private void getReportBuilder(ReportBuilder<ProgramReportDto> report) {
        Font font = getReportFont();

        report.getReportBuilder()
                .setDefaultStyles(getTitleStyle(font), getSubtitleStyle(font), getHeaderStyle(font), getDetailStyle(font))
                .setTitle(VIEW_NAME)
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true)
                .setMargins(10, 10, 20, 10)
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
