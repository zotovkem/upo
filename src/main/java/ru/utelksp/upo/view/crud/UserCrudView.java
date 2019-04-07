package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.security.User;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.UpoCrudFormFactory;
import ru.utelksp.upo.view.component.UpoGridCrud;
import ru.utelksp.upo.view.component.UpoHorizontalSplitCrudLayout;
import ru.utelksp.upo.view.listener.UserCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;

/**
 * @author Created by ZotovES on 30.03.2019
 * Форма для редактирования справочника пользователей
 */
@Route(value = "user", layout = MainLayout.class)
@PageTitle("Администрирование учетных записей")
@UIScope
@Component
@RequiredArgsConstructor
public class UserCrudView extends VerticalLayout {
    private final UserCrudListener userCrudListener;

    private static final String[] CRUD_FORM_FIELD = {"id", "username", "password", "confirmPassword"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Логин", "Пароль", "Повторите пароль"};
    private static final List<String> GRID_COLUMNS = List.of("id", "username");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Имя пользователя");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Учетные записи";

    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<User> formFactory = new UpoCrudFormFactory<>(User.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldType("password", PasswordField.class);
        formFactory.setFieldType("confirmPassword", PasswordField.class);

        UpoGridCrud<User> crud = new UpoGridCrud<>(User.class, new UpoHorizontalSplitCrudLayout(), formFactory, userCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        add(crud);
    }
}
