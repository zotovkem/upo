package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.VerticalCrudLayout;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.listener.OrganizationCrudListener;

import javax.annotation.PostConstruct;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 *
 */
@Route(value = "organization", layout = MainLayout.class)
@UIScope
@Component
@RequiredArgsConstructor
public class OrganizationCrudView extends VerticalLayout {
    private final OrganizationCrudListener organizationCrudListener;

    private static final String[] GRID_COLUMNS = {"id", "name", "description"};
    private static final String[] GRID_COLUMNS_CAPTION = {"Код", "Наименование", "Комментарии"};
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Организации";

    @PostConstruct
    public void init() {
        TextField nameFilter = new TextField();
        setSizeFull();

        CrudFormFactory<Organization> formFactory = new DefaultCrudFormFactory<>(Organization.class);
        formFactory.setVisibleProperties(GRID_COLUMNS);
        formFactory.setFieldCaptions(GRID_COLUMNS_CAPTION);
        formFactory.setFieldType("description", TextArea.class);

        GridCrud<Organization> crud = new GridCrud<>(Organization.class, new VerticalCrudLayout(), formFactory);
        crud.setDeletedMessage("Запись удалена");
        crud.setCrudListener(organizationCrudListener);
        crud.setSizeFull();
        crud.getGrid().setColumns(GRID_COLUMNS);
        crud.getGrid().getColumns().forEach(column -> column.setHeader(MAP_COLUMN_PROP.get(column.getKey())));
        crud.getDeleteButton().getElement().setAttribute("title", "Удалить");
        crud.getAddButton().getElement().setAttribute("title", "Добавить");
        crud.getUpdateButton().getElement().setAttribute("title", "Редактировать");
        crud.getFindAllButton().getElement().setAttribute("title", "Обновить");

        nameFilter.setPlaceholder("поиск по имени ...");
        nameFilter.addValueChangeListener(e -> crud.refreshGrid());
        crud.getCrudLayout().addFilterComponent(nameFilter);
        crud.setFindAllOperation(
                DataProvider.fromCallbacks(
                        query -> organizationCrudListener.findByFilter(nameFilter.getValue(), query.getOffset(), query.getLimit()).stream(),
                        query -> organizationCrudListener.findByFilter(nameFilter.getValue(), 0, 100).size())
        );

        add(crud);
    }
}
