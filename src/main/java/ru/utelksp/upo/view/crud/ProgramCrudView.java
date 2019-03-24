package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.service.ProgramService;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;


@Route(value = "Program", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Component
@RequiredArgsConstructor
public class ProgramCrudView extends HorizontalLayout  {

    public static final String VIEW_NAME = "Програмное обеспечение";
    private final ProgramService programService;
    private ProgramGrid grid;
    private ProgramForm form;
    private TextField filter;

    private Button newProgram;

    @PostConstruct
    void init() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new ProgramGrid();
        grid.setItems(programService.findAll());

        form = new ProgramForm();

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        add(barAndGridLayout);
        add(form);

    }

    private HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Filter name, availability or category");

        newProgram = new Button("New product");
        newProgram.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProgram.setIcon(VaadinIcon.PLUS_CIRCLE.create());
//        newProgram.addClickListener(click -> viewLogic.newProgram());
        newProgram.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newProgram);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        return topLayout;
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewProgramEnabled(boolean enabled) {
        newProgram.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Program row) {
        grid.getSelectionModel().select(row);
    }

    public Program getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProgram(Program product) {
        programService.save(product);
    }

    public void removeProgram(Program product) {
        programService.deleteById(product.getId());
    }

    public void editProgram(Program product) {
        showForm(product != null);
        form.editProgram(product);
    }

    private void showForm(boolean show) {
        form.setVisible(show);
      form.setEnabled(show);
    }

}
