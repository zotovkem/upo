package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import java.util.Collection;

/**
 * @author Created by ZotovES on 05.04.2019
 * Провайдер для листбокса
 */
public class ListBoxProvider<T> extends AbstractListingProvider<Select<T>, T> {

    private ItemLabelGenerator<T> itemLabelGenerator;

    public ListBoxProvider(Collection<T> items) {
        super(items);
    }

    public ListBoxProvider(String caption, Collection<T> items) {
        super(caption, items);
    }

    public ListBoxProvider(String caption, Collection<T> items, ComponentRenderer<? extends Component, T> renderer, ItemLabelGenerator<T> itemLabelGenerator) {
        super(caption, items, renderer);
        this.itemLabelGenerator = itemLabelGenerator;
    }

    @Override
    protected Select<T> buildAbstractListing() {
        Select<T> field = new Select<>();
        if (renderer != null) {
            field.setRenderer(renderer);
        }
        if (itemLabelGenerator != null) {
            field.setItemLabelGenerator(itemLabelGenerator);
        }
        field.setItems(items);
        return field;
    }
}
