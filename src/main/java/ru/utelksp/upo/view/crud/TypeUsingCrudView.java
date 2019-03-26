package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.service.TypeUsingService;
import ru.utelksp.upo.view.BuilderDictionaryForm;
import ru.utelksp.upo.view.MainLayout;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Форма для справочника видов использования
 */
@Route(value = "typeUsing", layout = MainLayout.class)
@Component
@RequiredArgsConstructor
public class TypeUsingCrudView extends HorizontalLayout implements HasUrlParameter<String> {
    private final TypeUsingService typeUsingService;
    private final BuilderDictionaryForm builderDictionaryForm;

    public static final String VIEW_NAME = "Виды использования";
    private TypeUsingGrid grid;
    private DictionaryForm form;

    @PostConstruct
    private void init() {
        setSizeFull();
        grid = new TypeUsingGrid();
        grid.setItems(typeUsingService.findAll());
        form = DictionaryForm.builder()
                .deleteListener(getListenerDelete(form))
                .build();
        form.show();
        grid.addItemDoubleClickListener(getFillFormListener(form));

        builderDictionaryForm.addGrid(grid, this);
        builderDictionaryForm.addForm(this, form);
//        grid.asSingleSelect().addValueChangeListener(
//                event -> viewLogic.rowSelected(event.getValue()));
    }

    private ComponentEventListener<ItemDoubleClickEvent<TypeUsing>> getFillFormListener(DictionaryForm form) {
        return event -> grid.getSelectedRow().ifPresent(typeUsing -> {
            form.setValIdTextField(typeUsing.getId().toString());
            form.setValNameTextField(typeUsing.getName());
            form.setValDescriptionTextField(typeUsing.getDescription());
            form.setVisible(true);
        });
    }

    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewProductEnabled(boolean enabled) {
//        newProduct.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(TypeUsing row) {
        grid.getSelectionModel().select(row);
    }

    public Optional<TypeUsing> getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProduct(TypeUsing product) {

//        dataProvider.save(product);
    }

    public void removeProduct(TypeUsing product) {
//        dataProvider.delete(product);
    }

    public void editProduct(TypeUsing product) {
        showForm(product != null);
//        form.editProgram(product);
    }

    public void showForm(boolean show) {
        form.setVisible(show);
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
//        viewLogic.enter(parameter);
    }

    private ComponentEventListener<ClickEvent<Button>> getListenerDelete(DictionaryForm form) {
        return event -> {
//            if (currentProgram != null) {
//                viewLogic.deleteProgram(currentProgram.getId());
//            }
        };
    }

    private ComponentEventListener<ClickEvent<Button>> getListenerCancel(DictionaryForm form) {
        return event -> this.setVisible(false);
    }
}
