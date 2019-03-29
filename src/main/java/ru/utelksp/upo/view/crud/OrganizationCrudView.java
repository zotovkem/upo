package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import ru.utelksp.upo.common.Util;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.OrganizationCrudListener;

import javax.annotation.PostConstruct;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;
import static ru.utelksp.upo.common.Util.getTranslateGrid;

/**
 *Форма для редактирования справочника организаций
 */
@Route(value = "organization", layout = MainLayout.class)
@UIScope
@Component
@RequiredArgsConstructor
public class OrganizationCrudView extends VerticalLayout {
    private final OrganizationCrudListener organizationCrudListener;

    private static final String[] CRUD_FORM_FIELD = {"id", "name","description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Наименование","Комментарии"};
    private static final String[] GRID_COLUMNS = {"id", "name"};
    private static final String[] GRID_COLUMNS_CAPTION = {"Код", "Наименование"};
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Организации";

    @PostConstruct
    public void init() {
        setSizeFull();

        DefaultCrudFormFactory<Organization> formFactory = new DefaultCrudFormFactory<>(Organization.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldType("description", TextArea.class);
        formFactory.setDisabledProperties("id");
        formFactory.setButtonCaption(CrudOperation.ADD, "Добавить новую запись");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Удалить");
        formFactory.setButtonCaption(CrudOperation.READ, "Закрыть");
        formFactory.setButtonCaption(CrudOperation.UPDATE, "Сохранить");
        formFactory.setCancelButtonCaption("Отмена");
        formFactory.setErrorListener(e -> Util.showError("Ошибка удаления"));

        UpoHorizontalSplitCrudLayout crudLayout = new UpoHorizontalSplitCrudLayout();
        crudLayout.setFormCaption(CrudOperation.DELETE, "Запись будет удалена, вы уверены?");

        GridCrud<Organization> crud = getTranslateGrid(new GridCrud<>(Organization.class, crudLayout, formFactory));
        crud.setCrudListener(organizationCrudListener);
        crud.getGrid().setColumnReorderingAllowed(true);
        crud.setSizeFull();
        crud.getGrid().setColumns(GRID_COLUMNS);
        crud.getGrid().getColumns().forEach(column -> column.setHeader(MAP_COLUMN_PROP.get(column.getKey())));

        add(crud);
    }
}
