package ru.utelksp.upo.view.form.grid;

import com.vaadin.flow.component.grid.Grid;
import ru.utelksp.upo.domain.dictionary.TypeUsing;

import java.util.Optional;

/**
 * Грид для справочника Виды использования
 */
public class TypeUsingGrid extends Grid<TypeUsing> {

    private TypeUsing currentRow;

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
                .setHeader("Комментарии")
                .setFlexGrow(50);

        this.addSelectionListener(e -> currentRow = asSingleSelect().getOptionalValue().orElse(null));
    }

    /**
     * Получить выделеную в гриде сущность
     *
     * @return объект
     */
    public Optional<TypeUsing> getSelectedRow() {
        return Optional.ofNullable(currentRow);
    }
}
