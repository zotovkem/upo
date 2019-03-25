package ru.utelksp.upo.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.server.VaadinSession;

public class Menu extends FlexLayout {

    private static final String SHOW_TABS = "show-tabs";
    private Tabs tabs;

    public Menu() {
        setClassName("menu-bar");

        // Button for toggling the menu visibility on small screens
//        final Button showMenu = new Button("Menu", event -> {
//            if (tabs.getClassNames().contains(SHOW_TABS)) {
//                tabs.removeClassName(SHOW_TABS);
//            } else {
//                tabs.addClassName(SHOW_TABS);
//            }
//        });
//        showMenu.setClassName("menu-button");
//        showMenu.addThemeVariants(ButtonVariant.LUMO_SMALL);
//        showMenu.setIcon(new Icon(VaadinIcon.MENU));
//        add(showMenu);

        // Заголовок меню
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        top.setClassName("menu-header");

        Label title = new Label("Учет ПО");

        //Логотип
        String resolvedImage = VaadinServletService.getCurrent()
                .resolveResource("frontend://img/table-logo.png",
                        VaadinSession.getCurrent().getBrowser());

        Image image = new Image(resolvedImage, "");
        top.add(image);
        top.add(title);
        add(top);

        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        setFlexGrow(1, tabs);
        add(tabs);

        // Кнопка выйти
        Button logoutButton = new Button("Выйти",
                VaadinIcon.SIGN_OUT.create());

        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        add(logoutButton);
    }

    /**
     * Add a view to the navigation menu
     *
     * @param viewClass
     *         that has a {@code Route} annotation
     * @param caption
     *         view caption in the menu
     * @param icon
     *         view icon in the menu
     */
    public void addView(Class<? extends Component> viewClass, String caption, Icon icon) {
        Tab tab = new Tab();
        RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        tab.add(routerLink);
        tabs.add(tab);
    }
}
