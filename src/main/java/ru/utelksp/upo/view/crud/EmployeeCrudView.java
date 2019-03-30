package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.EmployeeCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 * @author Created by ZotovES on 30.03.2019
 * Форма для редактирования справочника пользователей
 */
@Route(value = "employee", layout = MainLayout.class)
@UIScope
@Component
@RequiredArgsConstructor
public class EmployeeCrudView extends VerticalLayout {
private final EmployeeCrudListener employeeCrudListener;

    private static final String[] CRUD_FORM_FIELD = {"id", "lastName","firstName", "patronymic","department", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Фамилия", "Имя","Отчество","Отдел", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "lastName","firstName", "patronymic","department");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Фамилия", "Имя","Отчество","Отдел");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Пользователи";

    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<Employee> formFactory = new UpoCrudFormFactory<>(Employee.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);

        UpoGridCrud<Employee> crud = new UpoGridCrud<>(Employee.class, new UpoHorizontalSplitCrudLayout(), formFactory, employeeCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        add(crud);
    }
}
