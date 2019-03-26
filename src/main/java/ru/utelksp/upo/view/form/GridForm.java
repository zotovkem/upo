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
 * Конструктор форм для справочников
 */
@SuppressWarnings("Duplicates")
@Builder
@Getter
@Setter
public class GridForm extends VerticalLayout {
    private TextField filter;
    private Button btnAdd;
    private Grid grid;

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>> filterListener;
    private ComponentEventListener<ClickEvent<Button>> addListener;

    public void show() {
        HorizontalLayout topLayout = createTopBar();

        add(topLayout);
        add(grid);
        setFlexGrow(1, grid);
        setFlexGrow(0, topLayout);
        setSizeFull();
        expand(grid);
    }

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

    private void createAddButton() {
        btnAdd = new Button("Добавить ПО");
        btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAdd.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        btnAdd.addClickListener(event -> {
            if (!Objects.isNull(addListener)) {
                addListener.onComponentEvent(event);
            }
        });
        btnAdd.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
    }

    private void createFieldFilter() {
        filter = new TextField();
        filter.setPlaceholder("Поиск по имени");
        filter.addValueChangeListener(event -> {
            if (!Objects.isNull(filterListener)) {
                filterListener.valueChanged(event);
            }
        });
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
    }
}
