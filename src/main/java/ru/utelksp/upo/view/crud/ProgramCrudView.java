package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.Order;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.CustomGrid;
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

import static java.util.Objects.nonNull;
import static ru.utelksp.upo.common.Util.getCollectMap;


@Route(value = "program", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Реестр ПО")
@UIScope
@Component
@RequiredArgsConstructor
public class ProgramCrudView extends HorizontalLayout implements HasUrlParameter<Long> {
    private final ProgramCrudListener programCrudListener;
    private final TypeUsingCrudListener typeUsingCrudListener;
    private final OrderCrudListener orderCrudListener;
    private final ComputerCrudListener computerCrudListener;

    public static final String VIEW_NAME = "Реестр ПО";
    private static final String[] CRUD_FORM_FIELD = {"orders", "computers", "id", "name", "typeUsing", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Приказы", "Компьютеры", "Код", "Наименование", "Вид использования", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "name");
    private static final List<String> GRID_ORDER_COLUMNS = List.of("orderNumber", "orderDate");
    private static final List<String> GRID_COMPUTER_COLUMNS = List.of("name");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Наименование");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    private static final Map<String, String> MAP_COLUMN_COMPUTER = Map.of("name", "Компьютеры");
    private static final Map<String, String> MAP_COLUMN_ORDER = Map.of("orderNumber", "Приказ", "orderDate", "Дата");

    private UpoGridCrud<Program> crud;

    @PostConstruct
    void init() {
        setSizeFull();

        UpoCrudFormFactory<Program> formFactory = new UpoCrudFormFactory<>(Program.class);
        formFactory.setFieldProvider("orders", () -> {
            var grid = new CustomGrid<>(Order.class, orderCrudListener.findAll(), GRID_ORDER_COLUMNS, MAP_COLUMN_ORDER);
            grid.setValue(orderCrudListener.findAll());
            return grid;
        });
        formFactory.setFieldProvider("computers", () -> {
            var grid = new CustomGrid<>(Computer.class, computerCrudListener.findAll(), GRID_COMPUTER_COLUMNS, MAP_COLUMN_COMPUTER);
            grid.setValue(computerCrudListener.findAll());
            return grid;
        });
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldProvider("typeUsing", getTypeUsingProvider());

        var splitLayout = new UpoHorizontalSplitCrudLayout();
        splitLayout.getMainLayout().setSplitterPosition(60);
        crud = new UpoGridCrud<>(Program.class, splitLayout, formFactory, programCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        crud.addAttachListener(attachEvent -> refreshCombobox(crud));
        add(crud);
    }

    /**
     * Обновляет значения справочников в выпадающих списках
     *
     * @param crud форма
     */
    @SuppressWarnings("unchecked")
    private void refreshCombobox(UpoGridCrud crud) {
        crud.getCrudFormFactory().setFieldProvider("typeUsing", getTypeUsingProvider());
        crud.getCrudFormFactory().buildCaption(CrudOperation.READ, null);
    }

    /**
     * Получить провайдера для справочника видов использования
     */
    private ComboBoxProvider getTypeUsingProvider() {
        return new ComboBoxProvider<>("Вид использования", typeUsingCrudListener.findAll(), new TextRenderer<>(TypeUsing::getName), TypeUsing::getName);
    }

    /**
     * Ловит входящий параметр и ищет по нему ПО
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
        if (nonNull(parameter)) {
            programCrudListener.findById(parameter).ifPresent(this::showCrudForm);
        }
    }

    /**
     * Отображает форму детального просмотра
     *
     * @param p объект для отображения
     */
    private void showCrudForm(Program p) {
        var form = crud.getCrudFormFactory().buildNewForm(CrudOperation.READ, p, true, null, e -> {
            crud.getGrid().asSingleSelect().clear();
            crud.getGrid().select(p);
        });
        String caption = crud.getCrudFormFactory().buildCaption(CrudOperation.READ, p);
        crud.getCrudLayout().showForm(CrudOperation.READ, form, caption);
    }
}
