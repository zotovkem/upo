package ru.utelksp.upo.view.report;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.vaadin.reports.PrintPreviewReport;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author Created by ZotovES on 24.04.2019
 * Реализация класса для формирования отчетов
 */
public class ReportBuilder<T> extends PrintPreviewReport<T> {
    private String imageServletPathPattern = "report-image?image={0}";

    @Override
    protected DynamicReportBuilder buildReportBuilder() {
        return new DynamicReportBuilder()
                .setUseFullPageWidth(true)
                .setWhenNoData("(no data)", new Style());
    }

    /**
     * Формирует отчет
     *
     * @param items список отображаемых данных
     */
    @Override
    public void setItems(List<? extends T> items) {
        try {
            if (report == null) {
                report = reportBuilder.build();
            }
            print = buildJasperPrint(items, report);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Вывести html отчет
     */
    public void getHtmlComponent() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlExporter exporter = new HtmlExporter();

            SimpleHtmlExporterConfiguration reportExportConfiguration = new SimpleHtmlExporterConfiguration();
            reportExportConfiguration.setHtmlHeader(getHtmlHeader());
            reportExportConfiguration.setHtmlFooter(getHtmlFooter());

            SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(outputStream);
            exporterOutput.setImageHandler(new WebHtmlResourceHandler(imageServletPathPattern));

            exporter.setExporterOutput(exporterOutput);
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setConfiguration(reportExportConfiguration);
            exporter.exportReport();
            outputStream.flush();

            Html htmlContent = new Html(outputStream.toString(StandardCharsets.UTF_8.name()));
            Div htmlContainer = new Div(htmlContent);
            htmlContainer.addClassName(PrintPreviewReport.class.getSimpleName() + "-htmlContainer");

            mainLayout.removeAll();
            mainLayout.add(htmlContainer);
        } catch (JRException |
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить поток для pdf файла
     *
     * @param fileName имя файла
     * @return поток
     */
    public com.vaadin.flow.server.StreamResource getPdf(String fileName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimplePdfExporterConfiguration reportExportConfiguration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(reportExportConfiguration);
            exporter.exportReport();
            report.setFontsMap(Map.of("Arial", new Font("DejaVu Sans", 0, 12)));

            outputStream.flush();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new com.vaadin.flow.server.StreamResource(fileName, () -> inputStream);
        } catch (JRException |
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить сформированный отчет
     *
     * @return отчет
     */
    public JasperPrint getJasperPrint() {
        return print;
    }

    /**
     * Получить шаблон
     *
     * @return текст html
     */
    private String getHtmlHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("  <title></title>");
        sb.append("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
        sb.append("  <style type=\"text/css\">");
        sb.append("    a {text-decoration: none}");
        sb.append("  </style>");
        sb.append("</head>");
        sb.append("<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">");
        sb.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        sb.append("<tr><td align=\"left\">");

        return sb.toString();
    }

    /**
     * Получить html шаблон для подвала отчета
     *
     * @return текст html
     */
    private String getHtmlFooter() {
        return "<td></td>";
    }
}
