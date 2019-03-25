package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.grid.Grid;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

public class TypeUsingGrid extends Grid<TypeUsing> {

    public TypeUsingGrid() {
        setSizeFull();

        addColumn(TypeUsing::getName)
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
    }

    public TypeUsing getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(TypeUsing typeUsing) {
        getDataCommunicator().refresh(typeUsing);
    }

}
