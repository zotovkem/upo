package ru.utelksp.upo.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Service;

/**
 * @author Created by ZotovES on 26.03.2019
 * Конструктор форм для справочников
 */
@SuppressWarnings("Duplicates")
@Service
public class BuilderDictionaryForm {
    public void addGrid(Grid grid, HorizontalLayout layout) {
        HorizontalLayout topLayout = createTopBar();

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        layout.add(barAndGridLayout);
    }

    public void addForm(HorizontalLayout layout, Div form) {
        layout.add(form);
    }

    private HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setPlaceholder("Поиск по имени");
//        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        Button btnAdd = new Button("Добавить ПО");
        btnAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnAdd.setIcon(VaadinIcon.PLUS_CIRCLE.create());
//        newProduct.addClickListener(click -> viewLogic.newProduct());
        btnAdd.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(btnAdd);
        topLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }
}
