package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;

/**
 * @author Created by ZotovES on 28.04.2019
 * Компонент диалог
 */
public class UpoDialog extends Dialog {
    Label label;

    public UpoDialog() {
        super();
        label = new Label();
        add(label);

        setWidth("400px");
        setHeight("150px");
    }

    /**
     * Устанавливаем текст уведомления
     *
     * @param string текст
     */
    public void setCaption(String string) {
        label.setText(string);
    }

    /**
     * Открывает диалог c текстом
     *
     * @param string текст.
     */
    public void open(String string) {
        super.open();
        setCaption(string);
    }
}
