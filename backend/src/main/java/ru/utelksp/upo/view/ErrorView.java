package ru.utelksp.upo.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;

/**
 * Представление для отображение ошибки.
 */
@ParentLayout(MainLayout.class)
public class ErrorView extends VerticalLayout implements HasErrorParameter<Exception> {

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<Exception> errorParameter) {
        return 0;
    }

}
