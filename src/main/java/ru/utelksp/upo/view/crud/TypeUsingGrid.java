package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.grid.Grid;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

import java.util.Optional;

public class TypeUsingGrid extends Grid<TypeUsing> {

    private Optional<TypeUsing> currentRow;

    public TypeUsingGrid() {
        setSizeFull();

        addColumn(TypeUsing::getId)
                .setHeader("Код")
                .setFlexGrow(5)
                .setSortable(true);

        addColumn(TypeUsing::getName)
                .setHeader("Наименование")
                .setFlexGrow(20)
                .setSortable(true);

        addColumn(TypeUsing::getDescription)
                .setHeader("Коментарии")
                .setFlexGrow(50);

        this.addSelectionListener(e -> currentRow = asSingleSelect().getOptionalValue());
    }

    public Optional<TypeUsing> getSelectedRow() {
        return currentRow;
    }

    public void refresh(TypeUsing typeUsing) {
        getDataCommunicator().refresh(typeUsing);
    }

}
