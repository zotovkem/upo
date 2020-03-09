package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.OrganizationCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 * Форма для редактирования справочника организаций
 */
@Route(value = "organization", layout = MainLayout.class)
@PageTitle("Классификатор органов")
@UIScope
@Component
@RequiredArgsConstructor
public class OrganizationCrudView extends VerticalLayout {
    private final OrganizationCrudListener organizationCrudListener;

    private static final String[] CRUD_FORM_FIELD = {"id", "name", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Наименование", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "name");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Наименование");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Классификатор органов";

    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<Organization> formFactory = new UpoCrudFormFactory<>(Organization.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);

        UpoGridCrud<Organization> crud = new UpoGridCrud<>(Organization.class, new UpoHorizontalSplitCrudLayout(), formFactory, organizationCrudListener);
        crud.getGrid().setColumnReorderingAllowed(true);
        crud.setSizeFull();
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);

        add(crud);
    }
}
