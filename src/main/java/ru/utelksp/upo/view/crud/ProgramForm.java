package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import ru.utelksp.upo.domain.Program;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

/**
 * A form for editing a single product.
 */

public class ProgramForm extends Div {

    private VerticalLayout content;

    private TextField productName;
    private TextField price;
    private TextField stockCount;
    private ComboBox<TypeUsing> typeUsingComboBox;
    private Button save;
    private Button discard;
    private Button cancel;
    private Button delete;

    private Binder<Program> binder;
    private Program currentProgram;


    public ProgramForm() {
        setClassName("product-form");

        content = new VerticalLayout();
        content.setSizeUndefined();
        add(content);

        productName = new TextField("Program name");
        productName.setWidth("100%");
        productName.setRequired(true);
        productName.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(productName);

        price = new TextField("Price");
        price.setSuffixComponent(new Span("€"));
        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        price.setValueChangeMode(ValueChangeMode.EAGER);

        stockCount = new TextField("In stock");
        stockCount.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        stockCount.setValueChangeMode(ValueChangeMode.EAGER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(price,
                stockCount);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setFlexGrow(1, price, stockCount);
        content.add(horizontalLayout);

        typeUsingComboBox = new ComboBox<>("Availability");
        typeUsingComboBox.setWidth("100%");
        typeUsingComboBox.setRequired(true);
        typeUsingComboBox.setAllowCustomValue(false);
        content.add(typeUsingComboBox);

        Label categoryLabel = new Label("Categories");
        categoryLabel.setClassName("vaadin-label");

        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
            if (currentProgram != null
                    && binder.writeBeanIfValid(currentProgram)) {
//                viewLogic.saveProgram(currentProgram);
            }
        });
        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
//        discard.addClickListener(
//                event -> viewLogic.editProgram(currentProgram));

        cancel = new Button("Cancel");
        cancel.setWidth("100%");
//        cancel.addClickListener(event -> viewLogic.cancelProgram());
        cancel.addClickShortcut(Key.ESCAPE);
//        getElement()
//                .addEventListener("keydown", event -> viewLogic.cancelProgram())
//                .setFilter("event.key == 'Escape'");

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
//        delete.addClickListener(event -> {
//            if (currentProgram != null) {
//                viewLogic.deleteProgram(currentProgram.getId());
//            }
//        });

        content.add(save, discard, delete, cancel);
    }

    public void editProgram(Program program) {
        if (program == null) {
            program = new Program();
        }
        delete.setVisible(program.getId() != null);
        currentProgram = program;
        binder.readBean(program);
    }
}
