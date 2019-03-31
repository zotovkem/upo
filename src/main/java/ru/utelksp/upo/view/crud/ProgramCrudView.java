package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.form.impl.field.provider.CheckBoxGroupProvider;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.ComputerCrudListener;
import ru.utelksp.upo.view.listener.OrderCrudListener;
import ru.utelksp.upo.view.listener.ProgramCrudListener;
import ru.utelksp.upo.view.listener.TypeUsingCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;


@Route(value = "program", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Программное обеспечение")
@UIScope
@Component
@RequiredArgsConstructor
public class ProgramCrudView extends HorizontalLayout {
    private final ProgramCrudListener programCrudListener;
    private final TypeUsingCrudListener typeUsingCrudListener;
    private final OrderCrudListener orderCrudListener;
    private final ComputerCrudListener computerCrudListener;

    public static final String VIEW_NAME = "Программное обеспечение";
    private static final String[] CRUD_FORM_FIELD = {"id", "name", "typeUsing", "orders", "computers", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Наименование", "Вид использования", "Приказы", "Компьютеры", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "name");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Наименование");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);

    @PostConstruct
    void init() {
        setSizeFull();

        UpoCrudFormFactory<Program> formFactory = new UpoCrudFormFactory<>(Program.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldProvider("typeUsing",
                new ComboBoxProvider<>("Вид использования", typeUsingCrudListener.findAll(), new TextRenderer<>(TypeUsing::getName), TypeUsing::getName));
        formFactory.setFieldProvider("orders",
                new CheckBoxGroupProvider<>("Приказы", orderCrudListener.findAll(), Order::getOrderNumber));
        formFactory.setFieldProvider("computers",
                new CheckBoxGroupProvider<>("Компьютеры", computerCrudListener.findAll(), Computer::getName));

        UpoGridCrud<Program> crud = new UpoGridCrud<>(Program.class, new UpoHorizontalSplitCrudLayout(), formFactory, programCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        add(crud);
    }

}
