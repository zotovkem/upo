package ru.utelksp.upo.view.form;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Created by ZotovES on 26.03.2019
 * Форма в центре экрана с гридом.
 */
@Builder
@Getter
@Setter
@SuppressWarnings("Duplicates")
public class GridForm extends VerticalLayout {
    private TextField filter;
    private Button btnAdd;
    private Grid grid;

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>> filterListener;
    private ComponentEventListener<ClickEvent<Button>> addListener;

    /**
     * Отрисовывает форму
     */
    public void show() {
        HorizontalLayout topLayout = createTopBar();

        add(topLayout);
        add(grid);
        setFlexGrow(1, grid);
        setFlexGrow(0, topLayout);
        setSizeFull();
        expand(grid);
    }

    /**
     * Создает верхнюю панель с полем поиска и кнопкой добавить.
     *
     * @return объект панели.
     */
    private HorizontalLayout createTopBar() {
        createFieldFilter();
        createAddButton();

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(btnAdd);
        topLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    /**
     * Добавляет на форму кнопку Добавить
     */
    private void createAddButton() {
        btnAdd = new Button("Добавить");
        btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAdd.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        btnAdd.addClickListener(event -> {
            if (!Objects.isNull(addListener)) {
                addListener.onComponentEvent(event);
            }
        });
        btnAdd.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
    }

    /**
     * Добавляет на форму поле поиска наименованию
     */
    private void createFieldFilter() {
        filter = new TextField();
        filter.setPlaceholder("Поиск по наименованию");
        filter.addValueChangeListener(event -> {
            if (!Objects.isNull(filterListener)) {
                filterListener.valueChanged(event);
            }
        });
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
    }
}
