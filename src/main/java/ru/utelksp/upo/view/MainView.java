package ru.utelksp.upo.view;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;

/**
 * @author Created by ZotovES on 17.03.2019
 * Главная форма приложения
 */
@Route
public class MainView extends FlexLayout {
    private Label label;

    public MainView() {
        this.label = new Label();
        label.setText("HelloWorld");
        add(label);
    }
}
