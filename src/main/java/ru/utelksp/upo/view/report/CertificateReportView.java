package ru.utelksp.upo.view.report;

import ar.com.fdvs.dj.domain.ExpressionHelper;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.vaadin.reports.PrintPreviewReport;
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
import java.util.List;

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

    public static final String VIEW_NAME = "Отчет по сертификатам";

    @SuppressWarnings("Duplicates")
    @PostConstruct
    private void init() {
        SplitLayout mainLayout = new SplitLayout(reportContainer, menuLayout);
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
        menuLayout.add(comboBoxLayout, buildReportButton);
    }

    /**
     * Формирует отчет
     */
    private Component buildSimpleReport() {
        var employeeId = employeeCombobox.getOptionalValue().map(Employee::getId).orElse(null);
        var certificateId = certificateCombobox.getOptionalValue().map(Certificate::getId).orElse(null);
        var pcId = pcCombobox.getOptionalValue().map(Computer::getId).orElse(null);

        PrintPreviewReport<CertificateReportDto> report = new PrintPreviewReport<>();
        getReportBuilder(report);

        report.setItems(certificateRepository.findWithParam(employeeId, certificateId, pcId));
        var div = new Div(report);
        div.setSizeFull();
        return div;
    }

    /**
     * Настраивает сетку отчета
     */
    private void getReportBuilder(PrintPreviewReport<CertificateReportDto> report) {
        report.getReportBuilder()
                .setMargins(20, 20, 40, 40)
                .setTitle(VIEW_NAME)
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

    /**
     * Сформировать отчет
     */
    private Component buildDownloadableReport() {
        PrintPreviewReport<CertificateReportDto> report = new PrintPreviewReport<>();
        getReportBuilder(report);
        SerializableSupplier<List<? extends CertificateReportDto>> itemsSupplier = () -> certificateRepository.findWithParam(null, null, null);
//        report.setItems(itemsSupplier.get());

        HorizontalLayout anchors = new HorizontalLayout();

        var format = PrintPreviewReport.Format.PDF;
        Anchor anchor = new Anchor(report.getStreamResource("certificate-report." + format.name().toLowerCase(), itemsSupplier, format), format.name());
        anchor.getElement().setAttribute("download", true);
        anchors.add(anchor);

        VerticalLayout layout = new VerticalLayout(anchors, report);
        layout.getElement().setAttribute("theme", "spacing");
        return layout;
    }
}
