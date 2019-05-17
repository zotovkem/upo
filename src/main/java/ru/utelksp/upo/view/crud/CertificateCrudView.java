package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;
import ru.utelksp.upo.domain.Certificate;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.Computer;
import ru.utelksp.upo.domain.dictionary.Employee;
import ru.utelksp.upo.service.ComputerService;
import ru.utelksp.upo.service.EmployeeService;
import ru.utelksp.upo.service.ProgramService;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.component.*;
import ru.utelksp.upo.view.listener.CertificateCrudListener;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static ru.utelksp.upo.common.Util.getCollectMap;
import static ru.utelksp.upo.view.component.FactoryComponent.getItemComputer;
import static ru.utelksp.upo.view.component.FactoryComponent.getItemEmployee;

/**
 * @author Created by ZotovES on 30.03.2019
 * Форма для редактирования справочника Сертификатов
 */
@Route(value = "certificate", layout = MainLayout.class)
@PageTitle("Сертификаты")
@UIScope
@Component
@RequiredArgsConstructor
public class CertificateCrudView extends VerticalLayout {
    private final CertificateCrudListener certificateCrudListener;
    private final EmployeeService employeeService;
    private final ComputerService computerService;
    private final ProgramService programService;

    private static final String[] CRUD_FORM_FIELD = {"id", "name", "employee", "publisher",
            "keyContainerName", "dateEnd", "description", "programs"};
    private static final String[] CRUD_FORM_FIELD_CAPTION = {"Код", "Наименование", "Пользователь",
            "Издатель", "Имя ключевого контейнера", "Дата окончания", "Комментарии", "Программы"};
    private static final List<String> GRID_COLUMNS = List.of("id", "name");
    private static final List<String> GRID_COLUMNS_CAPTION = List.of("Код", "Наименование");
    private static final Map<String, String> MAP_COLUMN_PROP = getCollectMap(GRID_COLUMNS, GRID_COLUMNS_CAPTION);
    public static final String VIEW_NAME = "Сертификаты";
    private static final List<String> GRID_PROGRAM_COLUMNS = List.of("name");
    private static final Map<String, String> MAP_COLUMN_PROGRAM = Map.of("name", "Наименование");

    /**
     * Заполняет форму
     */
    @SuppressWarnings("Duplicates")
    @PostConstruct
    public void init() {
        setSizeFull();

        UpoCrudFormFactory<Certificate> formFactory = new UpoCrudFormFactory<>(Certificate.class);
        formFactory.setVisibleProperties(CRUD_FORM_FIELD);
        formFactory.setFieldCaptions(CRUD_FORM_FIELD_CAPTION);
        formFactory.setFieldProvider("employee", getEmployeeProvider());
        formFactory.setFieldProvider("computer", getComputerProvider());
        formFactory.setFieldProvider("programs", () -> {
            var grid = new CustomGrid<>(Program.class, programService.findAll(), GRID_PROGRAM_COLUMNS, MAP_COLUMN_PROGRAM);
            grid.getGrid().getColumnByKey("name");
            grid.setValue(programService.findAll());
            return grid;
        });
        UpoHorizontalSplitCrudLayout splitLayout = new UpoHorizontalSplitCrudLayout();
        splitLayout.getMainLayout().setSplitterPosition(60);
        UpoGridCrud<Certificate> crud = new UpoGridCrud<>(Certificate.class, splitLayout, formFactory, certificateCrudListener);
        crud.setGridColumn(GRID_COLUMNS);
        crud.setGridCaptionColumn(MAP_COLUMN_PROP);
        crud.addAttachListener(attachEvent -> refreshCombobox(crud));
        TextField employeeFilter = new TextField();
        employeeFilter.setPlaceholder("поиск по пользователю");
        employeeFilter.addValueChangeListener(e -> crud.refreshGrid());
        crud.getCrudLayout().addFilterComponent(employeeFilter);
        crud.getGrid().addColumn(new TextRenderer<>(FactoryComponent.getItemEmployeeOfCertificate())).setHeader("Пользователь").setWidth("45%");
        crud.setFindAllOperation(DataProvider.fromCallbacks(
                query -> certificateCrudListener.findByLikeEmployee(employeeFilter.getValue(), query.getLimit(), query.getOffset()).stream(),
                query -> certificateCrudListener.countByLikeEmployee(employeeFilter.getValue(), query.getLimit(), query.getOffset())));
        crud.getGrid().getColumnByKey("id").setWidth("15%");
        crud.getGrid().getColumnByKey("name").setWidth("30%");
        add(crud);
    }

    /**
     * Обновляет значения справочников в выпадающих списках
     *
     * @param crud форма
     */
    @SuppressWarnings("unchecked")
    private void refreshCombobox(UpoGridCrud crud) {
        crud.getCrudFormFactory().setFieldProvider("employee", getEmployeeProvider());
        crud.getCrudFormFactory().setFieldProvider("computer", getComputerProvider());
        crud.getCrudFormFactory().buildCaption(CrudOperation.READ, null);
    }

    /**
     * Получить провайдера для справочника компьютеров
     */
    private ComboBoxProvider getComputerProvider() {
        return new ComboBoxProvider<>("Компьютер", computerService.findAll(), new TextRenderer<>(getItemComputer()), Computer::getName);
    }

    /**
     * Получить провейдера для справочника пользователей
     */
    private ComboBoxProvider getEmployeeProvider() {
        return new ComboBoxProvider<>("Пользователь", employeeService.findAll(), new TextRenderer<>(getItemEmployee()), Employee::getShortFio);
    }
}
