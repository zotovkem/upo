package ru.utelksp.upo.view.component;

import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.layout.CrudLayout;

import java.util.Collection;
import java.util.Map;

/**
 * @author Created by ZotovES on 30.03.2019
 * CRUD grid переведенный и настроенный под приложение
 */
public class UpoGridCrud<T> extends GridCrud<T> {
    public UpoGridCrud(Class<T> domainType, CrudLayout crudLayout, CrudFormFactory<T> crudFormFactory, CrudListener<T> crudListener) {
        super(domainType, crudLayout, crudFormFactory, crudListener);
        getGrid().setColumnReorderingAllowed(true);
        setSizeFull();
        getTranslateGrid(this);
    }

    /**
     * Устанавливает значеия столбцов грида
     * @param gridColumn значения столбцов
     */
    public void setGridColumn(Collection<String> gridColumn){
        getGrid().setColumns(gridColumn.toArray(String[]::new));
    }

    /**
     * Устанавливает значение заголовков в гриде по соответсви в мапе
     * @param captionColumn мапа соответсвия столбцов в таблице и гриде
     */
    public void setGridCaptionColumn(Map<String,String> captionColumn) {
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

}
