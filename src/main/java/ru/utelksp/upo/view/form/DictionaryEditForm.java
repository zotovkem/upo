package ru.utelksp.upo.view.form;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Форма для редактирования справочников.
 */
@Builder
@Getter
@Setter
public class DictionaryEditForm extends Div {

    private VerticalLayout content;

    private TextField idTextField;
    private TextField nameTextField;
    private TextArea descriptionTextField;
    private Button save;
    private Button discard;
    private Button cancel;
    private Button delete;
    private boolean isNewRow = false;

    private ComponentEventListener<ClickEvent<Button>> saveListener;
    private ComponentEventListener<ClickEvent<Button>> discardListener;
    private ComponentEventListener<ClickEvent<Button>> cancelListener;
    private ComponentEventListener<ClickEvent<Button>> deleteListener;

    /**
     * Отрисовать форму редактирования справочников
     */
    public void show() {
        setClassName("product-form");

        this.setVisible(false);
        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        idTextField = new TextField("Код");
        idTextField.setWidth("100%");
        idTextField.setEnabled(false);
        idTextField.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(idTextField);

        nameTextField = new TextField("Наименование");
        nameTextField.setWidth("100%");
        nameTextField.setRequired(true);
        nameTextField.setValueChangeMode(ValueChangeMode.EAGER);
        nameTextField.addValueChangeListener(event -> setEditMode());
        content.add(nameTextField);

        descriptionTextField = new TextArea("Комментарии");
        descriptionTextField.setValueChangeMode(ValueChangeMode.EAGER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(descriptionTextField);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setFlexGrow(1, descriptionTextField);
        content.add(horizontalLayout);

        save = new Button("Сохранить");
        save.setVisible(false);
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (!Objects.isNull(saveListener)) {
                saveListener.onComponentEvent(event);
                isNewRow = false;
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Сбросить изменения");
        discard.setVisible(false);
        discard.setWidth("100%");
        discard.addClickListener(event -> {
            if (!Objects.isNull(discardListener)) {
                if (isNewRow) {
                    newRecord();
                } else {
                    discardListener.onComponentEvent(event);
                }
            }
        });

        cancel = new Button("Отменить");
        cancel.setWidth("100%");
        cancel.addClickListener(event -> {
            if (!Objects.isNull(cancelListener)) {
                isNewRow = false;
                cancelListener.onComponentEvent(event);
            }
            this.setVisible(false);
        });
        cancel.addClickShortcut(Key.ESCAPE);

        delete = new Button("Удалить");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {
            if (!Objects.isNull(deleteListener)) {
                deleteListener.onComponentEvent(event);
            }
        });

        content.add(save, discard, delete, cancel);
    }

    /**
     * Показывает кнопки в режиме редактирования
     */
    private void setEditMode() {
        discard.setVisible(true);
        save.setVisible(true);
    }

    /**
     * Установить текст поля идентификатор.
     *
     * @param value текст поля
     */
    public void setValIdTextField(String value) {
        idTextField.setValue(getNormalValue(value));
    }

    /**
     * Установить текст поля наименования.
     *
     * @param value текст поля
     */
    public void setValNameTextField(String value) {
        nameTextField.setValue(getNormalValue(value));
    }

    /**
     * Установить текст поля комментарии.
     *
     * @param value текст поля
     */
    public void setValDescriptionTextField(String value) {
        descriptionTextField.setValue(getNormalValue(value));
    }

    /**
     * Проверяет входящее значение на null, если null то пустое поле.
     *
     * @param value входящие значение
     * @return текстовое значение
     */
    private String getNormalValue(String value) {
        return Objects.isNull(value) ? "" : value;
    }

    /**
     * Открывает пустую форму для добавления новой записи
     */
    public void newRecord() {
        isNewRow = true;
        delete.setVisible(false);

        setValIdTextField(null);
        setValNameTextField(null);
        setValDescriptionTextField(null);
        setVisible(true);
    }
}
