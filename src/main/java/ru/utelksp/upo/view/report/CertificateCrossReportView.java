package ru.utelksp.upo.view.report;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.service.CertificateService;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.service.ProgramService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;

import static java.util.Objects.nonNull;

/**
 * Форма для крос-отчета по программа и сертификатам
 */
@Route(value = "certificateCrossReport", layout = MainLayout.class)
@PageTitle("Кросс-отчет ПО и сертификаты")
@UIScope
@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class CertificateCrossReportView extends VerticalLayout {
    private final ProgramService programService;
    private final CertificateService certificateService;
    private final EmployeeService employeeService;
    private VerticalLayout menuLayout = new VerticalLayout();
    private VerticalLayout reportContainer = new VerticalLayout();

    public static final String VIEW_NAME = "Кросс-отчет ПО и сертификаты";

    @SuppressWarnings("Duplicates")
    @PostConstruct
    private void init() {
        SplitLayout mainLayout = new SplitLayout(reportContainer, menuLayout);
        mainLayout.setSizeFull();
        mainLayout.setSplitterPosition(80);
        add(mainLayout);

        ComboBox<Employee> employeeCombobox = new ComboBox<>();
        employeeCombobox.setItems(employeeService.findAll());
        employeeCombobox.setLabel("Пользователь");
        employeeCombobox.setSizeFull();
        employeeCombobox.setItemLabelGenerator(Employee::getShortFio);
        employeeCombobox.setRenderer(TemplateRenderer.<Employee>of("<div>[[item.name]]</div>")
                .withProperty("name", Employee::getShortFio));
        employeeCombobox.addValueChangeListener(e -> {
            reportContainer.removeAll();
            if (nonNull(e.getValue())) {
                Div tableDiv = getReport(e.getValue());
                reportContainer.add(tableDiv);
            }
        });
        menuLayout.add(employeeCombobox);
    }

    /**
     * Формирует отчет по пользователю
     *
     * @param employee пользователь
     * @return компонент с отчетом
     */
    @NonNull
    private Div getReport(@NonNull Employee employee) {
        var tableDiv = new Div();
        tableDiv.setClassName("divTable blueTable");
        var firstRowLayout = new Div();
        firstRowLayout.setClassName("divTableRow");
        firstRowLayout.add(getItemDiv(employee.getShortFio()));
        tableDiv.add(firstRowLayout);
        var programs = programService.findByEmployeeId(employee.getId());
        var certificates = certificateService.findByEmployeeId(employee.getId());
        programs.forEach(p -> firstRowLayout.add(getLinkDiv(p.getName(), p.getId())));
        certificates.forEach(c -> {
            var certRowLayout = new Div();
            certRowLayout.setClassName("divTableRow");
            certRowLayout.add(getItemDiv(c.getName()));
            programs.forEach(program -> {
                if (isCross(c, program)) {
                    certRowLayout.add(getItemDiv(c.getDateEnd().toString()));
                } else {
                    certRowLayout.add(getDiv());
                }
            });
            tableDiv.add(certRowLayout);
        });
        return tableDiv;
    }

    /**
     * Проверяет пересечение программы и сертификата
     *
     * @param cert    сертификат
     * @param program ПО
     * @return результат проверки
     */
    private boolean isCross(Certificate cert, Program program) {
        return cert.getPrograms().stream()
                .map(Program::getId)
                .anyMatch(id -> program.getId().equals(id));
    }

    /**
     * Формирует пустой блок
     *
     * @return компонент
     */
    private Component getDiv() {
        Div div = new Div();
        div.setClassName("divTableCell");
        return div;
    }

    /**
     * Формирует блок с текстом
     *
     * @param text текст
     * @return компонент
     */
    private Component getItemDiv(String text) {
        Div div = new Div();
        div.setClassName("divTableCell");
        div.add(new Label(text));
        return div;
    }

    /**
     * Добавить на форму блок с ссылкой
     *
     * @param text      текст ссылки
     * @param programId идентификатор программы
     * @return компонент
     */
    private Component getLinkDiv(String text, Long programId) {
        Div div = new Div();
        div.setClassName("divTableCell");
        var link = new Anchor("program/" + programId, text);
        div.add(link);
        return div;
    }
}
