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
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.utelksp.upo.domain.event.LogoutUserEvent;
import ru.utelksp.upo.service.SecurityService;

import static java.lang.String.format;
import static ru.utelksp.upo.common.UpoConst.LOGOUT_PAGE_URL;
import static ru.utelksp.upo.common.UpoConst.LOGO_URL;

@RequiredArgsConstructor
public class Menu extends FlexLayout {
    //    @Value("${server.servlet.context-path}")
    private final String appUrl;
    private final ApplicationEventPublisher eventPublisher;
    private final SecurityService securityService;
    private Tabs tabs;

    public void init() {
        setClassName("menu-bar");

        // Заголовок меню
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        top.setClassName("menu-header");

        Label title = new Label("Учет ПО");

        //Логотип
        String resolvedImage = VaadinServletService.getCurrent()
                .resolveResource(appUrl + LOGO_URL, VaadinSession.getCurrent().getBrowser());
        Image image = new Image(resolvedImage, "");
        top.add(image);
        top.add(title);
        add(top);

        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        setFlexGrow(1, tabs);
        add(tabs);

        // Кнопка выйти
        Button logoutButton = new Button("Выйти", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(event -> {
            var username = securityService.currentUsername().orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
            eventPublisher.publishEvent(new LogoutUserEvent(this, username));
            UI.getCurrent().getPage().executeJavaScript(format("window.location.href='%s'", appUrl + LOGOUT_PAGE_URL));
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
