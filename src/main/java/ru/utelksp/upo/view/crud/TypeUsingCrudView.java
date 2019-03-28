package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.service.TypeUsingService;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.form.DictionaryEditForm;
import ru.utelksp.upo.view.form.GridForm;
import ru.utelksp.upo.view.form.grid.TypeUsingGrid;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Форма для справочника видов использования
 */
@Route(value = "typeUsing", layout = MainLayout.class)
@UIScope
@Component
@RequiredArgsConstructor
public class TypeUsingCrudView extends HorizontalLayout {
    private final TypeUsingService typeUsingService;

    public static final String VIEW_NAME = "Виды использования";
    private TypeUsingGrid grid;
    private DictionaryEditForm editForm;
    private GridForm gridForm;
    private Collection<TypeUsing> rows;
    private boolean isNewRow = false;

    /**
     * Собирает интерфейс формы редактирования справочника Видов использования
     */
    @PostConstruct
    private void init() {
        setSizeFull();
        grid = new TypeUsingGrid();
        refreshAll();
        editForm = DictionaryEditForm.builder()
                .saveListener(getListenerSave())
                .deleteListener(getListenerDelete())
                .cancelListener(getListenerCancel())
                .discardListener(getDiscardListener())
                .build();
        editForm.show();
        grid.addItemDoubleClickListener(getFillFormListener());
        grid.addItemClickListener(getViewFillFormListener());

        gridForm = GridForm.builder()
                .grid(grid)
                .filterListener(getFilterListener())
                .addListener(getAddListener())
                .build();
        gridForm.show();

        add(gridForm);
        add(editForm);
    }

    /**
     * Поведение кнопки сохранить
     */
    private ComponentEventListener<ClickEvent<Button>> getListenerSave() {
        return event -> {
            if (isNewRow) {
                var typeUsing = TypeUsing.builder()
                        .name(editForm.getNameTextField().getValue())
                        .description(editForm.getDescriptionTextField().getValue())
                        .build();
                updateOrCreate(typeUsing);
            } else {
                grid.getSelectedRow()
                        .filter(row -> row.getId().equals(Long.valueOf(editForm.getIdTextField().getValue())))
                        .ifPresent(this::update);
            }
            isNewRow = false;
        };
    }

    /**
     * Обновляет запись.
     *
     * @param typeUsing необновленая запись
     */
    private void update(TypeUsing typeUsing) {
        typeUsing.setName(editForm.getNameTextField().getValue());
        typeUsing.setDescription(editForm.getDescriptionTextField().getValue());
        updateOrCreate(typeUsing);
    }

    /**
     * Установить активной первую запись
     *
     * @param usingTypes коллекция типов
     */
    private void selectFirstRow(Collection<TypeUsing> usingTypes) {
        usingTypes.stream().findFirst()
                .ifPresent(typeUsing -> grid.select(typeUsing));
    }

    private void updateOrCreate(TypeUsing typeUsing) {
        TypeUsing savedTypeUsing = typeUsingService.save(typeUsing);
        if (isNewRow) {
            refreshAll();
        } else {
            refreshOneRow(savedTypeUsing);
        }
        grid.select(savedTypeUsing);
        fillEditForm(savedTypeUsing);
    }

    /**
     * Обновить весь грид.
     */
    private void refreshAll() {
        rows = typeUsingService.findAll();
        refreshGrid();
    }

    /**
     * Обновляет одну запись в гриде
     *
     * @param typeUsing запись которую нужно обновить
     */
    private void refreshOneRow(TypeUsing typeUsing) {
        rows = rows.stream()
                .map(t -> t.getId().equals(typeUsing.getId()) ? typeUsing : t)
                .collect(Collectors.toList());
        refreshGrid();
    }

    /**
     * Обновляет grid
     */
    private void refreshGrid() {
        grid.setItems(rows);
        selectFirstRow(rows);
    }

    /**
     * Поведение кнопки Удалить
     */
    private ComponentEventListener<ClickEvent<Button>> getListenerDelete() {
        return event -> grid.getSelectedRow().ifPresent(typeUsing -> {
            typeUsingService.deleteById(typeUsing.getId());
            rows.remove(typeUsing);
            refreshAll();
        });
    }

    /**
     * Поведение кнопки отменить
     */
    private ComponentEventListener<ClickEvent<Button>> getListenerCancel() {
        return event -> editForm.setVisible(false);
    }

    /**
     * Поведение кнопки Добавить
     */
    private ComponentEventListener<ClickEvent<Button>> getAddListener() {
        return event -> {
            isNewRow = true;
            clearEditForm();
        };
    }

    /**
     * Очищает форму редактирования
     */
    private void clearEditForm() {
        grid.deselectAll();
        editForm.newRecord();
    }

    /**
     * Поведение поля фильтра
     */
    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>> getFilterListener() {
        return event -> {
            var textFilter = event.getValue().toLowerCase().trim();
            editForm.setVisible(false);
            if (!textFilter.isEmpty()) {
                grid.deselectAll();
                grid.setItems(rows.stream()
                        .filter(row -> row.getName().toLowerCase().contains(textFilter)));
            } else {
                refreshGrid();
            }

        };
    }

    /**
     * Поведение при двойном клике на гриде
     */
    private ComponentEventListener<ItemDoubleClickEvent<TypeUsing>> getFillFormListener() {
        return event -> grid.getSelectedRow().ifPresent(typeUsing -> {
            isNewRow = false;

            fillEditForm(typeUsing);
        });
    }

    /**
     * Поведение при двойном клике на гриде
     */
    private ComponentEventListener<ItemClickEvent<TypeUsing>> getViewFillFormListener() {
        return event -> grid.getSelectedRow()
                .filter(row -> !isNewRow && editForm.isVisible())
                .ifPresent(this::fillEditForm);
    }

    /**
     * Сбросить введеные изменения
     */
    private ComponentEventListener<ClickEvent<Button>> getDiscardListener() {
        return event -> grid.getSelectedRow().ifPresent(typeUsing -> {
            if (isNewRow) {
                clearEditForm();
            } else {
                fillEditForm(typeUsing);
            }
        });
    }

    /**
     * Заполняет форму редактирования справочника
     *
     * @param typeUsing объект справочника
     */
    private void fillEditForm(TypeUsing typeUsing) {
        editForm.setValIdTextField(typeUsing.getId().toString());
        editForm.setValNameTextField(typeUsing.getName());
        editForm.setValDescriptionTextField(typeUsing.getDescription());
        editForm.setVisible(true);
    }
}
