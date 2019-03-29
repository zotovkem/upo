package ru.utelksp.upo.common;

import com.vaadin.flow.component.notification.Notification;
import org.vaadin.crudui.crud.impl.GridCrud;
import ru.utelksp.upo.domain.dictionary.Organization;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Created by ZotovES on 26.03.2019
 * Общие утилиты
 */
public class Util {
    public static void showError(String msg) {
        Notification.show(msg);
    }

    /**
     * Собирает из 2 листов мапу
     */
    public static Map<String, String> getCollectMap(String[] arr1, String[] arr2) {
        return IntStream.range(0, arr1.length)
                .boxed()
                .collect(Collectors.toMap(i -> arr1[i], i -> arr2[i]));
    }

    /**
     * Переводит кнопки и сообщения в гриде
     */
    public static GridCrud<Organization> getTranslateGrid(GridCrud<Organization> crud) {
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
