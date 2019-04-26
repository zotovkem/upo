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
import ru.utelksp.upo.common.dto.CertificateReportDto;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.repository.CertificateRepository;
import ru.utelksp.upo.service.CertificateService;
import ru.utelksp.upo.service.ComputerService;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;
import static ru.utelksp.upo.view.component.FactoryComponent.*;

/**
 * Форма для отчета по сертификатам
 */
@Route(value = "certificateReport", layout = MainLayout.class)
@PageTitle("Отчет по сертификатам")
@UIScope
@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class CertificateReportView extends VerticalLayout {
    private final CertificateRepository certificateRepository;
    private final EmployeeService employeeService;
    private final CertificateService certificateService;
    private final ComputerService computerService;

    private VerticalLayout menuLayout = new VerticalLayout();
    private VerticalLayout reportContainer = new VerticalLayout();
    private ComboBox<Employee> employeeCombobox = new ComboBox<>();
    private ComboBox<Certificate> certificateCombobox = new ComboBox<>();
    private ComboBox<Computer> pcCombobox = new ComboBox<>();
    private Anchor anchorPdf = new Anchor();

    public static final String VIEW_NAME = "Отчет по сертификатам";

    @SuppressWarnings("Duplicates")
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
            reportContainer.add(buildReport());
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
        certificateCombobox.setItems(certificateService.findAll());
        certificateCombobox.setLabel("Сертификат");
        certificateCombobox.setSizeFull();
        certificateCombobox.setItemLabelGenerator(Certificate::getName);
        certificateCombobox.setRenderer(TemplateRenderer.<Certificate>of("<div>[[item.name]]</div>")
                .withProperty("name", Certificate::getName));
        pcCombobox.setItems(computerService.findAll());
        pcCombobox.setLabel("Место установки");
        pcCombobox.setSizeFull();
        pcCombobox.setItemLabelGenerator(Computer::getName);
        pcCombobox.setRenderer(TemplateRenderer.<Computer>of("<div>[[item.name]]</div>")
                .withProperty("name", Computer::getName));

        var comboBoxLayout = new VerticalLayout(employeeCombobox, certificateCombobox, pcCombobox);
        menuLayout.add(comboBoxLayout, new HorizontalLayout(buildReportButton, printReportButton));
    }

    /**
     * Формирует отчет
     */
    @SuppressWarnings("Duplicates")
    private Component buildReport() {
        var employeeId = employeeCombobox.getOptionalValue().map(Employee::getId).orElse(null);
        var certificateId = certificateCombobox.getOptionalValue().map(Certificate::getId).orElse(null);
        var pcId = pcCombobox.getOptionalValue().map(Computer::getId).orElse(null);

        ReportBuilder<CertificateReportDto> report = new ReportBuilder<>();
        getReportBuilder(report);

        report.setItems(certificateRepository.findWithParam(employeeId, certificateId, pcId));
        anchorPdf = getAnchorPdf(report);
        menuLayout.add(anchorPdf);
        anchorPdf.setVisible(false);
        return report.getHtmlComponent();
    }

    /**
     * Настраивает сетку отчета
     */
    private void getReportBuilder(ReportBuilder<CertificateReportDto> report) {
        Font font = getReportFont();

        report.getReportBuilder()
                .setDefaultStyles(getTitleStyle(font), getSubtitleStyle(font), getHeaderStyle(font), getDetailStyle(font))
                .setTitle(VIEW_NAME)
                .setMargins(10, 10, 20, 10)
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true)
                .addColumn(ColumnBuilder.getNew()
                        .setCustomExpression(ExpressionHelper.getRecordsInReport())
                        .setTitle("Порядковый номер")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("employeeFio", String.class)
                        .setTitle("ФИО пользователя")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("name", String.class)
                        .setTitle("Наименование сертификата")
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("dateEnd", LocalDate.class)
                        .setTitle("Срок действия сертификата")
                        .setTextFormatter(DateTimeFormatter.ISO_DATE.toFormat())
                        .build())
                .addColumn(ColumnBuilder.getNew()
                        .setColumnProperty("computer", String.class)
                        .setTitle("Место установки")
                        .build());
    }
}
