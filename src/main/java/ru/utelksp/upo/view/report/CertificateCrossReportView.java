package ru.utelksp.upo.view.report;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.common.dto.ProgramAndCertificateReportDto;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.service.ProgramService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Форма для крос-отчета по программа и сертификатам
 */
@Route(value = "certificateCrossReport", layout = MainLayout.class)
@PageTitle("Кросс-отчет ПО и сертификаты")
@UIScope
@org.springframework.stereotype.Component
@HtmlImport("css/shared-styles.html")
@RequiredArgsConstructor
public class CertificateCrossReportView extends VerticalLayout {
    private final ProgramService programService;
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
            if (nonNull(e)) {
                Div tableDiv = getReport(e.getValue());
                reportContainer.add(tableDiv);
            }
        });
        menuLayout.add(employeeCombobox);
    }

    @NonNull
    private Div getReport(@NonNull Employee employee) {
        List<ProgramAndCertificateReportDto> certificates = programService.getProgramAndCertificate();
        var tableDiv = new Div();
        tableDiv.setClassName("divTable blueTable");
        var firstRowLayout = new Div();
        firstRowLayout.setClassName("divTableRow");
        firstRowLayout.add(getItemDiv(employee.getShortFio()));
        tableDiv.add(firstRowLayout);
        var programs = programService.findAll();
        programs.forEach(p -> {
            int countPO = 0;
            firstRowLayout.add(getItemDiv(p.getName()));
            int finalCountPO = countPO;
            certificates.stream().filter(c -> c.getId().equals(p.getId()))
                    .forEach(c -> {
                        var certRowLayout = new Div();
                        certRowLayout.setClassName("divTableRow");
                        for (int i = 0; i < finalCountPO; i++) {
                            certRowLayout.add(getDiv());
                        }
                        certRowLayout.add(getItemDiv(c.getCertificateName()));
                        certRowLayout.add(getItemDiv(c.getDateEnd().toString()));
                        long countEmptyCell = programs.size() - certificates.stream().filter(cert -> cert.getId().equals(p.getId())).count();
                        for (int i = 0; i < countEmptyCell; i++) {
                            certRowLayout.add(getDiv());
                        }
                        tableDiv.add(certRowLayout);
                    });
            countPO++;
        });
        return tableDiv;
    }

    private Component getDiv() {
        Div div = new Div();
        div.setClassName("divTableCell");
        return div;
    }

    private Component getItemDiv(String text) {
        Div div = new Div();
        div.setClassName("divTableCell");
        div.add(new Label(text));
        return div;
    }

    /**
     * Получить провейдера для справочника пользователей
     */
    private ComboBoxProvider getEmployeeProvider() {
        return new ComboBoxProvider<>("Пользователь", employeeService.findAll(), new TextRenderer<>(Employee::getShortFio), Employee::getShortFio);
    }
}
