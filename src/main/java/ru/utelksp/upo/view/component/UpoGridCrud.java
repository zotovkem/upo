package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.CrudOperationException;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.layout.CrudLayout;
import ru.utelksp.upo.common.exception.ValidationException;

import java.util.Collection;
import java.util.Map;

/**
 * @author Created by ZotovES on 30.03.2019
 * CRUD grid переведенный и настроенный под приложение
 */
public class UpoGridCrud<T> extends GridCrud<T> {
    private boolean clickRowToUpdate;
    private UpoDialog dialog = new UpoDialog();

    public UpoGridCrud(Class<T> domainType, CrudLayout crudLayout, CrudFormFactory<T> crudFormFactory, CrudListener<T> crudListener) {
        super(domainType, crudLayout, crudFormFactory, crudListener);
        getGrid().setColumnReorderingAllowed(true);
        setSizeFull();
        getTranslateGrid(this);
    }

    /**
     * Устанавливает значеия столбцов грида
     *
     * @param gridColumn значения столбцов
     */
    public void setGridColumn(Collection<String> gridColumn) {
        getGrid().setColumns(gridColumn.toArray(String[]::new));
    }

    /**
     * Устанавливает значение заголовков в гриде по соответсви в мапе
     *
     * @param captionColumn мапа соответсвия столбцов в таблице и гриде
     */
    public void setGridCaptionColumn(Map<String, String> captionColumn) {
        getGrid().getColumns().forEach(column -> column.setHeader(captionColumn.get(column.getKey())));
    }

    /**
     * Переводит кнопки и сообщения в гриде
     */
    private GridCrud<T> getTranslateGrid(GridCrud<T> crud) {
        crud.setDeletedMessage("Запись удалена");
        crud.setSavedMessage("Запись сохранена");
        crud.setRowCountCaption("%d записей найдено");
        crud.getDeleteButton().getElement().setAttribute("title", "Удалить");
        crud.getAddButton().getElement().setAttribute("title", "Добавить");
        crud.getUpdateButton().getElement().setAttribute("title", "Редактировать");
        crud.getFindAllButton().getElement().setAttribute("title", "Обновить");
        return crud;
    }

    public void setClickRowToUpdate(boolean clickRowToUpdate) {
        this.clickRowToUpdate = clickRowToUpdate;
    }

    /**
     * Метод создания формы редактирования
     */
    protected void showForm(CrudOperation operation, T domainObject, boolean readOnly, String successMessage, ComponentEventListener<ClickEvent<Button>> buttonClickListener) {
        Component form = crudFormFactory.buildNewForm(operation, domainObject, readOnly,
                cancelClickEvent -> {
                    if (clickRowToUpdate) {
                        grid.asSingleSelect().clear();
                    } else {
                        T selected = grid.asSingleSelect().getValue();
                        crudLayout.hideForm();
                        grid.asSingleSelect().clear();
                        grid.asSingleSelect().setValue(selected);
                    }
                }, operationPerformedClickEvent -> {
                    try {
                        buttonClickListener.onComponentEvent(operationPerformedClickEvent);
                    } catch (ValidationException ev) {
                        dialog.open(ev.getMessage());
                        throw ev;
                    }
                    crudLayout.hideForm();
                    showNotification(successMessage);
                });
        String caption = crudFormFactory.buildCaption(operation, domainObject);
        crudLayout.showForm(operation, form, caption);
    }

    /**
     * Метод редактирования сущности
     */
    protected void updateButtonClicked() {
        T domainObject = grid.asSingleSelect().getValue();
        showForm(CrudOperation.UPDATE, domainObject, false, savedMessage, event -> {
            try {
                T updatedObject = updateOperation.perform(domainObject);
                grid.asSingleSelect().clear();
                refreshGrid();
                grid.asSingleSelect().setValue(updatedObject);
            } catch (IllegalArgumentException ignore) {
            } catch (CrudOperationException e1) {
                refreshGrid();
            } catch (ValidationException ev) {
                throw ev;
            } catch (Exception e2) {
                refreshGrid();
                throw e2;
            }
        });
    }

    /**
     * Метод добавления сущности
     */
    @Override
    protected void addButtonClicked() {
        try {
            T domainObject = domainType.newInstance();
            showForm(CrudOperation.ADD, domainObject, false, savedMessage, event -> {
                try {
                    T addedObject = addOperation.perform(domainObject);
                    refreshGrid();
                    grid.asSingleSelect().setValue(addedObject);
                } catch (IllegalArgumentException ignore) {
                } catch (CrudOperationException e1) {
                    refreshGrid();
                } catch (ValidationException ev) {
                    throw ev;
                } catch (Exception e2) {
                    refreshGrid();
                    throw e2;
                }
            });
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод удаления сущности
     */
    @Override
    protected void deleteButtonClicked() {
        T domainObject = grid.asSingleSelect().getValue();
        showForm(CrudOperation.DELETE, domainObject, true, deletedMessage, event -> {
            try {
                deleteOperation.perform(domainObject);
                refreshGrid();
                grid.asSingleSelect().clear();
            } catch (CrudOperationException e1) {
                refreshGrid();
            } catch (ValidationException ev) {
                throw ev;
            } catch (Exception e2) {
                refreshGrid();
                throw e2;
            }
        });
    }
}
