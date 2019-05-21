package ru.utelksp.upo.view.component;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.lang.NonNull;
import org.vaadin.reports.PrintPreviewReport;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.view.report.ReportBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.com.fdvs.dj.domain.constants.Font.PDF_ENCODING_CP1251_Cyrillic;

/**
 * @author Created by ZotovES on 23.04.2019
 * Фабрика вспомогательных компонентов
 */
public class FactoryComponent {

    /**
     * Получить компонент календарь
     *
     * @param caption заголовок компонента
     * @return компонент
     */
    public static DatePicker getDatePicker(String caption) {
        DatePicker datePicker = new DatePicker(caption);
        datePicker.setWidthFull();
        datePicker.setLocale(new Locale("ru"));
        datePicker.setI18n(getI18n());
        return datePicker;
    }

    /**
     * Получить подписи из ордера для итемов в комбобокса ордеров
     */
    @NonNull
    public static ItemLabelGenerator<Order> getItemListEmployees() {
        return order -> order.getEmployees().stream()
                .map(Employee::getShortFio)
                .collect(Collectors.joining(", "));
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса пользователей
     */
    @NonNull
    public static ItemLabelGenerator<Certificate> getItemEmployeeOfCertificate() {
        return certificate -> Optional.ofNullable(certificate)
                .map(Certificate::getEmployee)
                .map(Employee::getShortFio).orElse("");
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса пользователей
     */
    @NonNull
    public static ItemLabelGenerator<Employee> getItemEmployee() {
        return employee -> Optional.ofNullable(employee)
                .map(Employee::getShortFio).orElse("");
    }

    /**
     * Получить подписи из компьютера для итемов в комбобокса компьютера
     */
    @NonNull
    public static ItemLabelGenerator<Computer> getItemComputer() {
        return com -> Optional.ofNullable(com)
                .map(Computer::getName)
                .orElse("");
    }

    /**
     * Получить подписи из организации для итемов в комбобокса организаций
     */
    @NonNull
    public static ItemLabelGenerator<Organization> getItemOrganization() {
        return o -> Optional.ofNullable(o)
                .map(Organization::getName)
                .orElse("");
    }

    /**
     * Получить подписи из сертификата для итемов в комбобокса сертификатов
     */
    @NonNull
    public static ItemLabelGenerator<Certificate> getItemCertificate() {
        return o -> Optional.ofNullable(o)
                .map(Certificate::getName)
                .orElse("");
    }

    /**
     * Получить подписи из Видов использования для итемов в комбобокса вид использования
     */
    @NonNull
    public static ItemLabelGenerator<TypeUsing> getItemTypeUsing() {
        return o -> Optional.ofNullable(o)
                .map(TypeUsing::getName)
                .orElse("");
    }


    /**
     * Получить шрифт для отчетов
     *
     * @return шрифт
     */
    @NonNull
    public static Font getReportFont() {
        var font = new Font();
        font.setFontName("DejaVu Sans");
        font.setPdfFontEmbedded(true);
        font.setPdfFontEncoding(PDF_ENCODING_CP1251_Cyrillic);
        return font;
    }


    /**
     * Получить стиль для строки сумирования
     *
     * @return стиль
     */
    @NonNull
    public static Style getAmountStyle(Font font) {
        var amountStyle = new Style();
        amountStyle.setFont(font);
        amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        return amountStyle;
    }

    /**
     * Получить стиль для строки подзаголовка
     *
     * @return стиль
     */
    @NonNull
    public static Style getSubtitleStyle(Font font) {
        return new Style();
    }

    /**
     * Получить стиль для строки заголовка
     *
     * @return стиль
     */
    @NonNull
    public static Style getTitleStyle(Font font) {
        Style titleStyle = new Style();
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * Получить стиль для первой строки таблицы
     *
     * @return стиль
     */
    @NonNull
    public static Style getHeaderStyle(Font font) {
        Style headerStyle = new Style();
        headerStyle.setFont(font);
        headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        headerStyle.setBackgroundColor(new Color(230, 230, 230));
        headerStyle.setBorder(Border.THIN());
        headerStyle.setTransparency(Transparency.OPAQUE);
        return headerStyle;
    }

    /**
     * Получить стиль для строк детализации
     *
     * @param font шрифт
     * @return стиль
     */
    @NonNull
    public static Style getDetailStyle(Font font) {
        Style detailStyle = new Style();
        detailStyle.setFont(font);
        detailStyle.setBorder(Border.THIN());
        detailStyle.getBorder().setColor(Color.BLACK);
        detailStyle.setStretchWithOverflow(true);
        return detailStyle;
    }

    /**
     * Получить ссылку на pdf файл.
     */
    public static Anchor getAnchorPdf(ReportBuilder report) {
        var format = PrintPreviewReport.Format.PDF;

        HorizontalLayout anchors = new HorizontalLayout();

        Anchor anchor = new Anchor(report.getPdf("file-report." + format.name().toLowerCase()), format.name());
        anchor.getElement().setAttribute("open", true);
//        anchor.getElement().setAttribute("isBlank", true);
        anchors.add(anchor);

        VerticalLayout layout = new VerticalLayout(anchors, report);
        layout.getElement().setAttribute("theme", "spacing");
        return anchor;
    }

    /**
     * Получить локализацию для календаря
     *
     * @return локализация
     */
    private static DatePicker.DatePickerI18n getI18n() {
        return new DatePicker.DatePickerI18n()
                .setWeek("неделя")
                .setCalendar("календарь")
                .setClear("очистить")
                .setToday("сегодня")
                .setCancel("отмена")
                .setFirstDayOfWeek(1)
                .setMonthNames(Arrays.asList("январь", "февраль", "март", "апрель", "май", "июнь",
                        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"))
                .setWeekdays(Arrays.asList("воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"))
                .setWeekdaysShort(Arrays.asList("вс", "пн", "вт", "ср", "чт", "пт", "сб"));
    }
}