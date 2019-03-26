package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.utelksp.upo.domain.dictionary.TypeUsing;
import ru.utelksp.upo.service.TypeUsingService;
import ru.utelksp.upo.view.MainLayout;
import ru.utelksp.upo.view.form.DictionaryEditForm;
import ru.utelksp.upo.view.form.GridForm;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Форма для справочника видов использования
 */
@Route(value = "typeUsing", layout = MainLayout.class)
@Component
@RequiredArgsConstructor
public class TypeUsingCrudView extends HorizontalLayout {
    private final TypeUsingService typeUsingService;

    public static final String VIEW_NAME = "Виды использования";
    private TypeUsingGrid grid;
    private DictionaryEditForm editForm;
    private GridForm gridForm;

    @PostConstruct
    private void init() {
        setSizeFull();
        grid = new TypeUsingGrid();
        grid.setItems(typeUsingService.findAll());
        editForm = DictionaryEditForm.builder()
                .deleteListener(getListenerDelete())
                .build();
        editForm.show();
        grid.addItemDoubleClickListener(getFillFormListener(editForm));

        gridForm = GridForm.builder()
                .grid(grid)
                .filterListener(getFilterListener(grid))
                .addListener(getAddListener())
                .build();
        gridForm.show();

        add(gridForm);
        add(editForm);
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
//        editForm.editProgram(product);
    }

    public void showForm(boolean show) {
        editForm.setVisible(show);
    }

    private ComponentEventListener<ClickEvent<Button>> getListenerDelete() {
        return event -> {
//            if (currentProgram != null) {
//                viewLogic.deleteProgram(currentProgram.getId());
//            }
        };
    }

    private ComponentEventListener<ClickEvent<Button>> getListenerCancel(DictionaryEditForm form) {
        return event -> this.setVisible(false);
    }

    private ComponentEventListener<ClickEvent<Button>> getAddListener() {
        return null;
    }

    private HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>> getFilterListener(TypeUsingGrid grid) {
        return null;
    }

    private ComponentEventListener<ItemDoubleClickEvent<TypeUsing>> getFillFormListener(DictionaryEditForm form) {
        return event -> grid.getSelectedRow().ifPresent(typeUsing -> {
            form.setValIdTextField(typeUsing.getId().toString());
            form.setValNameTextField(typeUsing.getName());
            form.setValDescriptionTextField(typeUsing.getDescription());
            form.setVisible(true);
        });
    }
}
