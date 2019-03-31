package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.dictionary.Organization;
import ru.utelksp.upo.service.OrganizationService;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.OrderCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

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

    public static final String VIEW_NAME = "Приказы";
    private static final String[] CRUD_FORM_FIELD = {"id", "orderNumber", "orderDate", "organization", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Номер приказа", "Дата приказа", "Организация", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "orderNumber");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Номер приказа");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);

    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<Order> formFactory = new UpoCrudFormFactory<>(Order.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldProvider("organization",
                new ComboBoxProvider<>("Пользователь", organizationService.findAll(), new TextRenderer<>(Organization::getName), Organization::getName));

        UpoGridCrud<Order> crud = new UpoGridCrud<>(Order.class, new UpoHorizontalSplitCrudLayout(), formFactory, orderCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        add(crud);
    }

}
