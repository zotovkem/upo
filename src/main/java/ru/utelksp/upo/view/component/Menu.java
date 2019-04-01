package ru.utelksp.upo.view.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
        logoutButton.addClickListener(event -> {
            UI.getCurrent().getPage().executeJavaScript(
                    "window.location.href='/logout'");
            UI.getCurrent().getSession().close();
        });

        logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        add(logoutButton);
    }

    /**
     * Добавляет пункт меню на панель
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
