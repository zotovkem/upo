package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Created by ZotovES on 30.03.2019
 * Фабрика для генерации формы CRUD
 */
public class UpoCrudFormFactory<T> extends DefaultCrudFormFactory<T> {
    private FormLayout.ResponsiveStep[] responsiveSteps;
    public UpoCrudFormFactory(Class<T> domainType, FormLayout.ResponsiveStep... responsiveSteps) {
        super(domainType);
        getTranslateFormFactory(this);
        setFieldType("description", TextArea.class);
        setDisabledProperties("id");
        if (responsiveSteps != null) {
            this.responsiveSteps = responsiveSteps;
        } else {
            this.responsiveSteps = new FormLayout.ResponsiveStep[]{
                    new FormLayout.ResponsiveStep("0em", 1),
                    new FormLayout.ResponsiveStep("25em", 2)
            };
        }
    }

    public UpoCrudFormFactory(Class<T> domainType) {
        this(domainType, (FormLayout.ResponsiveStep[]) null);
        getTranslateFormFactory(this);
        setFieldType("description", TextArea.class);
        setDisabledProperties("id");
    }

    private void getTranslateFormFactory(@NotNull DefaultCrudFormFactory<T> formFactory) {
        formFactory.setButtonCaption(CrudOperation.ADD, "Добавить");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Удалить");
        formFactory.setButtonCaption(CrudOperation.READ, "Закрыть");
        formFactory.setButtonCaption(CrudOperation.UPDATE, "Сохранить");
        formFactory.setCancelButtonCaption("Отмена");
    }

    @Override
    public Component buildNewForm(CrudOperation operation, T domainObject, boolean readOnly, ComponentEventListener<ClickEvent<Button>> cancelButtonClickListener, ComponentEventListener<ClickEvent<Button>> operationButtonClickListener) {
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setResponsiveSteps(responsiveSteps);

        List<HasValueAndElement> fields = buildFields(operation, domainObject, readOnly);
        fields.stream()
                .filter(f -> !(f instanceof CustomGrid) && !(f instanceof TextArea))
                .forEach(field ->
                        formLayout.getElement().appendChild(field.getElement()));


        var gridResponsiveStep = new FormLayout.ResponsiveStep[]{
                new FormLayout.ResponsiveStep("0em", 1)
        };
        FormLayout gridLayout = new FormLayout();
        gridLayout.setSizeFull();
        gridLayout.setResponsiveSteps(gridResponsiveStep);
        fields.stream()
                .filter(f -> f instanceof CustomGrid || f instanceof TextArea)
                .forEach(field ->
                        gridLayout.getElement().appendChild(field.getElement()));

        Component footerLayout = buildFooter(operation, domainObject, cancelButtonClickListener, operationButtonClickListener);

        com.vaadin.flow.component.orderedlayout.VerticalLayout mainLayout = new VerticalLayout(formLayout, gridLayout, footerLayout);
        mainLayout.setFlexGrow(2, formLayout);
        mainLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.END, footerLayout);
        mainLayout.setMargin(false);
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);

        return mainLayout;
    }
}
