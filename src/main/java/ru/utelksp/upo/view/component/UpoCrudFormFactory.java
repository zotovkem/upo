package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.textfield.TextArea;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;

import javax.validation.constraints.NotNull;

/**
 * @author Created by ZotovES on 30.03.2019
 * Фабрика для генерации формы CRUD
 */
public class UpoCrudFormFactory<T> extends DefaultCrudFormFactory<T> {
    public UpoCrudFormFactory(Class<T> domainType) {
        super(domainType);
        getTranslateFormFactory(this);
        setFieldType("description", TextArea.class);
        setDisabledProperties("id");
    }

    private void getTranslateFormFactory(@NotNull DefaultCrudFormFactory<T> formFactory) {
        formFactory.setButtonCaption(CrudOperation.ADD, "Добавить новую запись");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Удалить");
        formFactory.setButtonCaption(CrudOperation.READ, "Закрыть");
        formFactory.setButtonCaption(CrudOperation.UPDATE, "Сохранить");
        formFactory.setCancelButtonCaption("Отмена");
    }
}
