package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.service.OrganizationService;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.CustomGrid;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.EmployeeCrudListener;
import ru.utelksp.upo.view.listener.OrderCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 * Форма редактирования Приказов
 */
@Route(value = "order", layout = MainLayout.class)
@PageTitle("Приказы")
@UIScope
@Component
@RequiredArgsConstructor
public class OrderCrudView extends VerticalLayout {
    private final OrderCrudListener orderCrudListener;
    private final OrganizationService organizationService;
    private final EmployeeCrudListener employeeCrudListener;

    public static final String VIEW_NAME = "Приказы";
    private static final String[] CRUD_FORM_FIELD = {"id", "orderNumber", "orderDate", "organization", "description", "employees"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Номер приказа", "Дата приказа", "Организация", "Комментарии", "Пользователи"};
    private static final List<String> GRID_COLUMNS = List.of("id", "orderNumber");
    private static final List<String> GRID_EMPLOYEE_COLUMNS = List.of("lastName", "firstName", "patronymic");
    private static final Map<String, String> MAP_EMPLOYEE_COLUMNS = Map.of("lastName", "Фамилия", "firstName", "Имя", "patronymic", "Отчество");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Номер приказа");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);

    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<Order> formFactory = new UpoCrudFormFactory<>(Order.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldProvider("organization", getOrganizationProvider());
        formFactory.setFieldProvider("employees", () -> {
            var grid = new CustomGrid<>(Employee.class, employeeCrudListener.findAll(), GRID_EMPLOYEE_COLUMNS, MAP_EMPLOYEE_COLUMNS);
            grid.setValue(employeeCrudListener.findAll());
            return grid;
        });

        var splitLayout = new UpoHorizontalSplitCrudLayout();
        splitLayout.getMainLayout().setSplitterPosition(60);
        UpoGridCrud<Order> crud = new UpoGridCrud<>(Order.class, splitLayout, formFactory, orderCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        crud.addAttachListener(attachEvent -> refreshCombobox(crud));
        crud.getGrid().addColumn(new TextRenderer<>(order -> order.getEmployees().stream().map(Employee::getShortFio).collect(Collectors.joining(", "))))
                .setHeader("Пользователи");
        TextField employeeFilter = new TextField();
        employeeFilter.setPlaceholder("поиск по пользователям");
        employeeFilter.addValueChangeListener(e -> crud.refreshGrid());
        crud.getCrudLayout().addFilterComponent(employeeFilter);
        add(crud);
    }

    /**
     * Обновляет значения справочников в выпадающих списках
     *
     * @param crud форма
     */
    @SuppressWarnings("unchecked")
    private void refreshCombobox(UpoGridCrud crud) {
        crud.getCrudFormFactory().setFieldProvider("organization", getOrganizationProvider());
        crud.getCrudFormFactory().buildCaption(CrudOperation.READ, null);
    }

    /**
     * Получить провайдера для справочника организаций
     */
    private ComboBoxProvider getOrganizationProvider() {
        return new ComboBoxProvider<>("Организация", organizationService.findAll(), new TextRenderer<>(Organization::getName), Organization::getName);
    }
}
