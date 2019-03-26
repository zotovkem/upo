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
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.view.MainLayout;

/**
 *
 */
@Route(value = "organization", layout = MainLayout.class)
public class OrganizationCrudView extends HorizontalLayout
        implements HasUrlParameter<String> {

    public static final String VIEW_NAME = "Организации";
    private ProgramGrid grid;
    private DictionaryForm form;
    private TextField filter;

    private Button newProduct;

    public OrganizationCrudView() {
        setSizeFull();
        HorizontalLayout topLayout = createTopBar();

        grid = new ProgramGrid();
//        grid.setDataProvider(dataProvider);
//        grid.asSingleSelect().addValueChangeListener(
//                event -> viewLogic.rowSelected(event.getValue()));

        form = DictionaryForm.builder().build();
        form.show();
//        form.setCategories(DataService.get().getAllCategories());

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

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setPlaceholder("Поиск по имени");
        // Apply the filter to grid's data provider. TextField value is never null
//        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        newProduct = new Button("Добавить ПО");
        newProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newProduct.setIcon(VaadinIcon.PLUS_CIRCLE.create());
//        newProduct.addClickListener(click -> viewLogic.newProduct());
        // CTRL+N will create a new window which is unavoidable
        newProduct.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newProduct);
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

    public void setNewProductEnabled(boolean enabled) {
        newProduct.setEnabled(enabled);
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

    public void updateProduct(Program product) {

//        dataProvider.save(product);
    }

    public void removeProduct(Program product) {
//        dataProvider.delete(product);
    }

    public void editProduct(Program product) {
        showForm(product != null);
//        form.editProgram(product);
    }

    public void showForm(boolean show) {
        form.setVisible(show);

        /* FIXME The following line should be uncommented when the CheckboxGroup
         * issue is resolved. The category CheckboxGroup throws an
         * IllegalArgumentException when the form is disabled.
         */
        //form.setEnabled(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
//        viewLogic.enter(parameter);
    }
}
