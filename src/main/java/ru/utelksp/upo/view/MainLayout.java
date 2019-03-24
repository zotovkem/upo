package ru.utelksp.upo.view;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import ru.utelksp.upo.view.crud.ProgramCrudView;

/**
 *
 */
@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class)
@PWA(name = "Учет Програмного обеспечания", shortName = "UPO")
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;

    public MainLayout() {
        setSizeFull();
        setClassName("main-layout");

        menu = new Menu();
        menu.addView(ProgramCrudView.class, ProgramCrudView.VIEW_NAME,
                VaadinIcon.EDIT.create());
//        menu.addView(AboutView.class, AboutView.VIEW_NAME,
//                VaadinIcon.INFO_CIRCLE.create());

        add(menu);

    }
}
