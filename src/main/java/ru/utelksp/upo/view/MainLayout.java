package ru.utelksp.upo.view;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import ru.utelksp.upo.view.component.Menu;
import ru.utelksp.upo.view.crud.*;

import javax.annotation.PostConstruct;

/**
 * Главная страница приложения.
 */
@HtmlImport("css/shared-styles.html")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Учет Програмного обеспечения", shortName = "UPO")
public class MainLayout extends FlexLayout implements RouterLayout {
    private Menu menu;

    @PostConstruct
    private void init() {
        setSizeFull();
        setClassName("main-layout");

        menu = new Menu();
        menu.addView(ProgramCrudView.class, ProgramCrudView.VIEW_NAME, VaadinIcon.INFO.create());
        menu.addView(CertificateCrudView.class, CertificateCrudView.VIEW_NAME, VaadinIcon.MENU.create());
        menu.addView(OrderCrudView.class, OrderCrudView.VIEW_NAME, VaadinIcon.EDIT.create());
        menu.addView(EmployeeCrudView.class, EmployeeCrudView.VIEW_NAME, VaadinIcon.USER.create());
        menu.addView(ComputerCrudView.class, ComputerCrudView.VIEW_NAME, VaadinIcon.NOTEBOOK.create());
        menu.addView(OrganizationCrudView.class, OrganizationCrudView.VIEW_NAME, VaadinIcon.BUILDING.create());
        menu.addView(TypeUsingCrudView.class, TypeUsingCrudView.VIEW_NAME, VaadinIcon.ACCORDION_MENU.create());
        menu.addView(AboutView.class, AboutView.VIEW_NAME, VaadinIcon.INFO_CIRCLE.create());

        add(menu);

    }
}
