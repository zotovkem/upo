package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.JournalEvent;
import ru.utelksp.upo.domain.dictionary.TypeEvent;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.JournalCrudListner;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 * Форма для справочника видов использования
 */
@Route(value = "journalEvent", layout = MainLayout.class)
@PageTitle("Журнал событий")
@UIScope
@Component
@RequiredArgsConstructor
public class JournalEventCrudView extends VerticalLayout {
    private final JournalCrudListner journalCrudListner;

    public static final String VIEW_NAME = "Журнал событий";
    private static final String[] CRUD_FORM_FIELD = {"id", "dateEvent", "timeEvent", "username", "typeEvent", "name", "description"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Дата события", "Время события", "Пользователь", "Тип события", "Наименование", "Комментарии"};
    private static final List<String> GRID_COLUMNS = List.of("id", "dateEvent", "timeEvent", "username", "name");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Дата события", "Время события", "Пользователь", "Наименование");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);

    @PostConstruct
    private void init() {
        setSizeFull();

        UpoCrudFormFactory<JournalEvent> formFactory = new UpoCrudFormFactory<>(JournalEvent.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        var typeEvents = journalCrudListner.findAll().stream()
                .map(JournalEvent::getTypeEvent)
                .collect(Collectors.toSet());
        formFactory.setFieldProvider("typeEvent",
                new ComboBoxProvider<>("Тип события", typeEvents, new TextRenderer<>(TypeEvent::getName), TypeEvent::getName));

        var crudLayout = new UpoHorizontalSplitCrudLayout();
        UpoGridCrud<JournalEvent> crud = new UpoGridCrud<>(JournalEvent.class, crudLayout, formFactory, journalCrudListner);
        crudLayout.getToolbar().setVisible(false);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);

        add(crud);
    }
}
