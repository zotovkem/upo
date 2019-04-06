package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import lombok.Getter;
import lombok.Setter;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

/**
 * @author Created by ZotovES on 29.03.2019
 * Компонент расположения окна редактирования
 */
public class UpoHorizontalSplitCrudLayout extends HorizontalSplitCrudLayout {
    private static final int SPLIT_POSITION = 80;

    @Getter
    @Setter
    private SplitLayout mainLayout;

    public UpoHorizontalSplitCrudLayout() {
        super();
        setFormCaption(CrudOperation.DELETE, "Запись будет удалена, вы уверены?");
        filterLayout.setPadding(false);
        filterLayout.setMargin(false);
        firstComponentHeaderLayout.setMargin(true);
        secondComponent.setMargin(false);
    }

    @Override
    protected SplitLayout buildMainLayout() {
        mainLayout = new SplitLayout(firstComponent, secondComponent);
        mainLayout.setSizeFull();
        mainLayout.setSplitterPosition(SPLIT_POSITION);
        return mainLayout;
    }

    @Override
    protected void addToolbarLayout(Component toolbarLayout) {
        firstComponentHeaderLayout.add(toolbarLayout);
    }

    @Override
    public void addToolbarComponent(Component component) {
        if (!firstComponentHeaderLayout.isVisible()) {
            firstComponentHeaderLayout.setVisible(true);
            firstComponent.getElement()
                    .insertChild(firstComponent.getComponentCount() - 1, firstComponentHeaderLayout.getElement());
        }

        toolbarLayout.setVisible(true);
        toolbarLayout.add(component);
    }
}
