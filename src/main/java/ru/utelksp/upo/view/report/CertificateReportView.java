package ru.utelksp.upo.view.report;

import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.vaadin.reports.PrintPreviewReport;
import ru.utelksp.upo.common.dto.CertificateReportDto;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.repository.CertificateRepository;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    private HorizontalLayout menuLayout = new HorizontalLayout();
    private HorizontalLayout reportContainer = new HorizontalLayout();

    public static final String VIEW_NAME = "Отчет по сертификатам";
    private static final List<String> FORMAT_REPORT = List.of("PDF", "XLS", "DOCX");

    @PostConstruct
    private void init() {
        setSizeFull();
        Button buildReportButton = new Button("Сформировать", e -> {
            reportContainer.removeAll();
//            reportContainer.add(buildSimpleReport());
            reportContainer.add(buildDownloadableReport());
        });

//        Button downloadButton = new Button("Печать", e -> {
//            reportContainer.removeAll();
//            reportContainer.add(buildDownloadableReport());
//        });

//        reportContainer.getElement().setAttribute("theme", "");
        Div div = new Div(reportContainer);
        div.setSizeFull();

        menuLayout.add(buildReportButton);
        add(menuLayout);
        add(div);

    }

    /**
     * Формирует отчет
     */
    private Component buildSimpleReport() {
        PrintPreviewReport<CertificateReportDto> report = new PrintPreviewReport<>();
        getReportBuilder(report);

        report.setItems(certificateRepository.findWithParam(null, null, null));
        return report;
    }

    /**
     * Мапит список сертификатов в DTO для отображения в отчете
     *
     * @return список DTO
     */
    @NotNull
    private List<CertificateReportDto> mappingCertificateToDto(Collection<Certificate> listCertificate) {
        return listCertificate.stream()
                .map(c -> CertificateReportDto.builder()
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Настраивает сетку отчета
     */
    private void getReportBuilder(PrintPreviewReport<CertificateReportDto> report) {
        report.getReportBuilder()
//                .setMargins(20, 20, 40, 40)
                .setTitle(VIEW_NAME)
                .setPrintBackgroundOnOddRows(true)
//                .setUseFullPageWidth(true)
//                .addColumn(ColumnBuilder.getNew()
//                        .setCustomExpression(ExpressionHelper.getRecordsInReport())
//                        .setTitle("Порядковый номер")
//                        .build())
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
                        .setTextFormatter(DateTimeFormatter.ISO_LOCAL_DATE.toFormat())
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
        report.setItems(itemsSupplier.get());

        HorizontalLayout anchors = new HorizontalLayout();

        FORMAT_REPORT.forEach(f -> {
            var format = PrintPreviewReport.Format.valueOf(f);
            Anchor anchor = new Anchor(report.getStreamResource("certificate-report." + format.name().toLowerCase(), itemsSupplier, format), format.name());
            anchor.getElement().setAttribute("скачать", true);
            anchors.add(anchor);
        });

        VerticalLayout layout = new VerticalLayout(anchors, report);
        layout.getElement().setAttribute("theme", "spacing");
        return layout;
    }
}
