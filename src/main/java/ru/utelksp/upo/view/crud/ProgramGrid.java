package ru.utelksp.upo.view.crud;

import com.vaadin.flow.component.grid.Grid;
import ru.utelksp.upo.domain.Program;

public class ProgramGrid extends Grid<Program> {

    public ProgramGrid() {
        setSizeFull();

        addColumn(Program::getName)
                .setHeader("Код")
                .setFlexGrow(5)
                .setSortable(true);

        addColumn(Program::getName)
                .setHeader("Наименование")
                .setFlexGrow(20)
                .setSortable(true);

        addColumn(p -> p.getTypeUsing().getId())
                .setHeader("Вид использования")
                .setFlexGrow(12);

        addColumn(Program::getDescription)
                .setHeader("Коментарии")
                .setFlexGrow(50);
    }

    public Program getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Program program) {
        getDataCommunicator().refresh(program);
    }

}
