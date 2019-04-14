package ru.utelksp.upo.view;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import ru.utelksp.upo.common.exception.ValidationException;

/**
 * Представление для отображение ошибки валидации.
 */
@ParentLayout(MainLayout.class)
public class ValidationErrorView extends VerticalLayout implements HasErrorParameter<RuntimeException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<RuntimeException> errorParameter) {
        if (errorParameter.getException() instanceof ValidationException) {
            Notification.show(errorParameter.getException().getMessage());
        }
        return 0;
    }
}
