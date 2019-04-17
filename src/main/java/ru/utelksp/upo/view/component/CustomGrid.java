package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Created by ZotovES on 05.04.2019
 * Грид для отображения справочника на форме.
 */
@Tag("vaadin-custom-grid")
@HtmlImport("css/shared-styles.html")
public class CustomGrid<T> extends CustomField<Collection<T>> {
    private Grid<T> grid;

    public CustomGrid(Class<T> domainType, Collection<T> defaultValue, List<String> columns, Map<String, String> mapColumn) {
        grid = new Grid<>(domainType);
        grid.setItems(defaultValue);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setColumns(columns.toArray(String[]::new));
        grid.getColumns().forEach(column -> {
            column.setWidth("100%");
            column.setHeader(mapColumn.get(column.getKey()));
        });
        grid.setColumnReorderingAllowed(true);
        grid.setWidth("100%");
        grid.getColumns().get(0).setFlexGrow(1);
        setWidth("100%");
        VerticalLayout layout = new VerticalLayout();
        layout.add(grid);
        layout.setWidth("100%");
        layout.add(grid);
        add(layout);
    }

    /**
     * Возвращает выбранные значения
     *
     * @return коллекция выбранных элементов.
     */
    @Override
    protected Collection<T> generateModelValue() {
        return grid.getSelectedItems();
    }

    /**
     * Заполняет значения
     *
     * @param newPresentationValue новые значения
     */
    @Override
    protected void setPresentationValue(Collection<T> newPresentationValue) {
        grid.deselectAll();
        if (newPresentationValue != null) {
            newPresentationValue.forEach(grid::select);
        }

    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        grid.setEnabled(!readOnly);
    }

}


